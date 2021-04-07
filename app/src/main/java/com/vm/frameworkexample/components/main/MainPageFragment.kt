package com.vm.frameworkexample.components.main

import com.vm.framework.VmFrameworkLocation
import com.vm.framework.pager.BasePage
import com.vm.frameworkexample.MVVMExampleCurrentUser
import sa.zad.easypermission.PermissionManager
import javax.inject.Inject

abstract class MainPageFragment<VM : MainPageViewModel> : BasePage<VM>() {
    
    @Inject
    lateinit var permissionManager: PermissionManager

    @Inject
    lateinit var gpsLocation: VmFrameworkLocation

    @Inject
    lateinit var mvvmExampleCurrentUser: MVVMExampleCurrentUser
    
    override fun appLocation(): VmFrameworkLocation {
        return gpsLocation
    }

    override fun permissionManager(): PermissionManager {
        return permissionManager
    }

}