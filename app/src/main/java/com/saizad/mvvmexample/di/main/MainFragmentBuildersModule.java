package com.saizad.mvvmexample.di.main;


import com.saizad.mvvmexample.components.main.home.HomeFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class MainFragmentBuildersModule {

    @ContributesAndroidInjector
    abstract HomeFragment homeFragment();

}
