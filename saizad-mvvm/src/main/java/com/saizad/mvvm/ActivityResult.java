package com.saizad.mvvm;

import android.app.Activity;

import androidx.annotation.Nullable;

public class ActivityResult<V> {

    private final int requestCode;
    private final int resultCode;
    @Nullable
    private V value;

    public ActivityResult(final int requestCode, final @Nullable V value) {
        this(requestCode, OK(), value);
    }

    public ActivityResult(final int requestCode, final int resultCode,
                          final @Nullable V value) {
        this.resultCode = resultCode;
        this.requestCode = requestCode;
        this.value = value;
    }

    public static int OK() {
        return Activity.RESULT_OK;
    }

    public boolean isCanceled() {
        return resultCode == Activity.RESULT_CANCELED;
    }

    public boolean isOk() {
        return resultCode == OK();
    }

    public boolean isRequestCode(final int requestCode) {
        return this.requestCode == requestCode;
    }

    @Nullable
    public V getValue() {
        return value;
    }
}