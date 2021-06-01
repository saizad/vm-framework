package com.vm.framework.service;

import android.util.Log;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.RawRes;
import androidx.annotation.WorkerThread;

import com.google.firebase.installations.FirebaseInstallations;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.sa.easyandroidform.ObjectUtils;
import com.vm.framework.FCMToken;

import org.json.JSONObject;

import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;
import sa.zad.easyretrofit.observables.NeverErrorObservable;


abstract public class VmFrameworkFirebaseMessagingService extends FirebaseMessagingService {


    public static <T> void updateToken(String token, FCMToken fcmToken, @NonNull NeverErrorObservable<T> fcmRegister) {
        fcmToken.putToken(token);
        fcmRegister
                .exception(throwable -> {
                    Log.d("Fcm_token", "Error updating token " + throwable.getMessage());
                })
                .subscribe(dataModel -> {
                    Log.d("Fcm_token", "onNewToken Update");
                });
    }

    public static Observable<String> requestAndUpdate() {
        PublishSubject<String> tokenSubject = PublishSubject.create();
        FirebaseInstallations.getInstance().getToken(false).addOnCompleteListener(task -> {
            if (task.isSuccessful() && ObjectUtils.isNotNull(task.getResult())) {
                final String token = task.getResult().getToken();
                Log.d("Fcm_token", "requestFCMToken Received " + token);
                tokenSubject.onNext(token);
            }
        });
        return tokenSubject;
    }


    @WorkerThread
    public static void deleteFcmToken() {
        FirebaseMessaging.getInstance().deleteToken();
        Log.d("Fcm_token", "Deleted");
    }

    @Override
    public void onNewToken(String s) {
        Log.d("Fcm_token", "onNewToken Received " + s);
        updateToken(s);
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.d("MessageReceived", remoteMessage.getMessageId());
        final RemoteMessage.Notification notification = remoteMessage.getNotification();
        if (notification == null) {
            final Map<String, String> data = remoteMessage.getData();
            Log.d("MessageReceived-Data", data.toString());
            final JSONObject jsonObject = new JSONObject(data);
            String notificationType = data.get("type");
            final String json = jsonObject.toString();
            jsonString(json, notificationType);
        } else {
            NotificationHelper.createNotification(smallIcon(), sound(), getApplicationContext(), notification, aClass());
        }
    }

    abstract public void updateToken(String token);

    abstract public Class<?> aClass();

    abstract public boolean jsonString(String json, String type);

    abstract public @RawRes
    int sound();

    abstract public @DrawableRes
    int smallIcon();

}
