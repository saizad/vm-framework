package com.saizad.mvvmexample.di;

import android.app.Application;

import com.saizad.mvvm.di.ViewModelFactoryModule;
import com.saizad.mvvmexample.MVVMExampleApplication;
import com.saizad.mvvmexample.WorkerModule;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

@Singleton
@Component(
        modules = {
                AndroidSupportInjectionModule.class,
                ActivityBuildersModule.class,
                AppModule.class,
                ViewModelFactoryModule.class,
                AppWorkerFactoryModule.class,
                WorkerModule.class
        }
)
public interface AppComponent extends AndroidInjector<MVVMExampleApplication> {

    @Component.Builder
    interface Builder {

        @BindsInstance
        Builder application(Application application);

        AppComponent build();
    }
}







