package com.saizad.mvvmexample.components.auth;


import com.saizad.mvvmexample.AuthEnvironment;
import com.saizad.mvvmexample.api.AuthApi;
import com.saizad.mvvmexample.components.CommonViewModel;

public abstract class AuthViewModel extends CommonViewModel {

    protected final AuthApi authApi;

    public AuthViewModel(AuthEnvironment authEnvironment) {
        super(authEnvironment);
        this.authApi = authEnvironment.api();
    }

}
