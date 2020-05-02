package com.saizad.mvvmexample.components.main;

import com.saizad.mvvmexample.di.main.MainEnvironment;

import javax.inject.Inject;

public class MVVMExampleMainActivityViewModel extends MVVMExampleMainViewModel {

    @Inject
    public MVVMExampleMainActivityViewModel(MainEnvironment mainEnvironment) {
        super(mainEnvironment);
    }
}
