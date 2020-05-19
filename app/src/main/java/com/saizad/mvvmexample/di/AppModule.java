package com.saizad.mvvmexample.di;

import android.Manifest;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.saizad.mvvm.CurrentUserType;
import com.saizad.mvvm.SaizadEasyRetrofit;
import com.saizad.mvvm.di.SaizadDaggerAppModule;
import com.saizad.mvvmexample.BuildConfig;
import com.saizad.mvvmexample.MVVMExampleCurrentUser;
import com.saizad.mvvmexample.api.BackgroundApi;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import sa.zad.easypermission.AppPermission;
import sa.zad.easypermission.AppPermissionImp;
import sa.zad.easypermission.PermissionManager;

import static com.saizad.mvvmexample.RequestCodes.LOCATION_PERMISSION_REQUEST_CODE;
import static com.saizad.mvvmexample.RequestCodes.STORAGE_PERMISSION_REQUEST_CODE;


@Module
public class AppModule extends SaizadDaggerAppModule {


    private static final int MAX_REQUEST = 3;


    @Override
    public String domainURL() {
        return BuildConfig.DOMAIN_URL;
    }

    @Override
    public CurrentUserType currentUser(SharedPreferences sharedPreferences, Gson gson) {
        return new MVVMExampleCurrentUser(sharedPreferences, gson);
    }

    @Provides
    @Singleton
    protected PermissionManager providesPermissionManager(SharedPreferences sharedPreferences) {
        return new PermissionManager(storagePermission(sharedPreferences), locationPermission(sharedPreferences));
    }

    private AppPermission storagePermission(SharedPreferences sharedPreferences) {
        return new AppPermissionImp(STORAGE_PERMISSION_REQUEST_CODE, new String[]{
                Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE
        }, MAX_REQUEST, sharedPreferences);
    }

    private AppPermission locationPermission(SharedPreferences sharedPreferences) {
        return new AppPermissionImp(LOCATION_PERMISSION_REQUEST_CODE, new String[]{
                Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION
        }, MAX_REQUEST, sharedPreferences);
    }

    @Singleton
    @Provides
    protected BackgroundApi providesBackgroundApi(SaizadEasyRetrofit saizadEasyRetrofit) {
        return saizadEasyRetrofit.provideRetrofit().create(BackgroundApi.class);
    }
}
















