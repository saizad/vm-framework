package com.vm.framework.enums

import com.vm.framework.error.ApiErrorException

sealed class DataState<out R>(val requestId: Int) {

    data class Success<out T>(val data: T?, private val _requestId: Int) : DataState<T>(_requestId)
    data class ApiError(
        val apiErrorException: ApiErrorException,
        private val _requestId: Int
    ) :
        DataState<Nothing>(_requestId)

    data class Error(val throwable: Throwable, private val _requestId: Int) :
        DataState<Nothing>(_requestId)

    data class Loading(val isLoading: Boolean, private val _requestId: Int) :
        DataState<Nothing>(_requestId)
}