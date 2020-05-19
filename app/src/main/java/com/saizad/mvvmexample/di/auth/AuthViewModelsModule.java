package com.saizad.mvvmexample.di.auth;


import androidx.lifecycle.ViewModel;

import com.saizad.mvvm.di.ViewModelKey;
import com.saizad.mvvmexample.components.auth.AuthActivityViewModel;
import com.saizad.mvvmexample.components.auth.login.LoginViewModel;
import com.saizad.mvvmexample.components.auth.splash.SplashViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class AuthViewModelsModule {

    @Binds
    @IntoMap
    @ViewModelKey(SplashViewModel.class)
    public abstract ViewModel splashViewModel(SplashViewModel splashViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(LoginViewModel.class)
    public abstract ViewModel loginViewModel(LoginViewModel loginViewModel);


    @Binds
    @IntoMap
    @ViewModelKey(AuthActivityViewModel.class)
    public abstract ViewModel authActivityViewModel(AuthActivityViewModel authActivityViewModel);
}
