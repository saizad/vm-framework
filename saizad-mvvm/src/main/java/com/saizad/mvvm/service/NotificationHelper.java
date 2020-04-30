package com.saizad.mvvm.service;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;

import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.RemoteMessage;
import com.saizad.mvvm.BaseNotificationModel;
import com.saizad.mvvm.components.DrawerMainActivity;


public class NotificationHelper {

    public static final String ORDER_PROCESSING_NOTIFICATIONS = "order_processing_notifications";


    public static void createNotification(BaseNotificationModel baseNotificationModel, PendingIntent pendingIntent) {

        Context context = null;


        NotificationCompat.Builder mBuilder;

        mBuilder = new NotificationCompat.Builder(context, ORDER_PROCESSING_NOTIFICATIONS);

        mBuilder
//                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(baseNotificationModel.getTitle())
                .setContentText(baseNotificationModel.getBody())
                .setAutoCancel(false)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setContentIntent(pendingIntent);

        NotificationManager mNotificationManager;

        mNotificationManager = channelized(context, mBuilder);

        assert mNotificationManager != null;
        mNotificationManager.notify(0 /* Request Code */, mBuilder.build());
    }

    public static void createNotification(BaseNotificationModel baseNotificationModel) {
        Context context = null;
        Intent intent = new Intent(context, DrawerMainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        createNotification(baseNotificationModel, PendingIntent.getActivity(context, 0, intent,
                PendingIntent.FLAG_UPDATE_CURRENT));
    }

    public static void createNotification(RemoteMessage.Notification notification) {
        Context context = null;
        Intent intent = new Intent(context, DrawerMainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        createNotification(notification, PendingIntent.getActivity(context, 0, intent,
                PendingIntent.FLAG_UPDATE_CURRENT));
    }

    public static void createNotification(RemoteMessage.Notification notification, PendingIntent pendingIntent) {

        Context context = null;


        NotificationCompat.Builder mBuilder;

        mBuilder = new NotificationCompat.Builder(context, ORDER_PROCESSING_NOTIFICATIONS);

        mBuilder
//                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(notification.getTitle())
                .setContentText(notification.getBody())
                .setAutoCancel(false)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setContentIntent(pendingIntent);

        NotificationManager mNotificationManager;

        mNotificationManager = channelized(context, mBuilder);

        assert mNotificationManager != null;
        mNotificationManager.notify(0 /* Request Code */, mBuilder.build());
    }

    private static NotificationManager channelized(Context context, NotificationCompat.Builder builder) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel notificationChannel = new NotificationChannel(ORDER_PROCESSING_NOTIFICATIONS, "Order processing notifications", importance);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.enableVibration(true);
            notificationChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            assert notificationManager != null;
            builder.setChannelId(ORDER_PROCESSING_NOTIFICATIONS);
            notificationManager.createNotificationChannel(notificationChannel);
        }
        return notificationManager;
    }


}
