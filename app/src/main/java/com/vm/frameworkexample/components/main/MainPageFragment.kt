package com.vm.frameworkexample.components.main

import com.vm.framework.VmFrameworkNetworkRequest
import com.vm.framework.pager.BasePage
import com.vm.frameworkexample.MVVMExampleCurrentUser
import com.vm.frameworkexample.di.main.MainEnvironment
import sa.zad.easypermission.PermissionManager
import javax.inject.Inject

abstract class MainPageFragment<VM : MainPageViewModel> : BasePage<VM>() {

    @Inject
    lateinit var mainEnvironment: MainEnvironment

    override val networkRequest: VmFrameworkNetworkRequest by lazy {
        mainEnvironment.networkRequest
    }

    @Inject
    lateinit var permissionManager: PermissionManager

    @Inject
    lateinit var mvvmExampleCurrentUser: MVVMExampleCurrentUser

    override fun permissionManager(): PermissionManager {
        return permissionManager
    }

}