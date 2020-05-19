package com.saizad.mvvmexample.di.auth;


import com.saizad.mvvm.ActivityResult;
import com.saizad.mvvm.CurrentUserType;
import com.saizad.mvvm.Environment;
import com.saizad.mvvm.FCMToken;
import com.saizad.mvvm.NotifyOnce;
import com.saizad.mvvmexample.api.AuthApi;

import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import io.reactivex.subjects.BehaviorSubject;
import sa.zad.easypermission.PermissionManager;

@Singleton
public class AuthEnvironment extends Environment {

    private final AuthApi authApi;

    @Inject
    public AuthEnvironment(FCMToken fcmToken, AuthApi authApi, BehaviorSubject<ActivityResult<?>> behaviorSubject, CurrentUserType currentUser, @Named("notification") BehaviorSubject<NotifyOnce<?>> notifyOnceBehaviorSubject, PermissionManager permissionManager) {
        super(fcmToken, behaviorSubject, currentUser, notifyOnceBehaviorSubject, permissionManager);
        this.authApi = authApi;
    }

    @NotNull
    public AuthApi api() {
        return authApi;
    }
}
