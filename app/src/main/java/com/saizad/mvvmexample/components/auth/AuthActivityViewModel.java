package com.saizad.mvvmexample.components.auth;

import com.saizad.mvvmexample.di.auth.AuthEnvironment;

import javax.inject.Inject;

public class AuthActivityViewModel extends AuthViewModel {

    @Inject
    public AuthActivityViewModel(AuthEnvironment authEnvironment) {
        super(authEnvironment);
    }
}
