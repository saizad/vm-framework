package com.vm.frameworkexample.components.auth.blank

import com.vm.frameworkexample.components.auth.AuthViewModel
import com.vm.frameworkexample.di.auth.AuthEnvironment
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BlankViewModel @Inject constructor(
    authEnvironment: AuthEnvironment
) : AuthViewModel(authEnvironment)