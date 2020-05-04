package com.saizad.mvvmexample.components.auth.splash;


import com.saizad.mvvmexample.components.auth.MVVMExampleAuthViewModel;
import com.saizad.mvvmexample.di.auth.AuthEnvironment;

import javax.inject.Inject;

public class SplashViewModel extends MVVMExampleAuthViewModel {

    @Inject
    public SplashViewModel(AuthEnvironment authEnvironment) {
        super(authEnvironment);
    }
}
