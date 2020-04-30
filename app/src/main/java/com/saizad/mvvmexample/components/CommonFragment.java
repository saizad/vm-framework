package com.saizad.mvvmexample.components;

import androidx.annotation.NonNull;

import com.saizad.mvvm.SaizadLocation;
import com.saizad.mvvm.CurrentUser;
import com.saizad.mvvm.Environment;
import com.saizad.mvvm.ViewModelProviderFactory;
import com.saizad.mvvm.components.SaizadBaseFragment;

import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;

abstract public class CommonFragment<VM extends CommonViewModel> extends SaizadBaseFragment<VM> {

    @Inject
    SaizadLocation saizadLocation;

    @Inject
    public Environment mainEnvironment;

    @Inject
    public ViewModelProviderFactory viewModelProviderFactory;

    @NonNull
    @Override
    public SaizadLocation appLocation() {
        return saizadLocation;
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

    protected CurrentUser currentUser() {
        return mainEnvironment.currentUser();
    }
}
