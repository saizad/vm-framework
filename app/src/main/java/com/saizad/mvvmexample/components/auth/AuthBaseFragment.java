package com.saizad.mvvmexample.components.auth;


import com.saizad.mvvmexample.api.AuthApi;
import com.saizad.mvvmexample.components.CommonFragment;
import com.saizad.mvvmexample.di.auth.AuthEnvironment;

import javax.inject.Inject;


public abstract class AuthBaseFragment<V extends AuthViewModel> extends CommonFragment<V> {

    @Inject
    public AuthEnvironment authEnvironment;

    protected AuthApi api() {
        return authEnvironment.api();
    }

}
