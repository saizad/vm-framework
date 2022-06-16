package com.vm.frameworkexample.components.auth

import com.vm.framework.VmFrameworkNetworkRequest
import com.vm.frameworkexample.components.VmFrameworkExampleFragment
import com.vm.frameworkexample.di.auth.AuthEnvironment
import javax.inject.Inject

abstract class AuthFragment<V : AuthViewModel> : VmFrameworkExampleFragment<V>() {
    @Inject
    lateinit var authEnvironment: AuthEnvironment

    override val networkRequest: VmFrameworkNetworkRequest by lazy {
        authEnvironment.networkRequest
    }
}

