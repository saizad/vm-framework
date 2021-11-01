package com.vm.frameworkexample

import com.vm.frameworkexample.api.AuthApi
import com.vm.frameworkexample.components.auth.splash.SplashViewModel
import com.vm.frameworkexample.di.auth.AuthEnvironment
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.mock

class SplashViewModelTestUnit {

    lateinit var splashViewModel: SplashViewModel
    lateinit var authEnvironment: AuthEnvironment
    @Before
    fun setUp() {
        authEnvironment = mock(AuthEnvironment::class.java)
        Mockito.doReturn(mock(AuthApi::class.java)).`when`(authEnvironment).api
        Mockito.doReturn(mock(MVVMExampleCurrentUser::class.java)).`when`(authEnvironment).currentUser
    }

    @Test
    fun userNotLoggedIn() {
        splashViewModel = SplashViewModel(authEnvironment)
    }
}