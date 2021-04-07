package com.vm.framework;

import androidx.annotation.CallSuper;

import com.facebook.stetho.Stetho;

import net.danlew.android.joda.JodaTimeAndroid;

public abstract class VmFrameworkApplication extends MultiDexApplication {

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
