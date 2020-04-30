package com.saizad.mvvmexample.di.main;


import com.saizad.mvvm.ActivityResult;
import com.saizad.mvvm.CurrentUser;
import com.saizad.mvvm.Environment;
import com.saizad.mvvm.FCMToken;
import com.saizad.mvvm.NotifyOnce;
import com.saizad.mvvmexample.api.MainApi;

import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import io.reactivex.subjects.BehaviorSubject;
import sa.zad.easypermission.PermissionManager;

@Singleton
public class MainEnvironment extends Environment {

    private final MainApi mainApi;

    @Inject
    public MainEnvironment(FCMToken fcmToken, MainApi authApi, BehaviorSubject<ActivityResult<?>> behaviorSubject, CurrentUser currentUser, PermissionManager permissionManager, @Named("notification") BehaviorSubject<NotifyOnce<?>> notifyOnceBehaviorSubject) {
        super(fcmToken, behaviorSubject, currentUser, notifyOnceBehaviorSubject, permissionManager);
        this.mainApi = authApi;
    }

    @NotNull
    public MainApi api() {
        return mainApi;
    }


}
