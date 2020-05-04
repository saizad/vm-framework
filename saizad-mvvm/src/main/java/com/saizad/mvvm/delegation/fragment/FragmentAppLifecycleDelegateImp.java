package com.saizad.mvvm.delegation.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.annotation.CallSuper;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.saizad.mvvm.ActivityResult;
import com.saizad.mvvm.components.SaizadBaseViewModel;
import com.saizad.mvvm.delegation.BaseLifecycleDelegateImp;

import io.reactivex.disposables.CompositeDisposable;

public final class FragmentAppLifecycleDelegateImp<V extends SaizadBaseViewModel> extends BaseLifecycleDelegateImp<V, FragmentCB<V>> implements FragmentAppLifecycleDelegate {

    private boolean hasInitializedRootView = false;
    private View rootView = null;
    private final FragmentAppLifecycleCallBack fragmentAppLifecycleCallBack;

    public FragmentAppLifecycleDelegateImp(FragmentAppLifecycleCallBack fragmentAppLifecycleCallBack, FragmentCB<V> fragmentAppLifecycleDelegate, String tag) {
        super(fragmentAppLifecycleDelegate, tag);
        this.fragmentAppLifecycleCallBack = fragmentAppLifecycleCallBack;
    }

    private View getPersistentView(LayoutInflater layoutInflater, ViewGroup container, Bundle savedInstanceState, int layout) {
        if (rootView == null || !appLifecycleDelegate.persistView()) {
            rootView = layoutInflater.inflate(layout, container, false);
        } else {
            final ViewParent parent = rootView.getParent();
            if (parent != null) {
                ((ViewGroup) parent).removeView(rootView);
            }
        }

        return rootView;
    }

    public final <T> void finishWithResult(ActivityResult<T> activityResult) {
        getNavigationFragmentResult().onNext(activityResult);
        finish();
    }

    public void finish() {
        navController().popBackStack();
    }

    @CallSuper
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        log("onActivityCreated");
        viewModel.errorLiveData().observe(appLifecycleDelegate.getViewLifecycleOwner(), errorData -> {
            if (!errorData.isDiscarded()) {
                requestError(errorData);
            }
        });
        viewModel.apiErrorLiveData().observe(appLifecycleDelegate.getViewLifecycleOwner(), apiErrorData -> {
            if (!apiErrorData.isDiscarded()) {
                requestApiError(apiErrorData);
            }
        });
        viewModel.loadingLiveData().observe(appLifecycleDelegate.getViewLifecycleOwner(), loadingData -> {
            if (!loadingData.isDiscarded()) {
                requestLoading(loadingData);
            }
        });
    }

    @CallSuper
    @Override
    public void onAttach(Context context) {
        log("onAttach");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragmentAppLifecycleCallBack.setHasOptionsMenu(appLifecycleDelegate.menRes() != 0);
    }

    @Nullable
    @Override
    @CallSuper
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        log("onCreateView");
        return getPersistentView(inflater, container, savedInstanceState, appLifecycleDelegate.layoutRes());
    }

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        log("onViewCreated");
        compositeDisposable = new CompositeDisposable();
        viewModel.onViewCreated();
        fragmentAppLifecycleCallBack.onViewCreated(view, savedInstanceState, hasInitializedRootView);
        if(!hasInitializedRootView){
            hasInitializedRootView = appLifecycleDelegate.persistView();
        }

    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(appLifecycleDelegate.menRes(), menu);
    }

    @Override
    @CallSuper
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        log("onViewStateRestored");
    }

    @Override
    @CallSuper
    public void onDestroyView() {
        log("onDestroyView");
        compositeDisposable.dispose();
        loadingDialog.dismiss();
        viewModel.onDestroyView();
    }

    @Override
    @CallSuper
    public void onDetach() {
        log("onDetach");
    }
}
