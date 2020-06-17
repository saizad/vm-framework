package com.saizad.mvvm.components;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;

import androidx.annotation.CallSuper;
import androidx.annotation.DrawableRes;
import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.navigation.NavOptions;
import androidx.navigation.fragment.NavHostFragment;

import com.saizad.mvvm.ActivityResult;
import com.saizad.mvvm.delegation.activity.ActivityAppLifeCycleCallback;
import com.saizad.mvvm.delegation.activity.ActivityAppLifecycleDelegate;
import com.saizad.mvvm.delegation.activity.ActivityAppLifecycleDelegateImp;
import com.saizad.mvvm.delegation.activity.ActivityCB;

import org.jetbrains.annotations.NotNull;

import dagger.android.support.DaggerAppCompatActivity;
import io.reactivex.Scheduler;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.subjects.BehaviorSubject;
import rx.functions.Action1;

public abstract class SaizadBaseActivity<V extends SaizadBaseViewModel> extends DaggerAppCompatActivity implements ActivityAppLifecycleDelegate, ActivityCB<V>, ActivityAppLifeCycleCallback {

    protected final ActivityAppLifecycleDelegate delegate;

    public SaizadBaseActivity() {
        delegate = new ActivityAppLifecycleDelegateImp<>(this, this, getClass().getSimpleName());
    }

    @NonNull
    @Override
    public Context context() {
        return this;
    }

    @NonNull
    @Override
    public ViewModelStoreOwner viewModelStoreOwner() {
        return this;
    }

    @NonNull
    @Override
    public LifecycleOwner getViewLifecycleOwner() {
        return this;
    }

    @NotNull
    public abstract Class<V> getViewModelClassType();


    final public void showLongToast(CharSequence text) {
        delegate.showLongToast(text);
    }

    final public void showShortToast(Integer value) {
        delegate.showShortToast(value);
    }

    final public void showShortToast(CharSequence text) {
        delegate.showShortToast(text);
    }

    public void showToast(CharSequence text, int toastLength) {
        delegate.showToast(text, toastLength);
    }

    public void log(int integer) {
        delegate.log(integer);
    }

    public void log(String string) {
        delegate.log(string);
    }

    public final BehaviorSubject<ActivityResult<?>> getNavigationFragmentResult() {
        return delegate.getNavigationFragmentResult();
    }

    public Scheduler getSchedulerProviderUI() {
        return delegate.getSchedulerProviderUI();
    }

    public Scheduler getSchedulerProviderIO() {
        return delegate.getSchedulerProviderIO();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        delegate.onCreate(savedInstanceState);
        onViewReady();
    }

    @Override
    public final void onViewReady() {
        delegate.onViewReady();
    }

    public void requestError(@NonNull SaizadBaseViewModel.ErrorData errorData) {
        delegate.requestError(errorData);
    }

    public void requestApiError(@NonNull SaizadBaseViewModel.ApiErrorData apiErrorData) {
        delegate.requestApiError(apiErrorData);
    }

    public void requestLoading(@NonNull SaizadBaseViewModel.LoadingData loadingData) {
        delegate.requestLoading(loadingData);
    }

    @Override
    public void showLoading(boolean show) {
        delegate.showLoading(show);
    }

    public boolean serverError(@NonNull Throwable throwable, int requestId) {
        return delegate.serverError(throwable, requestId);
    }

    @CallSuper
    @Override
    public void onPause() {
        super.onPause();
        delegate.onPause();
    }

    @Override
    @CallSuper
    public void onStart() {
        super.onStart();
        delegate.onStart();
    }

    @Override
    @CallSuper
    public void onStop() {
        super.onStop();
        delegate.onStop();
    }

    @Override
    @CallSuper
    public void onDestroy() {
        super.onDestroy();
        delegate.onDestroy();
    }

    @Override
    @CallSuper
    public void onResume() {
        super.onResume();
        delegate.onResume();
    }

    public void requestLocation(Action1<Location> locationAction) {
        delegate.requestLocation(locationAction);
    }

    public LiveData<Integer> showAlertDialogOk(String title, String message, boolean cancelAble) {
        return delegate.showAlertDialogOk(title, message, cancelAble);
    }

    public LiveData<Integer> showAlertDialogOk(String title, String message) {
        return delegate.showAlertDialogOk(title, message, false);
    }

    public LiveData<Integer> showAlertDialogYesNo(String title, String message, @DrawableRes int icon) {
        return delegate.showAlertDialogYesNo(title, message, icon);
    }

    public LiveData<Integer> showAlertDialogYesNo(String title, String message, @DrawableRes int icon, String positiveName, String negativeName) {
        return delegate.showAlertDialogYesNo(title, message, icon, positiveName, negativeName);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        environment().navigationFragmentResult().onNext(new ActivityResult<>(requestCode, resultCode, data));
    }

    @Override
    public @NonNull
    V viewModel() {
        return (V) delegate.viewModel();
    }

    @NotNull
    @Override
    public CompositeDisposable compositeDisposable() {
        return delegate.compositeDisposable();
    }

    @Override
    public int menRes() {
        return 0;
    }

    public void openClosableFragment(@IdRes int fragment) {
        delegate.openClosableFragment(fragment);
    }

    public void openClosableFragment(@IdRes int fragment, @Nullable Bundle bundle) {
        delegate.openClosableFragment(fragment, bundle);
    }

    public void openClosableFragment(@IdRes int fragment, @Nullable Bundle bundle, @Nullable NavOptions navOptions) {
        delegate.openClosableFragment(fragment, bundle, navOptions);
    }

    @CallSuper
    @Override
    public void onBackPressed() {
        final NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().getPrimaryNavigationFragment();
        final Fragment primaryNavigationFragment = navHostFragment.getChildFragmentManager().getPrimaryNavigationFragment();
        if (primaryNavigationFragment instanceof SaizadBaseFragment<?>) {
            if (!((SaizadBaseFragment<?>) primaryNavigationFragment).onBackPressed()) {
                super.onBackPressed();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        environment().permissionManager().onRequestPermissionsResult(this, requestCode, permissions, grantResults);
    }
}
