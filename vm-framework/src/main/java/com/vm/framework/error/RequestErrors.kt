package com.vm.framework.error

import com.vm.framework.model.BaseApiError
import java.net.ConnectException
import java.util.concurrent.TimeoutException

abstract class ErrorRequest(var requestId: Int): Exception() {
    open fun isThisRequest(vararg ids: Int): Boolean {
        return ids.contains(requestId)
    }
}

class ApiErrorData(val error: ApiErrorException, id: Int) :
    ErrorRequest(id)

class ErrorData(val error: Throwable, id: Int) : ErrorRequest(id)

class ConnectionErrorData(val error: ConnectException, id: Int) : ErrorRequest(id)

class TimeoutErrorData(val error: TimeoutException, id: Int) : ErrorRequest(id)

class LoadingData(var isLoading: Boolean, id: Int) : ErrorRequest(id)

class ApiErrorException(val errorModel: BaseApiError) :
    Exception(Throwable(errorModel.message()))