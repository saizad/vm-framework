package com.vm.framework;

import android.location.Location;

import com.google.android.gms.location.LocationListener;

public class FutureLocation extends FutureImp<Location> implements LocationListener {

    @Override
    public void onLocationChanged(Location location) {
        setResult(location);
    }
}
