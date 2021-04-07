package com.vm.frameworkexample.models

import com.vm.framework.model.BaseApiError

data class ApiError(val error: String = "Error not described") : BaseApiError() {

    override fun error(): String {
        return "Error"
    }

    override fun message(): String {
        return error
    }

}