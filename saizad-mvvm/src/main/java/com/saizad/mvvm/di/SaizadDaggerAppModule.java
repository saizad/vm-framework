package com.saizad.mvvm.di;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.saizad.mvvm.ActivityResult;
import com.saizad.mvvm.CurrentUserType;
import com.saizad.mvvm.Environment;
import com.saizad.mvvm.FCMToken;
import com.saizad.mvvm.NotifyOnce;
import com.saizad.mvvm.SaizadEasyRetrofit;
import com.saizad.mvvm.SaizadLocation;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.reactivex.subjects.BehaviorSubject;
import sa.zad.easypermission.PermissionManager;

@Module
public abstract class SaizadDaggerAppModule {

    @Singleton
    @Provides
    public static SaizadLocation providesAppLocation(Application application) {
        return new SaizadLocation(application);
    }

    @Singleton
    @Provides
    public static FCMToken providesFCMToken(SharedPreferences sharedPreferences, Gson gson) {
        return new FCMToken(sharedPreferences, gson);
    }

    public abstract SaizadEasyRetrofit retrofit(Application application, CurrentUserType currentUser, Gson gson);

    @Singleton
    @Provides
    public SaizadEasyRetrofit providesRetrofit(Application application, CurrentUserType currentUser, Gson gson) {
        return retrofit(application, currentUser, gson);
    }

    public abstract String domainURL();

    @Singleton
    @Provides
    public static SharedPreferences providesSharedPreferences(Application application) {
        return PreferenceManager.getDefaultSharedPreferences(application);
    }

    abstract public CurrentUserType currentUser(SharedPreferences sharedPreferences, Gson gson);

    @Singleton
    @Provides
    public static Gson provideGson() {
        GsonBuilder gsonBuilder = new GsonBuilder().serializeNulls();
        gsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);
        return gsonBuilder.create();
    }

    @Singleton
    @Provides
    public static BehaviorSubject<ActivityResult<?>> provideFragmentNavigationResult() {
        return BehaviorSubject.create();
    }

    @Singleton
    @Provides
    @Named("notification")
    public static BehaviorSubject<NotifyOnce<?>> notificationResultBehaviorSubject() {
        return BehaviorSubject.create();
    }

    @Singleton
    @Provides
    public CurrentUserType providesCurrentUserType(SharedPreferences sharedPreferences, Gson gson) {
        return currentUser(sharedPreferences, gson);
    }

    @Singleton
    @Provides
    public static Environment providesEnvironment(FCMToken fcmToken, CurrentUserType currentUser, BehaviorSubject<ActivityResult<?>> navigationFragmentResult, @Named("notification") BehaviorSubject<NotifyOnce<?>> notifyOnceBehaviorSubject, PermissionManager permissionManager) {
        return new Environment(fcmToken, navigationFragmentResult, currentUser, notifyOnceBehaviorSubject, permissionManager);
    }

}