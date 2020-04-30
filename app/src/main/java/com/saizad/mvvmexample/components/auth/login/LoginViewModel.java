package com.saizad.mvvmexample.components.auth.login;


import com.saizad.mvvmexample.di.auth.AuthEnvironment;
import com.saizad.mvvmexample.components.auth.AuthViewModel;

import javax.inject.Inject;

public class LoginViewModel extends AuthViewModel {

    @Inject
    public LoginViewModel(AuthEnvironment authEnvironment) {
        super(authEnvironment);
    }
}
