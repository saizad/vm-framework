package com.vm.frameworkexample.components.auth.login

import androidx.lifecycle.MutableLiveData
import com.vm.framework.enums.DataState
import com.vm.framework.model.DataModel
import com.vm.framework.model.LoginBody
import com.vm.frameworkexample.ApiRequestCodes
import com.vm.frameworkexample.components.auth.AuthViewModel
import com.vm.frameworkexample.di.auth.AuthEnvironment
import com.vm.frameworkexample.models.LoginResponse
import com.vm.frameworkexample.models.ReqResUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
open class LoginViewModel @Inject constructor(
    environment: AuthEnvironment
) : AuthViewModel(environment) {

    private val form by lazy {
        LoginBody.EmailLoginForm("eve.holt@reqres.in")
    }

    val loginFormLiveData: MutableLiveData<LoginBody.EmailLoginForm> = MutableLiveData()

    init {
        val random = java.util.UUID.randomUUID().toString().substring(0, 10)
        form.passwordField.field = random
        loginFormLiveData.postValue(form)
    }

    open fun login(requestId: Int = ApiRequestCodes.LOGIN): Flow<DataState<DataModel<ReqResUser>>> {
        return loginApi()
            .flatMapMerge {
                flatMapMerge(it)
            }
    }

    private fun flatMapMerge(it: DataState<LoginResponse>): Flow<DataState<DataModel<ReqResUser>>> {
        return when (it) {
            is DataState.Success -> {
                fetchUser()
            }
            is DataState.Loading -> {
                MutableStateFlow(it)
            }
            is DataState.Error -> {
                MutableStateFlow(it)
            }
            is DataState.ApiError -> {
                MutableStateFlow(it)
            }
        }
    }

    fun fetchUser(): Flow<DataState<DataModel<ReqResUser>>> {
       return userApi()
            .onEach {
                logUserLocally(it)
            }
    }

    suspend fun logUserLocally(dataState: DataState<DataModel<ReqResUser>>){
        if (dataState is DataState.Success) {
            currentUser().login(dataState.data!!.data)
        }
    }

    fun userApi(): Flow<DataState<DataModel<ReqResUser>>> {
        return flowData(api.user((1..10).random()), ApiRequestCodes.LOGIN)
    }

    fun loginApi(): Flow<DataState<LoginResponse>> {
        return flowData(api.login(form.requiredBuild()), ApiRequestCodes.LOGIN)
    }
}