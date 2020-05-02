package com.saizad.mvvmexample.components.main

import com.saizad.mvvm.SaizadLocation
import com.saizad.mvvm.Environment
import com.saizad.mvvm.ViewModelProviderFactory
import com.saizad.mvvm.components.DrawerMainActivity
import com.saizad.mvvmexample.di.main.MainEnvironment
import javax.inject.Inject

class MVVMExampleMainActivity : DrawerMainActivity<MVVMExampleMainActivityViewModel>() {

    @Inject
    lateinit var gpsLocation: SaizadLocation

    @Inject
    lateinit var mainEnvironment: MainEnvironment

    @Inject
    lateinit var viewModelProviderFactory: ViewModelProviderFactory

    override fun environment(): Environment {
        return mainEnvironment
    }

    override fun viewModelProviderFactory(): ViewModelProviderFactory {
        return viewModelProviderFactory
    }

    override fun getViewModelClassType(): Class<MVVMExampleMainActivityViewModel> {
        return MVVMExampleMainActivityViewModel::class.java
    }

    override fun appLocation(): SaizadLocation {
        return gpsLocation
    }
}