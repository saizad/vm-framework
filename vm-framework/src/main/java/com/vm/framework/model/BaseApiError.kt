package com.vm.framework.model

abstract class BaseApiError {
    abstract fun error(): String
    abstract fun message(): String
}