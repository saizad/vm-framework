package com.saizad.mvvm.delegation.fragment;

import androidx.annotation.LayoutRes;

import com.saizad.mvvm.components.SaizadBaseViewModel;
import com.saizad.mvvm.delegation.BaseCB;

public interface FragmentCB<V extends SaizadBaseViewModel> extends BaseCB<V> {

    @LayoutRes
    int layoutRes();

    boolean persistView();
}
