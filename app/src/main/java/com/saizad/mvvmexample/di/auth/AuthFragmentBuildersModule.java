package com.saizad.mvvmexample.di.auth;


import com.saizad.mvvmexample.components.auth.login.LoginFragment;
import com.saizad.mvvmexample.components.auth.splash.SplashFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class AuthFragmentBuildersModule {

    @ContributesAndroidInjector
    abstract SplashFragment splashFragment();

    @ContributesAndroidInjector
    abstract LoginFragment loginFragment();


}
