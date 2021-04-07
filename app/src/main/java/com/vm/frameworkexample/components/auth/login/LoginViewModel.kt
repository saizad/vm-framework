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
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
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

    fun login(requestId: Int = ApiRequestCodes.LOGIN): Flow<DataState<LoginResponse>> {
        return flowData(api.login(form.requiredBuild()), requestId)
    }

    fun user(user: Int, requestId: Int = ApiRequestCodes.USER): Flow<DataState<DataModel<ReqResUser>>> {
        return flowData(api.user(user), requestId)
    }
}