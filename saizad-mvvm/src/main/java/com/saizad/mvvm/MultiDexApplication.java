package com.saizad.mvvm;

import android.content.Context;
import androidx.annotation.CallSuper;
import androidx.multidex.MultiDex;
import dagger.android.DaggerApplication;

public abstract class MultiDexApplication extends DaggerApplication {

    @Override
    @CallSuper
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(newBase);
        MultiDex.install(this);
    }

}

