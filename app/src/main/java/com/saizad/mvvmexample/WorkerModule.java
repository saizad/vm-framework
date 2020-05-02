package com.saizad.mvvmexample;

import com.squareup.inject.assisted.dagger2.AssistedModule;

import dagger.Module;

// do not move this module out of this package
@AssistedModule
@Module(includes = AssistedInject_WorkerModule.class)
abstract public class WorkerModule {
}