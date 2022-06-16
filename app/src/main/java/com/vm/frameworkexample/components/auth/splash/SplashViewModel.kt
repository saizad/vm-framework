package com.vm.frameworkexample.components.auth.splash

import com.vm.framework.enums.DataState
import com.vm.framework.model.DataModel
import com.vm.framework.utils.dataStateDataModelSuccessFlow
import com.vm.frameworkexample.components.auth.AuthViewModel
import com.vm.frameworkexample.di.auth.AuthEnvironment
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

@HiltViewModel
@ExperimentalCoroutinesApi
class SplashViewModel @Inject constructor(
    authEnvironment: AuthEnvironment
) : AuthViewModel(authEnvironment) {

    val result: Flow<DataState<DataModel<Boolean>>> =
        currentUser().isLoggedIn
            .onStart { delay(200) }
            .flatMapLatest {
                dataStateDataModelSuccessFlow(-1) { it }
            }

}

