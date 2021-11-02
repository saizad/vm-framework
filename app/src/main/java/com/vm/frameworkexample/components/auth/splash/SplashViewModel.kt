package com.vm.frameworkexample.components.auth.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.vm.framework.enums.DataState
import com.vm.frameworkexample.components.auth.AuthViewModel
import com.vm.frameworkexample.di.auth.AuthEnvironment
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.transformLatest
import javax.inject.Inject

@HiltViewModel
@ExperimentalCoroutinesApi
class SplashViewModel @Inject constructor(
    authEnvironment: AuthEnvironment
) : AuthViewModel(authEnvironment) {

    val result: StateFlow<DataState<Boolean>> =
        currentUser().isLoggedIn
            .onStart { delay(100) }
            .transformLatest {
                emit(DataState.Loading(false))
                delay(100)
                emit(DataState.Success(it))
            }.stateIn(
                scope = viewModelScope,
                started = WhileSubscribed(2000),
                initialValue = DataState.Loading(true)
            )
}

