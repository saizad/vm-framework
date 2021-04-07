package com.vm.frameworkexample.di.main

import com.vm.framework.*
import com.vm.frameworkexample.api.MainApi
import io.reactivex.subjects.BehaviorSubject
import sa.zad.easypermission.PermissionManager
import javax.inject.Inject

class MainEnvironment @Inject constructor(
    val api: MainApi,
    fcmToken: FCMToken,
    behaviorSubject: BehaviorSubject<ActivityResult<*>>,
    currentUser: CurrentUserType<*>,
    permissionManager: PermissionManager,
    notifyOnceBehaviorSubject: BehaviorSubject<NotifyOnce<*>>
) : Environment(
    fcmToken,
    behaviorSubject,
    currentUser,
    notifyOnceBehaviorSubject,
    permissionManager
)