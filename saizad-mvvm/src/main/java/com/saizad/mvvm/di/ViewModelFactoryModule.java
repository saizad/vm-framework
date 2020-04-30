package com.saizad.mvvm.di;

import androidx.lifecycle.ViewModelProvider;

import com.saizad.mvvm.ViewModelProviderFactory;

import dagger.Binds;
import dagger.Module;


@Module
public abstract class ViewModelFactoryModule {

    @Binds
    public abstract ViewModelProvider.Factory bindViewModelFactory(ViewModelProviderFactory viewModelFactory);

}
