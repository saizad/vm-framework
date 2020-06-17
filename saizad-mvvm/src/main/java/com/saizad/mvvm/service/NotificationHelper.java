package com.saizad.mvvm.service;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.AudioAttributes;
import android.media.RingtoneManager;
import android.net.Uri;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RawRes;
import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.RemoteMessage;
import com.saizad.mvvm.BaseNotificationModel;


public class NotificationHelper {


    public static void createNotification(@Nullable String channelId, @DrawableRes int smallIcon, @RawRes int sound, Context context, BaseNotificationModel baseNotificationModel, PendingIntent pendingIntent) {

        NotificationCompat.Builder mBuilder;

        mBuilder = new NotificationCompat.Builder(context, context.getPackageName());

        mBuilder
                .setSmallIcon(smallIcon)
                .setContentTitle(baseNotificationModel.getTitle())
                .setContentText(baseNotificationModel.getBody())
                .setAutoCancel(false)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setContentIntent(pendingIntent);

        NotificationManager mNotificationManager;

        Uri soundUri = sound == 0 ? RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION):  Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + context.getPackageName() + "/" + sound);

        mNotificationManager = channelized(soundUri, context, mBuilder, channelId);

        assert mNotificationManager != null;
        mNotificationManager.notify(baseNotificationModel.id(), mBuilder.build());
    }

    public static void createNotification(@Nullable String channelId, @DrawableRes int smallIcon, @RawRes int sound, Context context, BaseNotificationModel baseNotificationModel, Class<?> cl) {
        Intent intent = new Intent(context, cl);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        createNotification(channelId, smallIcon, sound, context, baseNotificationModel, PendingIntent.getActivity(context, 0, intent,
                PendingIntent.FLAG_UPDATE_CURRENT));
    }

    public static void createNotification(@DrawableRes int smallIcon, @RawRes int sound, Context context, @NonNull RemoteMessage.Notification notification, Class<?> cl) {
        Intent intent = new Intent(context, cl);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        createNotification(smallIcon, sound, context, notification, PendingIntent.getActivity(context, 0, intent,
                PendingIntent.FLAG_UPDATE_CURRENT));
    }

    public static void createNotification(@DrawableRes int smallIcon, @RawRes int sound, Context context, @NonNull RemoteMessage.Notification notification, PendingIntent pendingIntent) {

        NotificationCompat.Builder mBuilder;
        mBuilder = new NotificationCompat.Builder(context, context.getPackageName());

        Uri soundUri = sound == 0 ? RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION):  Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + context.getPackageName() + "/" + sound);

        mBuilder
                .setSmallIcon(smallIcon)
                .setContentTitle(notification.getTitle())
                .setContentText(notification.getBody())
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setAutoCancel(false)
                .setContentIntent(pendingIntent);

        NotificationManager mNotificationManager;
        final String channelId = "General";
        mNotificationManager = channelized(soundUri, context, mBuilder, channelId);

        assert mNotificationManager != null;
        mNotificationManager.notify(0 /* Request Code */, mBuilder.build());
    }

    private static NotificationManager channelized(Uri sound, Context context, NotificationCompat.Builder builder, @Nullable String channelId) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_HIGH;
            AudioAttributes attributes = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                    .build();

            channelId = channelId == null ? "General" : channelId;

            NotificationManager mNotificationManager = context.getSystemService(NotificationManager.class);
            NotificationChannel existingChannel = notificationManager.getNotificationChannel(channelId);
            //it will delete existing channel if it exists
            if (existingChannel != null) {
                mNotificationManager.deleteNotificationChannel(channelId);
            }

            NotificationChannel notificationChannel = new NotificationChannel(channelId, channelId, importance);

            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.enableVibration(true);
            notificationChannel.setSound(sound, attributes);
            notificationChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            assert notificationManager != null;
            builder.setChannelId(channelId);
            notificationManager.createNotificationChannel(notificationChannel);
        }
        return notificationManager;
    }


}
