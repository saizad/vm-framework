package com.vm.framework

import com.vm.framework.components.VmFrameworkBaseViewModel
import com.vm.framework.enums.DataState
import com.vm.framework.error.ApiErrorException
import com.vm.framework.error.LoadingData
import com.vm.framework.model.BaseApiError
import com.vm.framework.model.ErrorModel
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import kotlinx.coroutines.channels.ProducerScope
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.filterNotNull
import sa.zad.easyretrofit.observables.NeverErrorObservable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class VmFrameworkNetworkRequest @Inject constructor() {

    private val _apiRequest = MutableStateFlow<ApiRequest<Any?>?>(null)

    val apiRequest = _apiRequest.filterNotNull()

    fun <M> flowData(
        observable: NeverErrorObservable<M>,
        requestId: Int
    ): Flow<DataState<M>> {
        return flowData(observable, requestId, ErrorModel::class.java)
    }

    fun <M, E : BaseApiError> flowData(
        observable: NeverErrorObservable<M>,
        requestId: Int,
        eClass: Class<E>,
    ): Flow<DataState<M>> {
        val block: suspend ProducerScope<DataState<M>>.() -> Unit = {
            val request = baseRequest(observable, requestId, eClass)
                .subscribe {
                    _apiRequest.value = ApiRequest(it)
                    when (it) {
                        is DataState.Success -> {
                            offer(it)
                        }
                        is DataState.Loading -> {
                            offer(it)
                            if (!it.isLoading) {
                                close()
                            }
                        }
                        is DataState.Error -> {
                            offer(it)
                        }
                        is DataState.ApiError -> {
                            offer(it)
                        }
                    }
                }

            awaitClose {
                request.dispose()
            }
        }
        return callbackFlow(block)
    }

    fun <M, E : BaseApiError> baseRequest(
        observable: NeverErrorObservable<M>,
        requestId: Int,
        eClass: Class<E>
    ): Observable<DataState<M>> {
        val publishSubject = BehaviorSubject.create<DataState<M>>()
        val loadingData = LoadingData(true, requestId)
        publishSubject.onNext(DataState.Loading(true, requestId))
        observable
            .successResponse {
                publishSubject.onNext(DataState.Success(it.body(), requestId))
            }
            .timeoutException { shootError(requestId, publishSubject, it) }
            .connectionException { shootError(requestId, publishSubject, it) }
            .apiException({
                if (it != null) {
                    shootError(requestId, publishSubject, it)
                }
            }, eClass)
            .exception {
                shootError(requestId, publishSubject, it)
            }
            .doFinally {
                loadingData.isLoading = false
                publishSubject.onNext(DataState.Loading(false, requestId))
            }.subscribe()
        return publishSubject
    }


    private fun <M> shootError(
        requestId: Int,
        publishSubject: BehaviorSubject<DataState<M>>,
        errorModel: BaseApiError
    ) {
        val apiErrorException = ApiErrorException(errorModel)
        publishSubject.onNext(DataState.ApiError(apiErrorException, requestId))
    }

    private fun <M> shootError(
        requestId: Int,
        publishSubject: BehaviorSubject<DataState<M>>,
        throwable: Throwable
    ) {
        publishSubject.onNext(DataState.Error(throwable, requestId))
    }

    data class ApiRequest<out R>(val dataState: DataState<R>)
}