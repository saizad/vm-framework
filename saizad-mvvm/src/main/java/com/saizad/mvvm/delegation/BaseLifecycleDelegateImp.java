package com.saizad.mvvm.delegation;


import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.CallSuper;
import androidx.annotation.DrawableRes;
import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;

import com.saizad.mvvm.ActivityResult;
import com.saizad.mvvm.LoadingDialog;
import com.saizad.mvvm.R;
import com.saizad.mvvm.SaizadLocation;
import com.saizad.mvvm.components.SaizadBaseViewModel;
import com.saizad.mvvm.model.ErrorModel;

import org.jetbrains.annotations.NotNull;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.BehaviorSubject;
import rx.functions.Action1;

public abstract class BaseLifecycleDelegateImp<V extends SaizadBaseViewModel, CB extends BaseCB<V>> implements BaseLifecycleDelegate {

    public final String tag;

    protected CompositeDisposable compositeDisposable;
    protected LoadingDialog loadingDialog;
    public V viewModel;
    protected final CB appLifecycleDelegate;

    public BaseLifecycleDelegateImp(CB appLifecycleDelegate, String tag) {
        this.appLifecycleDelegate = appLifecycleDelegate;
        this.tag = tag;
    }

    private V getFragmentViewModel(Class<V> viewModel) {
       return new ViewModelProvider(appLifecycleDelegate.viewModelStoreOwner(), appLifecycleDelegate.viewModelProviderFactory()).get(viewModel);
    }

    public final void showLongToast(CharSequence text) {
        showToast(text, Toast.LENGTH_LONG);
    }

    final public void showShortToast(Integer value) {
        showToast(String.valueOf(value), Toast.LENGTH_SHORT);
    }

    final public void showShortToast(CharSequence text) {
        showToast(text, Toast.LENGTH_SHORT);
    }

    final public void showToast(CharSequence text, int toastLength) {
        Toast.makeText(appLifecycleDelegate.context(), text, toastLength).show();
    }

    public void log(int integer) {
        log(String.valueOf(integer));
    }

    public void log(String string) {
        Log.i(tag, string);
    }

    public final BehaviorSubject<ActivityResult<?>> getNavigationFragmentResult(){
        return appLifecycleDelegate.environment().navigationFragmentResult();
    }

    public Scheduler getSchedulerProviderUI() {
        return AndroidSchedulers.mainThread();
    }

    public Scheduler getSchedulerProviderIO() {
        return Schedulers.io();
    }

    public void requestError(@NonNull SaizadBaseViewModel.ErrorData errorData) {
        final boolean handle = serverError(errorData.getThrowable(), errorData.getId());
        if (!handle) {
            showAlertDialogOk("Error", errorData.getThrowable().getMessage());
        }
    }

    public void requestApiError(@NonNull SaizadBaseViewModel.ApiErrorData apiErrorData) {
        final ErrorModel.Error error = apiErrorData.getApiErrorException().getError();
        final boolean handel = serverError(apiErrorData.getApiErrorException(), apiErrorData.getId());
        if (!handel) {
            showAlertDialogOk(error.error, error.message);
        }
    }

    public void requestLoading(@NonNull SaizadBaseViewModel.LoadingData loadingData) {
        loadingDialog.show(loadingData.isLoading());
    }

    public boolean serverError(@NonNull Throwable throwable, int requestId) {
        return false;
    }


    public LiveData<Integer> showAlertDialogOk(String title, String message, boolean cancelAble) {
        MutableLiveData<Integer> liveData = new MutableLiveData<>();
        new AlertDialog.Builder(appLifecycleDelegate.context())
                .setTitle(title)
                .setMessage(message)
                .setCancelable(cancelAble)
                .setPositiveButton(android.R.string.ok, (dialog, which) -> liveData.setValue(which))
                .show();
        return liveData;
    }

    public LiveData<Integer> showAlertDialogOk(String title, String message) {
        return showAlertDialogOk(title, message, false);
    }

    public LiveData<Integer> showAlertDialogYesNo(String title, String message, @DrawableRes int icon) {
        return showAlertDialogYesNo(title, message, icon, appLifecycleDelegate.context().getString(android.R.string.yes), appLifecycleDelegate.context().getString(android.R.string.no));
    }

    public LiveData<Integer> showAlertDialogYesNo(String title, String message, @DrawableRes int icon, String positiveName, String negativeName) {
        MutableLiveData<Integer> liveData = new MutableLiveData<>();
        new AlertDialog.Builder(appLifecycleDelegate.context())
                .setTitle(title)
                .setMessage(message)
                .setIcon(icon)
                .setPositiveButton(positiveName, (dialog, which) -> liveData.setValue(which))
                .setNegativeButton(negativeName, (dialog, which) -> liveData.setValue(which))
                .show();
        return liveData;
    }

    public void requestLocation(Action1<Location> locationAction) {
        loadingDialog.show(true);
        appLifecycleDelegate.appLocation().getLastLocation(location -> {
            loadingDialog.show(false);
            locationAction.call(location);
        }, throwable -> {
            loadingDialog.show(false);
            String title = "Error";
            String message = throwable.getMessage();
            if(throwable instanceof SecurityException){
                title = "Permission Not Granted";
                message = "Please provide location permission from the app settings";
            }else if(throwable instanceof SaizadLocation.GPSOffException){
                title = "GPS is OFF";
                message = throwable.getMessage();
            }
            showAlertDialogOk(title, message, true);
        });
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        log("onCreate");
        loadingDialog = new LoadingDialog(appLifecycleDelegate.context());
        viewModel = getFragmentViewModel(appLifecycleDelegate.getViewModelClassType());
//        compositeDisposable = new CompositeDisposable();
    }

    @Override
    @CallSuper
    public void onResume() {
        log("onResume");
    }

    @Override
    @CallSuper
    public void onStart() {
        log("onStart");
    }

    @CallSuper
    @Override
    public void onPause() {
        log("onPause");
    }

    @Override
    @CallSuper
    public void onStop() {
        log("onStop");
    }

    @Override
    @CallSuper
    public void onDestroy() {
        log("onDestroy");
    }

    @Override
    public SaizadBaseViewModel viewModel() {
        return viewModel;
    }

    @NotNull
    @Override
    public CompositeDisposable compositeDisposable() {
        return compositeDisposable;
    }


    public void openClosableFragment(@IdRes int fragment) {
        openClosableFragment(fragment, null);
    }

    public void openClosableFragment(@IdRes int fragment, @Nullable Bundle bundle) {
        openClosableFragment(fragment, bundle, new NavOptions.Builder()
                .setEnterAnim(R.anim.slide_up)
                .setPopExitAnim(R.anim.slide_down).build());
    }

    public void openClosableFragment(@IdRes int fragment, @Nullable Bundle bundle, @Nullable NavOptions navOptions) {
        navController().navigate(fragment, bundle, navOptions);
    }

    @NotNull
    @Override
    public NavController navController() {
        return appLifecycleDelegate.navController();
    }

}
