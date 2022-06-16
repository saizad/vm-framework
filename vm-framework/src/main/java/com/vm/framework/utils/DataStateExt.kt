package com.vm.framework.utils

import android.util.Log
import com.vm.framework.enums.DataState
import com.vm.framework.error.ApiErrorData
import com.vm.framework.error.ApiErrorException
import com.vm.framework.error.ErrorData
import com.vm.framework.model.DataModel
import com.vm.framework.model.ErrorModel
import com.vm.framework.model.IntPageDataModel
import kotlinx.coroutines.flow.*
import java.net.ConnectException
import java.util.concurrent.TimeoutException

fun <T> Flow<DataState<T>>.failOrSuccess(): Flow<DataState<T>> {
    return filter {
        it !is DataState.Loading
    }
}

fun <T> Flow<DataState<T>>.failed(): Flow<DataState<T>> {
    return filter {
        it is DataState.Error || it is DataState.ApiError
    }
}

fun <T> Flow<DataState<T>>.filterApiError(): Flow<DataState.ApiError> {
    return filterIsInstance()
}

fun <T> Flow<DataState<T>>.filterError(): Flow<DataState.Error> {
    return filterIsInstance()
}

fun <T> Flow<DataState<T>>.filterAnyError(): Flow<DataState.Error> {
    return failed()
        .map {
            if (it is DataState.ApiError) {
                DataState.Error(it.apiErrorException, it.requestId)
            } else if(it is DataState.Error) {
                it
            } else {
                null
            }
        }.filterNotNull()
}

fun <R> Flow<DataState<R>>.stateToData(): Flow<R?> {
    return filterSuccess()
        .map { it.data }
}

fun <R> Flow<DataState<DataModel<R>>>.dataModel(): Flow<DataModel<R>> {
    return filterSuccess().map { it.data!! }
}

fun <R> Flow<DataState<IntPageDataModel<R>>>.intPagedDataModel(): Flow<IntPageDataModel<R>> {
    return dataModel().filterIsInstance()
}

fun <R> Flow<DataState<DataModel<R>>>.stateToDataRemoveDataModel(): Flow<R> {
    return dataModel().map { it.data }
}

fun <R> Flow<DataState<R>>.filterSuccess(): Flow<DataState.Success<R>> {
    return filterIsInstance()
}

fun Flow<DataState<Void>>.noContentStateToData(): Flow<Void?> {
    return filterSuccess().map { it.data }
}

fun <T> Flow<DataState<T>>.successState(callback: suspend (DataState.Success<T>) -> Unit): Flow<DataState<T>> {
    return onEach {
        if (it is DataState.Success) {
            callback(it)
        }
    }
}

fun <T> Flow<DataState<T>>.successModel(callback: suspend (T?) -> Unit): Flow<DataState<T>> {
    return onEach {
        if (it is DataState.Success) {
            callback(it.data)
        }
    }
}

fun <T> Flow<DataState<T>>.requireSuccessModel(callback: suspend (T) -> Unit): Flow<DataState<T>> {
    return successModel {
        callback.invoke(it!!)
    }
}

fun <T> Flow<DataState<T>>.apiErrorState(callback: suspend (DataState.ApiError) -> Unit): Flow<DataState<T>> {
    return onEach {
        if (it is DataState.ApiError) {
            callback(it)
        }
    }
}

fun <T> Flow<DataState<T>>.errorState(callback: suspend (DataState.Error) -> Unit): Flow<DataState<T>> {
    return onEach {
        if (it is DataState.Error) {
            callback(it)
        }
    }
}

fun <T> Flow<DataState<T>>.anyErrorState(callback: suspend (DataState.Error) -> Unit): Flow<DataState<T>> {
    return apiErrorState { callback(DataState.Error(it.apiErrorException, it.requestId)) }
        .errorState(callback)
}

fun <T> Flow<DataState<T>>.connectionErrorState(callback: suspend (ConnectException) -> Unit): Flow<DataState<T>> {
    return errorState {
        if (it.throwable is ConnectException) {
            callback(it.throwable)
        }
    }
}

fun <T> Flow<DataState<T>>.timeoutErrorState(callback: suspend (TimeoutException) -> Unit): Flow<DataState<T>> {
    return errorState {
        if (it.throwable is TimeoutException) {
            callback(it.throwable)
        }
    }
}

fun <T> Flow<DataState<T>>.onError(callback: suspend (String) -> Unit): Flow<DataState<T>> {
    return onEach {
        if (it is DataState.Error || it is DataState.ApiError) {
            val message = if(it is DataState.Error) {
                it.throwable.message
            } else {
                (it as DataState.ApiError).apiErrorException.errorModel.message()
            }
            callback(message ?: "--NA--")
        }
    }
}

