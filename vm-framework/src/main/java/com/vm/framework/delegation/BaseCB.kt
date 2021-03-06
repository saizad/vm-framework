package com.vm.framework.delegation

import android.content.Context
import android.os.Bundle
import androidx.annotation.MenuRes
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelStoreOwner
import androidx.navigation.NavController
import com.vm.framework.VmFrameworkLocation
import com.vm.framework.components.VmFrameworkBaseViewModel
import sa.zad.easypermission.PermissionManager

interface BaseCB<V : VmFrameworkBaseViewModel> {

    val lifecycleOwner: LifecycleOwner
    fun context(): Context
    fun viewModelStoreOwner(): ViewModelStoreOwner
    val viewModelClassType: Class<V>
    fun appLocation(): VmFrameworkLocation
    fun permissionManager(): PermissionManager

    @MenuRes
    fun menRes(): Int
    fun navController(): NavController
    fun bundle(): Bundle?
}