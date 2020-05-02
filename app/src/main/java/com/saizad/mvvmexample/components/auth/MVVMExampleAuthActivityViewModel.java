package com.saizad.mvvmexample.components.auth;

import com.saizad.mvvmexample.di.auth.AuthEnvironment;

import javax.inject.Inject;

public class MVVMExampleAuthActivityViewModel extends MVVMExampleAuthViewModel {

    @Inject
    public MVVMExampleAuthActivityViewModel(AuthEnvironment authEnvironment) {
        super(authEnvironment);
    }
}
