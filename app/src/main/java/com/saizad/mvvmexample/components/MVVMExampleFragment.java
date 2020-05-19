package com.saizad.mvvmexample.components;

import androidx.annotation.NonNull;

import com.saizad.mvvm.SaizadLocation;
import com.saizad.mvvm.CurrentUserType;
import com.saizad.mvvm.Environment;
import com.saizad.mvvm.ViewModelProviderFactory;
import com.saizad.mvvm.components.SaizadBaseFragment;

import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;

abstract public class MVVMExampleFragment<VM extends MVVMExampleViewModel> extends SaizadBaseFragment<VM> {

    @Inject
    public Environment mainEnvironment;
    @Inject
    public ViewModelProviderFactory viewModelProviderFactory;
    @Inject
    SaizadLocation gpsLocation;

    @NonNull
    @Override
    public SaizadLocation appLocation() {
        return gpsLocation;
    }

    @NonNull
    @Override
    public ViewModelProviderFactory viewModelProviderFactory() {
        return viewModelProviderFactory;
    }

    @NotNull
    @Override
    public Environment environment() {
        return mainEnvironment;
    }

    protected CurrentUserType currentUser() {
        return mainEnvironment.currentUser();
    }
}
