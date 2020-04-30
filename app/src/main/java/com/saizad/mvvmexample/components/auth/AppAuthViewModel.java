package com.saizad.mvvmexample.components.auth;

import com.saizad.mvvmexample.AuthEnvironment;

import javax.inject.Inject;

public class AppAuthViewModel extends AuthViewModel {

    @Inject
    public AppAuthViewModel(AuthEnvironment authEnvironment) {
        super(authEnvironment);
    }
}
