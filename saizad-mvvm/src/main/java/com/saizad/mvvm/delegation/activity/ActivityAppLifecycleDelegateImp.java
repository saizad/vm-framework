package com.saizad.mvvm.delegation.activity;


import com.saizad.mvvm.components.SaizadBaseViewModel;
import com.saizad.mvvm.delegation.BaseLifecycleDelegateImp;

public class ActivityAppLifecycleDelegateImp<V extends SaizadBaseViewModel> extends BaseLifecycleDelegateImp<V, ActivityCB<V>> implements ActivityAppLifecycleDelegate {

    public ActivityAppLifecycleDelegateImp(ActivityAppLifeCycleCallback activityAppLifeCycleCallback, ActivityCB<V> activityCB, String tag) {
        super(activityAppLifeCycleCallback, activityCB, tag);
    }
}
