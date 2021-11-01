package com.vm.frameworkexample

import com.vm.framework.components.VmFrameworkBaseViewModel
import com.vm.framework.enums.DataState
import com.vm.framework.model.DataModel
import com.vm.frameworkexample.models.ApiError
import com.vm.frameworkexample.models.ReqResUser


object Utils {

    val FAKE_USER = ReqResUser(1, "John", "Doe", "JohnDoe@gmail.com", "")
    val FAKE_USER_WITH_JOB = ReqResUser(1, "John", "Doe", "JohnDoe@gmail.com", "", "Banker")

    val API_ERROR_EXCEPTION = VmFrameworkBaseViewModel.ApiErrorException(ApiError())
    val ERROR_STATE = DataState.Error(Throwable("Login Error"))

    val API_ERROR_STATE = DataState.ApiError(API_ERROR_EXCEPTION)

    val LOADING_TRUE_STATE = DataState.Loading(true)

    val LOADING_FALSE_STATE = DataState.Loading(false)

    val USER_STATE = DataState.Success(DataModel(FAKE_USER))
    val VOID_STATE = DataState.Success(null)
}