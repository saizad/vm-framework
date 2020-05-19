package com.saizad.mvvmexample.components.main;

import com.saizad.mvvmexample.di.main.MainEnvironment;

import javax.inject.Inject;

public class MainActivityViewModel extends MainViewModel {

    @Inject
    public MainActivityViewModel(MainEnvironment mainEnvironment) {
        super(mainEnvironment);
    }
}
