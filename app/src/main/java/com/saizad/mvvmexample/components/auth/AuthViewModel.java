package com.saizad.mvvmexample.components.auth;


import com.saizad.mvvmexample.api.AuthApi;
import com.saizad.mvvmexample.components.MVVMExampleViewModel;
import com.saizad.mvvmexample.di.auth.AuthEnvironment;

public abstract class AuthViewModel extends MVVMExampleViewModel {

    protected final AuthApi authApi;

    public AuthViewModel(AuthEnvironment authEnvironment) {
        super(authEnvironment);
        this.authApi = authEnvironment.api();
    }

}
