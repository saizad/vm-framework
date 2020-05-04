package com.saizad.mvvm.delegation;

import android.content.Context;

import androidx.annotation.MenuRes;
import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.navigation.NavController;

import com.saizad.mvvm.Environment;
import com.saizad.mvvm.SaizadLocation;
import com.saizad.mvvm.ViewModelProviderFactory;
import com.saizad.mvvm.components.SaizadBaseViewModel;

public interface BaseCB<V extends SaizadBaseViewModel> {
    @NonNull
    LifecycleOwner getViewLifecycleOwner();

    @NonNull
    Context context();

    @NonNull
    ViewModelStoreOwner viewModelStoreOwner();

    @NonNull
    ViewModelProviderFactory viewModelProviderFactory();

    @NonNull
    Class<V> getViewModelClassType();

    @NonNull
    SaizadLocation appLocation();

    @NonNull
    Environment environment();

    @MenuRes
    int menRes();

    NavController navController();
}
