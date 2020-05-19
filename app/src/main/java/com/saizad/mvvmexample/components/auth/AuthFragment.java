package com.saizad.mvvmexample.components.auth;


import com.saizad.mvvmexample.api.AuthApi;
import com.saizad.mvvmexample.components.MVVMExampleFragment;
import com.saizad.mvvmexample.di.auth.AuthEnvironment;

import javax.inject.Inject;


public abstract class AuthFragment<V extends AuthViewModel> extends MVVMExampleFragment<V> {

    @Inject
    public AuthEnvironment authEnvironment;

    protected AuthApi api() {
        return authEnvironment.api();
    }

}
