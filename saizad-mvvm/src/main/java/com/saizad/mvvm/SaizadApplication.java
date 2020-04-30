package com.saizad.mvvm;

import androidx.annotation.CallSuper;

import com.facebook.stetho.Stetho;

import net.danlew.android.joda.JodaTimeAndroid;

public abstract class SaizadApplication extends MultiDexApplication {

    @CallSuper
    @Override
    public void onCreate() {
        super.onCreate();

        JodaTimeAndroid.init(this);

        Stetho.initialize(Stetho.newInitializerBuilder(this)
                .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this))
                .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                .build());
    }

}
