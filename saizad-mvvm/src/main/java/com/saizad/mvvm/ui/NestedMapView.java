package com.saizad.mvvm.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.MapView;

public class NestedMapView extends MapView {

    public NestedMapView(Context context) {
        super(context);
    }

    public NestedMapView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NestedMapView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public NestedMapView(Context context, GoogleMapOptions options) {
        super(context, options);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        getParent().requestDisallowInterceptTouchEvent(true);
        return super.dispatchTouchEvent(ev);
    }
}