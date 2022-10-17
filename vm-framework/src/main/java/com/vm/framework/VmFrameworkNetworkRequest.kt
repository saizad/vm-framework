package com.vm.framework

import com.vm.framework.enums.DataState
import com.vm.framework.error.ApiErrorException
import com.vm.framework.model.BaseApiError
import com.vm.framework.model.ErrorModel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.*
import sa.zad.easyretrofit.observables.NeverErrorObservable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class VmFrameworkNetworkRequest @Inject constructor() {

    private val _apiRequest = MutableStateFlow<ApiRequest<Any?>?>(null)

    val apiRequest = _apiRequest.filterNotNull()

    fun <M> NeverErrorObservable<M>.toFlowDataState(
        requestId: Int
    ): Flow<DataState<M>> {
        return toFlowDataState(requestId, ErrorModel::class.java)
    }

    fun <M, E> NeverErrorObservable<M>.toFlowDataState(requestId: Int, eClass: Class<E>): Flow<DataState<M>> {

        return callbackFlow {
            trySend(DataState.Loading(true, requestId))
            val subscribe = successResponse {
                trySend(DataState.Success(it.body(), requestId))
            }
                .timeoutException {
                    trySend(DataState.Error(it, requestId))
                }
                .connectionException {
                    trySend(DataState.Error(it, requestId))
                }
                .apiException({
                    if (it != null) {
                        val apiErrorException = ApiErrorException(it as BaseApiError)
                        trySend(DataState.ApiError(apiErrorException, requestId))
                    }
                }, eClass)
                .exception {
                    trySend(DataState.Error(it, requestId))
                }
                .doFinally {
                    trySend(DataState.Loading(false, requestId))
                }.subscribe()

            awaitClose {
                subscribe.dispose()
            }

        }
            .onEach {
                _apiRequest.value = ApiRequest(it)
            }

    }

    fun <M> flowData(
        observable: NeverErrorObservable<M>,
        requestId: Int
    ): Flow<DataState<M>> {
        return flowData(observable, requestId, ErrorModel::class.java)
    }

    fun <M, E> flowData(
        observable: NeverErrorObservable<M>,
        requestId: Int,
        eClass: Class<E>
    ): Flow<DataState<M>> {
        return  observable.toFlowDataState(requestId, eClass)
    }

    data class ApiRequest<out R>(val dataState: DataState<R>)
}