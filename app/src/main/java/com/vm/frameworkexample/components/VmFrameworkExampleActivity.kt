package com.vm.frameworkexample.components

import com.vm.framework.components.VmFrameworkBaseActivity
import sa.zad.easypermission.PermissionManager
import javax.inject.Inject

abstract class VmFrameworkExampleActivity<VM : VmFrameworkExampleViewModel> : VmFrameworkBaseActivity<VM>() {

    override fun onSupportNavigateUp() = navController().navigateUp()


    @Inject
    lateinit var permissionManager: PermissionManager

    override fun permissionManager(): PermissionManager {
        return permissionManager
    }


}