fun <T> Flow<DataState<T>>.loadingState(callback: suspend (DataState.Loading) -> Unit): Flow<DataState<T>> {
    return onEach {
        if (it is DataState.Loading) {
            callback(it)
        }
    }
}

fun <T, R> Flow<DataState<T>>.successMap(callback: suspend suspend (T?) -> R): Flow<DataState<R>> {
    return map {
        when (it) {
            is DataState.Loading -> {
                it
            }
            is DataState.ApiError -> {
                it
            }
            is DataState.Error -> {
                it
            }
            is DataState.Success -> {
                DataState.Success(callback(it.data), it.requestId)
            }
        }
    }
}

fun <T> Flow<DataState<DataModel<T>>>.requireRemoveDataModel(): Flow<DataState<T>> {
    return successMap { it!!.data }
}

fun <T> Flow<DataState<DataModel<T>>>.removeDataModel(): Flow<DataState<T?>> {
    return successMap { it!!.data }
}

inline fun <T> Flow<DataState<T>>.errorToSuccess(crossinline callback: suspend (Throwable) -> T): Flow<DataState<T>> {
    return map {
        when (it) {
            is DataState.Loading -> {
                it
            }
            is DataState.ApiError -> {
                DataState.Success(callback(it.apiErrorException), it.requestId)
            }
            is DataState.Error -> {
                DataState.Success(callback(it.throwable), it.requestId)
            }
            is DataState.Success -> {
                it
            }
        }
    }
}

suspend fun <T> dataStateErrorFlow(emitValue: Throwable, requestId: Int): Flow<DataState<T>> {
    return flow {
        emit(DataState.Loading(true, requestId))
        kotlinx.coroutines.delay(10)
        emit(DataState.Error(emitValue, requestId))
        kotlinx.coroutines.delay(10)
        emit(DataState.Loading(false, requestId))
    }
}

fun <T> dataStateFlow(
    requestId: Int,
    block: suspend () -> T
): Flow<DataState<DataModel<T>>> {
    return flow {
        emit(DataState.Loading(true, requestId))
        kotlinx.coroutines.delay(10)
        try {
            block.invoke()
            emit(DataState.Success(DataModel(block.invoke()), requestId))
        } catch (e: Exception){
            emit(DataState.Error(e, requestId))
        } catch (e: ApiErrorException){
            emit(DataState.ApiError(e, requestId))
        }
        kotlinx.coroutines.delay(10)
        emit(DataState.Loading(false, requestId))
    }
}

fun <T> dataStateSuccessFlow(
    requestId: Int,
    block: suspend () -> T?
): Flow<DataState<T>> {
    return flow {
        emit(DataState.Loading(true, requestId))
        kotlinx.coroutines.delay(10)
        emit(DataState.Success(block.invoke(), requestId))
        kotlinx.coroutines.delay(10)
        emit(DataState.Loading(false, requestId))
    }
}

fun <T> dataStateDataModelSuccessFlow(
    requestId: Int,
    block: suspend () -> T
): Flow<DataState<DataModel<T>>> {
    return dataStateSuccessFlow(requestId) {
        DataModel(block.invoke()!!)
    }
}

fun <T> Flow<T>.flowLog(tag: String, message: (T) -> String): Flow<T> {
    return onEach {
        Log.d(tag, message.invoke(it))
    }
}

fun <T> Flow<T>.flowLog(tag: String): Flow<T> {
    return flowLog(tag) {
        it.toString()
    }
}

fun <T> Flow<DataState<T>>.loadingState(): Flow<DataState.Loading> {
    return filterIsInstance()
}

fun <T> Flow<DataState<T>>.successState(): Flow<DataState.Success<T>> {
    return filterIsInstance()
}

fun <T> Flow<DataState<T>>.exception(): Flow<Throwable> {
    return filterError().map { it.throwable }
}

fun <T> Flow<DataState<T>>.connectException(): Flow<ConnectException> {
    return exception().filterIsInstance()
}

fun <T> Flow<DataState<T>>.timeoutException(): Flow<TimeoutException> {
    return exception().filterIsInstance()
}

fun <T> Flow<DataState<T>>.apiErrorException(): Flow<ApiErrorException> {
    return filterApiError().map { it.apiErrorException }
}

fun <T> Flow<DataState<T>>.mapErrorModel(): Flow<ErrorModel> {
    return filterApiError().map { it.apiErrorException.errorModel as ErrorModel }
}

fun DataState.ApiError.toApiErrorData(): ApiErrorData {
    return ApiErrorData(apiErrorException, requestId)
}

fun DataState.Error.toErrorData(): ErrorData {
    return ErrorData(throwable, requestId)
}

fun DataState.Error.isConnectionError(): Boolean {
    return throwable is ConnectException
}

fun DataState.Error.isTimeoutException(): Boolean {
    return throwable is TimeoutException
}

fun DataState.Error.isSimpleError(): Boolean {
    return !(isConnectionError() || isTimeoutException())
}