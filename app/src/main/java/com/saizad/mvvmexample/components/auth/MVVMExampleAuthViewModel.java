package com.saizad.mvvmexample.components.auth;


import com.saizad.mvvmexample.api.AuthApi;
import com.saizad.mvvmexample.components.MVVMExampleViewModel;
import com.saizad.mvvmexample.di.auth.AuthEnvironment;

public abstract class MVVMExampleAuthViewModel extends MVVMExampleViewModel {

    protected final AuthApi authApi;

    public MVVMExampleAuthViewModel(AuthEnvironment authEnvironment) {
        super(authEnvironment);
        this.authApi = authEnvironment.api();
    }

}
