package com.saizad.mvvmexample.components.auth

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.saizad.mvvm.Environment
import com.saizad.mvvm.SaizadLocation
import com.saizad.mvvm.ViewModelProviderFactory
import com.saizad.mvvm.components.SaizadBaseAuthActivity
import com.saizad.mvvmexample.R
import com.saizad.mvvmexample.di.auth.AuthEnvironment
import javax.inject.Inject

class MVVMExampleAuthActivity : SaizadBaseAuthActivity<MVVMExampleAuthActivityViewModel>() {

    @Inject
    lateinit var gpsLocation: SaizadLocation

    @Inject
    lateinit var authEnvironment: AuthEnvironment

    @Inject
    lateinit var viewModelProviderFactory: ViewModelProviderFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        //sets to default theme after app launch
        setTheme(R.style.AuthTheme)
        super.onCreate(savedInstanceState)
    }

    override fun environment(): Environment {
        return authEnvironment
    }

    override fun viewModelProviderFactory(): ViewModelProviderFactory {
        return viewModelProviderFactory
    }

    override fun getViewModelClassType(): Class<MVVMExampleAuthActivityViewModel> {
        return MVVMExampleAuthActivityViewModel::class.java
    }

    override fun appLocation(): SaizadLocation {
        return gpsLocation
    }

    override fun navController(): NavController {
        return findNavController(R.id.auth_nav)
    }
}