package com.saizad.mvvmexample.di;

import com.saizad.mvvm.di.worker.ChildWorkerFactory;
import com.saizad.mvvm.di.worker.WorkerFactoryModule;
import com.saizad.mvvm.di.worker.WorkerKey;
import com.saizad.mvvmexample.service.LocationWorker;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;


@Module
public abstract class AppWorkerFactoryModule extends WorkerFactoryModule {

    @Binds
    @IntoMap
    @WorkerKey(LocationWorker.class)
    public abstract ChildWorkerFactory bindLocationWorker(LocationWorker.Factory factory);

}
