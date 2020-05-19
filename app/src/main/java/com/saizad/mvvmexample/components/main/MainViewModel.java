package com.saizad.mvvmexample.components.main;


import com.saizad.mvvmexample.components.MVVMExampleViewModel;
import com.saizad.mvvmexample.di.main.MainEnvironment;

public abstract class MainViewModel extends MVVMExampleViewModel {

    public MainViewModel(MainEnvironment environment) {
        super(environment);
    }

}
