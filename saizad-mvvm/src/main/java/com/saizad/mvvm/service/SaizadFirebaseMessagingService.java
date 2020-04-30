package com.saizad.mvvm.service;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.WorkerThread;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.sa.easyandroidfrom.ObjectUtils;
import com.saizad.mvvm.CurrentUser;
import com.saizad.mvvm.FCMToken;
import com.saizad.mvvm.model.UserInfo;

import org.json.JSONObject;

import java.io.IOException;
import java.util.Map;

import dagger.android.AndroidInjection;


abstract public class SaizadFirebaseMessagingService extends FirebaseMessagingService {

    public static void updateToken(String token, FCMToken fcmToken, @NonNull CurrentUser currentUser, FcmCallBack callBack) {
        fcmToken.putToken(token);
        final UserInfo user = currentUser.getUser();
        if (ObjectUtils.isNotNull(user)) {
            callBack.updateToken(token);
        }
    }

    public static void requestAndUpdate(FCMToken fcmToken, CurrentUser currentUser, FcmCallBack callBack) {
        FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(
                task -> {
                    if (task.isSuccessful() && ObjectUtils.isNotNull(task.getResult())) {
                        final String token = task.getResult().getToken();
                        Log.d("Fcm_token", "requestFCMToken Received " + token);
                        updateToken(token, fcmToken, currentUser, callBack);
                    }
                });
    }

    @WorkerThread
    public static void deleteFcmToken() throws IOException {
        try {
            FirebaseInstanceId.getInstance().deleteInstanceId();
            Log.d("Fcm_token", "Deleted");
        } catch (IOException e) {
            Log.d("Fcm_token", "Fail to delete " + e.getMessage());
            throw e;
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        AndroidInjection.inject(this);
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
        if(notification == null) {
            final Map<String, String> data = remoteMessage.getData();
            Log.d("MessageReceived-Data", data.toString());
            final JSONObject jsonObject = new JSONObject(data);
            String notificationType = data.get("type");
            final String json = jsonObject.toString();
        } else {
            NotificationHelper.createNotification(notification);
        }
    }

    abstract public void updateToken(String token);

}
