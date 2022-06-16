package com.vm.frameworkexample.components

import com.vm.framework.components.VmFrameworkBaseFragment
import com.vm.frameworkexample.MVVMExampleCurrentUser
import sa.zad.easypermission.PermissionManager
import javax.inject.Inject

abstract class VmFrameworkExampleFragment<VM : VmFrameworkExampleViewModel> :
    VmFrameworkBaseFragment<VM>() {

    @Inject
    lateinit var currentUserType: MVVMExampleCurrentUser

    @Inject
    lateinit var permissionManager: PermissionManager


    override fun permissionManager(): PermissionManager {
        return permissionManager
    }

    val vmFrameworkExampleActivity: VmFrameworkExampleActivity<*> by lazy { requireActivity() as VmFrameworkExampleActivity<*> }
}