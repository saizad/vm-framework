package com.saizad.mvvmexample.components.auth.splash;


import com.saizad.mvvmexample.di.auth.AuthEnvironment;
import com.saizad.mvvmexample.components.auth.AuthViewModel;

import javax.inject.Inject;

public class SplashViewModel extends AuthViewModel {

    @Inject
    public SplashViewModel(AuthEnvironment authEnvironment) {
        super(authEnvironment);
    }
}
