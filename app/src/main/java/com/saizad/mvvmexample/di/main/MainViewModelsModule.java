package com.saizad.mvvmexample.di.main;

import androidx.lifecycle.ViewModel;

import com.saizad.mvvm.di.ViewModelKey;
import com.saizad.mvvmexample.components.home.AppMainViewModel;
import com.saizad.mvvmexample.components.home.main.MainViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class MainViewModelsModule {

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel.class)
    public abstract ViewModel mainViewModel(MainViewModel mainViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(AppMainViewModel.class)
    public abstract ViewModel appMainViewModel(AppMainViewModel appMainViewModel);
}




