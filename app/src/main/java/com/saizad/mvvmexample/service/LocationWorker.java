package com.saizad.mvvmexample.service;

import android.content.Context;
import android.location.Location;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.WorkerParameters;

import com.saizad.mvvm.SaizadLocation;
import com.saizad.mvvm.service.BaseLocationWorker;
import com.saizad.mvvmexample.RequestCodes;
import com.squareup.inject.assisted.Assisted;
import com.squareup.inject.assisted.AssistedInject;

import com.saizad.mvvm.di.worker.ChildWorkerFactory;
import sa.zad.easypermission.PermissionManager;

public class LocationWorker extends BaseLocationWorker {

    @AssistedInject
    public LocationWorker(@Assisted @NonNull Context arg0, @Assisted @NonNull WorkerParameters arg1, SaizadLocation saizadLocation, PermissionManager permissionManager) {
        super(arg0, arg1, saizadLocation, permissionManager.getAppPermission(RequestCodes.LOCATION_PERMISSION_REQUEST_CODE));
    }

    @Override
    protected Result locationDoWork(Location location) {
        Log.d("Location", location.toString());
        return Result.success();
    }

    @AssistedInject.Factory
    public interface Factory extends ChildWorkerFactory {}
}