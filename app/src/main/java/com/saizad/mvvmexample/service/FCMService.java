package com.saizad.mvvmexample.service;

import com.google.gson.Gson;
import com.saizad.mvvm.CurrentUser;
import com.saizad.mvvm.FCMToken;
import com.saizad.mvvm.NotifyOnce;
import com.saizad.mvvm.service.SaizadFirebaseMessagingService;

import javax.inject.Inject;
import javax.inject.Named;

import io.reactivex.subjects.BehaviorSubject;

public class FCMService extends SaizadFirebaseMessagingService {

    @Inject
    FCMToken fcmToken;

    @Inject
    CurrentUser currentUser;


    @Inject
    @Named("notification")
    BehaviorSubject<NotifyOnce<?>> notification;

    @Inject
    Gson gson;

    @Override
    public void updateToken(String token) {

    }
}
