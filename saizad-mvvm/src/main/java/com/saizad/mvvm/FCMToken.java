package com.saizad.mvvm;

import android.content.SharedPreferences;

import androidx.annotation.Nullable;

import com.google.gson.Gson;
import com.sa.easyandroidfrom.ObjectUtils;

import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;

public class FCMToken extends SaizadSharedPreferences {

    private static final String KEY_FCM_TOKEN = "fcm_token";
    private final BehaviorSubject<String> fcmTokenSubject = BehaviorSubject.create();

    public FCMToken(SharedPreferences sharedPreferences, Gson gson) {
        super(sharedPreferences, gson);
    }

    public void putToken(@Nullable String fcmToken){
        putValue(KEY_FCM_TOKEN, fcmToken);
        fcmTokenSubject.onNext(ObjectUtils.coalesce(fcmToken, ""));
    }

    public String getFcmToken(){
        return getValue(KEY_FCM_TOKEN);
    }

    public Observable<String> observeFcmToken(){
        return fcmTokenSubject.filter(s -> !s.isEmpty());
    }

    public Observable<String> observeFcmTokenRemoved(){
        return fcmTokenSubject.filter(String::isEmpty);
    }
}
