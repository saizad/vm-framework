package com.saizad.mvvm.delegation;

import android.location.Location;
import android.os.Bundle;

import androidx.annotation.DrawableRes;
import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;

import com.saizad.mvvm.ActivityResult;
import com.saizad.mvvm.components.SaizadBaseViewModel;

import io.reactivex.Scheduler;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.subjects.BehaviorSubject;
import rx.functions.Action1;

public interface BaseLifecycleDelegate {
    void onCreate(@Nullable Bundle savedInstanceState);

    void onStart();

    void onResume();

    void onPause();

    void onStop();

    void onDestroy();

    void showLongToast(CharSequence text);

    void showShortToast(Integer value);

    void showShortToast(CharSequence text);

    void showToast(CharSequence text, int toastLength);

    void log(int integer);

    void log(String string);

    BehaviorSubject<ActivityResult<?>> getNavigationFragmentResult();

    Scheduler getSchedulerProviderUI();

    Scheduler getSchedulerProviderIO();

    void requestError(@NonNull SaizadBaseViewModel.ErrorData errorData);

    void requestApiError(@NonNull SaizadBaseViewModel.ApiErrorData apiErrorData);

    void requestLoading(@NonNull SaizadBaseViewModel.LoadingData loadingData);

    boolean serverError(@NonNull Throwable throwable, int requestId);

    LiveData<Integer> showAlertDialogOk(String title, String message, boolean cancelAble);

    LiveData<Integer> showAlertDialogOk(String title, String message);

    LiveData<Integer> showAlertDialogYesNo(String title, String message, @DrawableRes int icon);

    LiveData<Integer> showAlertDialogYesNo(String title, String message, @DrawableRes int icon, String positiveName, String negativeName);

    void requestLocation(Action1<Location> locationAction);

    SaizadBaseViewModel viewModel();

    @NonNull CompositeDisposable compositeDisposable();

    @NonNull NavController navController();

    void openClosableFragment(@IdRes int fragment);

    void openClosableFragment(@IdRes int fragment, @Nullable Bundle bundle);

    void openClosableFragment(@IdRes int fragment, @Nullable Bundle bundle, @Nullable NavOptions navOptions);

    void onViewReady();

}
