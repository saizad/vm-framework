package com.saizad.mvvm.delegation.fragment;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.saizad.mvvm.delegation.BaseLifecycleCallBack;

public interface FragmentAppLifecycleCallBack extends BaseLifecycleCallBack {
    void onViewCreated(View view, @Nullable Bundle savedInstanceState, boolean recycled);
}
