package com.saizad.mvvm.delegation;

import androidx.annotation.NonNull;

import com.saizad.mvvm.components.SaizadBaseViewModel;

public interface BaseLifecycleCallBack {

    void requestError(@NonNull SaizadBaseViewModel.ErrorData errorData);

    void requestApiError(@NonNull SaizadBaseViewModel.ApiErrorData apiErrorData);

    void requestLoading(@NonNull SaizadBaseViewModel.LoadingData loadingData);

    boolean serverError(@NonNull Throwable throwable, int requestId);

}
