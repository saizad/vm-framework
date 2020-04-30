package com.saizad.mvvmexample.components.home;

import com.saizad.mvvmexample.di.main.MainEnvironment;

import javax.inject.Inject;

public class AppMainViewModel extends HomeViewModel {

    @Inject
    public AppMainViewModel(MainEnvironment mainEnvironment) {
        super(mainEnvironment);
    }
}
