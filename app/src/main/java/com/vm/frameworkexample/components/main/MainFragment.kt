package com.vm.frameworkexample.components.main

import com.vm.framework.VmFrameworkNetworkRequest
import com.vm.frameworkexample.components.VmFrameworkExampleFragment
import com.vm.frameworkexample.di.main.MainEnvironment
import javax.inject.Inject

abstract class MainFragment<V : MainViewModel> : VmFrameworkExampleFragment<V>() {
    @Inject
    lateinit var mainEnvironment: MainEnvironment

    override val networkRequest: VmFrameworkNetworkRequest by lazy {
        mainEnvironment.networkRequest
    }
}