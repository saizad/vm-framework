package com.vm.frameworkexample.service;

import com.google.gson.Gson;
import com.vm.framework.FCMToken;
import com.vm.framework.NotifyOnce;
import com.vm.framework.service.VmFrameworkFirebaseMessagingService;
import com.vm.frameworkexample.components.auth.AuthActivity;

import javax.inject.Inject;
import javax.inject.Named;

import io.reactivex.subjects.BehaviorSubject;

public class FCMService extends VmFrameworkFirebaseMessagingService {

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
    public boolean jsonString(String json, String type) {
        return false;
    }

    @Override
    public int sound() {
        return 0;
    }

    @Override
    public int smallIcon() {
        return 0;
    }
}
