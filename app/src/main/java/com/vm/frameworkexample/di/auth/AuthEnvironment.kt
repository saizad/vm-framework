package com.vm.frameworkexample.di.auth

import com.vm.framework.*
import com.vm.frameworkexample.api.AuthApi
import io.reactivex.subjects.BehaviorSubject
import sa.zad.easypermission.PermissionManager
import javax.inject.Inject

open class AuthEnvironment @Inject constructor(
    val api: AuthApi,
    fcmToken: FCMToken,
    behaviorSubject: BehaviorSubject<ActivityResult<*>>,
    currentUser: CurrentUserType<*>,
    notifyOnceBehaviorSubject: BehaviorSubject<NotifyOnce<*>>,
    permissionManager: PermissionManager
) : Environment(
    fcmToken, behaviorSubject, currentUser, notifyOnceBehaviorSubject, permissionManager
)