package com.vm.framework.enums

import com.vm.framework.components.VmFrameworkBaseViewModel

sealed class DataState<out R> {

    data class Success<out T>(val data: T?) : DataState<T>()
    data class ApiError(val apiErrorException: VmFrameworkBaseViewModel.ApiErrorException) :
        DataState<Nothing>()

    data class Error(val throwable: Throwable) : DataState<Nothing>()
    data class Loading(val isLoading: Boolean) : DataState<Nothing>()
}