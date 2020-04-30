package com.saizad.mvvmexample.components.auth

import com.saizad.mvvm.SaizadLocation
import com.saizad.mvvm.Environment
import com.saizad.mvvm.ViewModelProviderFactory
import com.saizad.mvvm.components.AuthActivity
import com.saizad.mvvmexample.AuthEnvironment
import javax.inject.Inject

class AppAuthActivity : AuthActivity<AppAuthViewModel>() {

    @Inject
    lateinit var saizadLocation: SaizadLocation

    @Inject
    lateinit var authEnvironment: AuthEnvironment

    @Inject
    lateinit var viewModelProviderFactory: ViewModelProviderFactory

    override fun environment(): Environment {
        return authEnvironment
    }

    override fun viewModelProviderFactory(): ViewModelProviderFactory {
        return viewModelProviderFactory
    }

    override fun getViewModelClassType(): Class<AppAuthViewModel> {
        return AppAuthViewModel::class.java
    }

    override fun appLocation(): SaizadLocation {
        return saizadLocation
    }
}