package com.saizad.mvvm.components;

import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.CallSuper;
import androidx.annotation.DrawableRes;
import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;

import com.saizad.mvvm.ActivityResult;
import com.saizad.mvvm.delegation.fragment.FragmentAppLifecycleCallBack;
import com.saizad.mvvm.delegation.fragment.FragmentAppLifecycleDelegate;
import com.saizad.mvvm.delegation.fragment.FragmentAppLifecycleDelegateImp;
import com.saizad.mvvm.delegation.fragment.FragmentCB;

import org.jetbrains.annotations.NotNull;

import io.reactivex.Scheduler;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.subjects.BehaviorSubject;
import rx.functions.Action1;

public abstract class SaizadBaseDialogFragment<V extends SaizadBaseViewModel> extends DaggerDialogFragment implements FragmentAppLifecycleDelegate, FragmentCB<V>, FragmentAppLifecycleCallBack {

    protected FragmentAppLifecycleDelegate delegate;


    public SaizadBaseDialogFragment() {
        delegate = new FragmentAppLifecycleDelegateImp<>(this, this, getClass().getSimpleName());
    }

    @NonNull
    @Override
    public Context context() {
        return requireContext();
    }

    @NonNull
    @Override
    public ViewModelStoreOwner viewModelStoreOwner() {
        return this;
    }

    @NotNull
    public abstract Class<V> getViewModelClassType();

    @NotNull
    @Override
    public NavController navController() {
        return Navigation.findNavController(requireView());
    }

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

    public final <T> void finishWithResult(ActivityResult<T> activityResult) {
        delegate.finishWithResult(activityResult);
    }

    public void finish() {
        delegate.finish();
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
    public final void onViewCreated(@NotNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        delegate.onViewCreated(view, savedInstanceState);
    }

    @Override
    public final void onViewReady() { }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        delegate.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState, boolean recycled) {
    }

    @Override
    public void setHasOptionsMenu(boolean hasMenu) {
        super.setHasOptionsMenu(hasMenu);
    }

    @Override
    public boolean onBackPressed() {
        return false;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        delegate.onCreate(savedInstanceState);
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
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        delegate.onActivityCreated(savedInstanceState);
    }

    @CallSuper
    @Override
    public void onPause() {
        super.onPause();
        delegate.onPause();
    }

    @CallSuper
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        delegate.onAttach(context);
    }

    @Nullable
    @Override
    @CallSuper
    public View onCreateView(@NotNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return delegate.onCreateView(inflater, container, savedInstanceState);
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
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        delegate.onViewStateRestored(savedInstanceState);
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

    @Override
    @CallSuper
    public void onDestroyView() {
        super.onDestroyView();
        delegate.onDestroyView();
    }

    @Override
    @CallSuper
    public void onDetach() {
        super.onDetach();
        delegate.onDetach();
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

    public void openClosableFragment(@IdRes int fragment) {
        delegate.openClosableFragment(fragment);
    }

    public void openClosableFragment(@IdRes int fragment, @Nullable Bundle bundle) {
        delegate.openClosableFragment(fragment, bundle);
    }

    public void openClosableFragment(@IdRes int fragment, @Nullable Bundle bundle, @Nullable NavOptions navOptions) {
        delegate.openClosableFragment(fragment, bundle, navOptions);
    }

    public void requestLocation(Action1<Location> locationAction) {
        delegate.requestLocation(locationAction);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        environment().permissionManager().onRequestPermissionsResult(getActivity(),requestCode, permissions, grantResults);
    }

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
    public boolean persistView() {
        return true;
    }

    @Override
    public int menRes() {
        return 0;
    }
}
