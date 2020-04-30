package com.saizad.mvvm.di.worker;

import androidx.work.WorkerFactory;

import dagger.Binds;
import dagger.Module;


@Module
public abstract class WorkerFactoryModule {

    @Binds
    public abstract WorkerFactory bindSampleWorkerFactory(InjectedWorkerFactory injectedWorkerFactory);

}
