package com.saizad.mvvmexample.di.main;


import com.saizad.mvvmexample.components.home.main.MainFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class MainFragmentBuildersModule {

    @ContributesAndroidInjector
    abstract MainFragment mainFragment();

}
