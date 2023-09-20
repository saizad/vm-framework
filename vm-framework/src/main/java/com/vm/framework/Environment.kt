package com.vm.framework

import kotlinx.coroutines.flow.MutableStateFlow
import sa.zad.easypermission.PermissionManager
import java.util.Locale

abstract class Environment(
    val locale: MutableStateFlow<Locale>,
    val currentUser: CurrentUserType<*>,
    val networkRequest: VmFrameworkNetworkRequest,
    val permissionManager: PermissionManager,
    val activityResultFlow : MutableStateFlow<ActivityResult<*>>
)