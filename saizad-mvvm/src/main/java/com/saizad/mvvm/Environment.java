package com.saizad.mvvm;

import org.jetbrains.annotations.NotNull;

import io.reactivex.subjects.BehaviorSubject;
import sa.zad.easypermission.PermissionManager;

public class Environment {

    private final FCMToken fcmToken;
    private final BehaviorSubject<ActivityResult<?>> behaviorSubject;
    private final CurrentUser currentUser;
    private final BehaviorSubject<NotifyOnce<?>> notification;
    private final PermissionManager permissionManager;

    public Environment(FCMToken fcmToken, BehaviorSubject<ActivityResult<?>> behaviorSubject, CurrentUser currentUser,
                       BehaviorSubject<NotifyOnce<?>> notification, PermissionManager permissionManager) {
        this.fcmToken = fcmToken;
        this.behaviorSubject = behaviorSubject;
        this.currentUser = currentUser;
        this.notification = notification;
        this.permissionManager = permissionManager;
    }

    @NotNull
    public FCMToken fcmToken() {
        return fcmToken;
    }

    @NotNull
    public BehaviorSubject<ActivityResult<?>> navigationFragmentResult() {
        return behaviorSubject;
    }

    @NotNull
    public CurrentUser currentUser() {
        return currentUser;
    }

    public BehaviorSubject<NotifyOnce<?>> getNotification() {
        return notification;
    }

    @NotNull
    public PermissionManager permissionManager() {
        return permissionManager;
    }
}
