package com.saizad.mvvmexample.di.main;

import androidx.lifecycle.ViewModel;

import com.saizad.mvvm.di.ViewModelKey;
import com.saizad.mvvmexample.components.main.MainActivityViewModel;
import com.saizad.mvvmexample.components.main.home.HomeViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class MainViewModelsModule {

    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel.class)
    public abstract ViewModel homeViewModel(HomeViewModel homeViewModel);


    @Binds
    @IntoMap
    @ViewModelKey(MainActivityViewModel.class)
    public abstract ViewModel mainActivityViewModel(MainActivityViewModel mainActivityViewModel);
}




