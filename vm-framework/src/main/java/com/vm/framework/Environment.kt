package com.vm.framework

import kotlinx.coroutines.flow.MutableStateFlow
import sa.zad.easypermission.PermissionManager

abstract class Environment(
    val currentUser: CurrentUserType<*>,
    val networkRequest: VmFrameworkNetworkRequest,
    val permissionManager: PermissionManager,
    val activityResultFlow : MutableStateFlow<ActivityResult<*>>
)