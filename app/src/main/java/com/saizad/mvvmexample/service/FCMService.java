package com.saizad.mvvmexample.service;

import com.google.gson.Gson;
import com.saizad.mvvm.FCMToken;
import com.saizad.mvvm.NotifyOnce;
import com.saizad.mvvm.service.SaizadFirebaseMessagingService;
import com.saizad.mvvmexample.components.auth.AuthActivity;

import javax.inject.Inject;
import javax.inject.Named;

import io.reactivex.subjects.BehaviorSubject;

public class FCMService extends SaizadFirebaseMessagingService {

    @Inject
    FCMToken fcmToken;

    @Inject
    @Named("notification")
    BehaviorSubject<NotifyOnce<?>> notification;

    @Inject
    Gson gson;

    @Override
    public void updateToken(String token) {

    }

    @Override
    public Class<?> aClass() {
        return AuthActivity.class;
    }

    @Override
    public int sound() {
        return 0;
    }
}
