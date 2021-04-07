package com.vm.frameworkexample.components

import com.vm.framework.VmFrameworkLocation
import com.vm.framework.components.VmFrameworkBaseFragment
import com.vm.frameworkexample.MVVMExampleCurrentUser
import sa.zad.easypermission.PermissionManager
import javax.inject.Inject

abstract class VmFrameworkExampleFragment<VM : VmFrameworkExampleViewModel> :
    VmFrameworkBaseFragment<VM>() {

    @Inject
    lateinit var gpsLocation: VmFrameworkLocation

    @Inject
    lateinit var currentUserType: MVVMExampleCurrentUser

    @Inject
    lateinit var permissionManager: PermissionManager

    @Inject
    lateinit var location: VmFrameworkLocation

    override fun appLocation(): VmFrameworkLocation {
        return location
    }

    override fun permissionManager(): PermissionManager {
        return permissionManager
    }

}