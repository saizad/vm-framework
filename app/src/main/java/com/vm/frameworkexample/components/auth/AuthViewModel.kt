package com.vm.frameworkexample.components.auth

import com.vm.frameworkexample.api.AuthApi
import com.vm.frameworkexample.components.VmFrameworkExampleViewModel
import com.vm.frameworkexample.di.auth.AuthEnvironment

abstract class AuthViewModel constructor(
    authEnvironment: AuthEnvironment,
    val api: AuthApi = authEnvironment.api
) : VmFrameworkExampleViewModel(authEnvironment)