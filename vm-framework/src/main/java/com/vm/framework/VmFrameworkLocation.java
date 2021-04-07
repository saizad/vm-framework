package com.vm.framework;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.os.Looper;

import androidx.annotation.IntDef;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.WorkerThread;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.Task;
import com.sa.easyandroidform.ObjectUtils;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.BiFunction;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;
import rx.functions.Action1;


@SuppressLint("MissingPermission")
public class VmFrameworkLocation {

    private static final long INTERVAL = 2000;
    private static final long FASTEST_INTERVAL = 1000;
    private static final @LocationPriorities
    int LOCATION_REQUEST_PRIORITY = LocationRequest.PRIORITY_HIGH_ACCURACY;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private Location latestLocation;

    public VmFrameworkLocation(@NonNull Context context) {
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context);
    }

    public void getLastLocation(Action1<Location> locationAction) {
        getLastLocation(locationAction, throwable -> {

        });
    }

    public void getLastLocation(Action1<Location> locationAction, Action1<Throwable> throwableAction) {
        if (ObjectUtils.isNull(latestLocation)) {
            getLocationObservable()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(locationAction::call, throwableAction::call);
            return;
        }
        locationAction.call(latestLocation);
    }

    @WorkerThread
    public Location getBlockingLocation() throws InterruptedException, GPSOffException {
        return getBlockingLocation(new Builder().build());
    }

    @WorkerThread
    public Location getBlockingLocation(@NonNull LocationRequest locationRequest) throws InterruptedException, GPSOffException {
        final FutureLocation locationListener = new FutureLocation();
        getLocation(locationListener, locationRequest, Looper.getMainLooper());
        return locationListener.get();
    }

    public void getLocation(@NonNull final LocationListener locationListener) throws GPSOffException {
        getLocation(locationListener, new Builder().build());
    }

    public void getLocation(@NonNull final LocationListener locationListener, @NonNull LocationRequest locationRequest) throws GPSOffException {
        getLocation(locationListener, new Builder().build(), null);
    }

    public void getLocation(@NonNull final LocationListener locationListener, @Nullable Looper looper) {
        try {
            getLocation(locationListener, new Builder().build(), looper);
        } catch (GPSOffException e) {
            e.printStackTrace();
        }
    }

    public void getLocation(@NonNull final LocationListener locationListener, @NonNull LocationRequest locationRequest, @Nullable Looper looper) throws GPSOffException {
        requestLocation(locationRequest, (location, locationCallback) -> {
            locationListener.onLocationChanged(location);
            return false;
        }, looper);
    }

    public Observable<Location> getLocationObservable() {
        return getLocationObservable(new Builder().build());
    }

    public Observable<Location> getLocationObservable(@NonNull LocationRequest locationRequest) {
        return getLocationObservable(locationRequest, null);
    }

    public Observable<Location> getLocationObservable(@NonNull LocationRequest locationRequest, @Nullable Looper looper) {
        PublishSubject<Location> publishSubject = PublishSubject.create();
        try {
            requestLocation(locationRequest, (location, locationCallback) -> {
                publishSubject.onNext(location);
                return false;
            }, looper).addOnFailureListener(publishSubject::onError);
        } catch (SecurityException e) {
            publishSubject.onError(e);
        } catch (GPSOffException e){

        }
        return publishSubject.take(1);
    }

    public void startLocationUpdates(@NonNull Activity activity, @NonNull LocationListener locationListener) throws GPSOffException {
        startLocationUpdates(activity, locationListener, new Builder().build());
    }

    public void startLocationUpdates(@NonNull final Activity activity,
                                     @NonNull final LocationListener locationListener,
                                     @NonNull LocationRequest locationRequest) throws GPSOffException {
        requestLocation(locationRequest, (location, locationCallback) -> {
            if (activity.isDestroyed() || activity.isFinishing()) {
                mFusedLocationProviderClient.removeLocationUpdates(locationCallback);
            } else {
                locationListener.onLocationChanged(location);
            }
            return true;
        }, null);
    }

    private Task<Void> requestLocation(@NonNull LocationRequest locationRequest, BiFunction<Location, LocationCallback, Boolean> locationAction, @Nullable Looper looper) throws GPSOffException {
        final Context context = mFusedLocationProviderClient.getApplicationContext();
        final LocationManager systemService = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        if (systemService != null && !systemService.isProviderEnabled(LocationManager.GPS_PROVIDER)){
            throw new GPSOffException("GPS is Disabled");
        }
        return mFusedLocationProviderClient.requestLocationUpdates(locationRequest,
                new LocationCallback() {
                    @Override
                    public void onLocationResult(LocationResult locationResult) {
                        super.onLocationResult(locationResult);
                        latestLocation = locationResult.getLastLocation();
                        try {
                            if (!locationAction.apply(locationResult.getLastLocation(), this)) {
                                mFusedLocationProviderClient.removeLocationUpdates(this);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, looper).addOnFailureListener(Throwable::printStackTrace);
    }

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({
            LocationRequest.PRIORITY_HIGH_ACCURACY, LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY,
            LocationRequest.PRIORITY_LOW_POWER, LocationRequest.PRIORITY_NO_POWER
    })
    public @interface LocationPriorities {
    }

    public static class Builder {
        private long interval = INTERVAL, fastInterval = FASTEST_INTERVAL;
        private @LocationPriorities
        int priority = LOCATION_REQUEST_PRIORITY;

        public Builder interval(long interval) {
            this.interval = interval;
            return this;
        }

        public Builder fastInterval(long fastInterval) {
            this.fastInterval = fastInterval;
            return this;
        }

        public Builder priority(@LocationPriorities int priority) {
            this.priority = priority;
            return this;
        }

        public LocationRequest build() {
            return new LocationRequest().setInterval(interval)
                    .setFastestInterval(fastInterval)
                    .setPriority(priority);
        }
    }

    public static class GPSOffException extends Exception{
        public GPSOffException(String message) {
            super(message);
        }
    }
}