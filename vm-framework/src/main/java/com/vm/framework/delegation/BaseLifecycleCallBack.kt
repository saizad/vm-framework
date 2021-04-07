package com.vm.framework.delegation

import com.vm.framework.components.VmFrameworkBaseViewModel.*

interface BaseLifecycleCallBack {
    fun requestError(errorData: ErrorData)
    fun requestApiError(apiErrorData: ApiErrorData)
    fun serverError(throwable: Throwable, requestId: Int): Boolean
}