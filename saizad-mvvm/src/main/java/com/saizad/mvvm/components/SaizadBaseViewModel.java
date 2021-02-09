package com.saizad.mvvm.components;

import android.os.Handler;
import android.os.Looper;

import androidx.annotation.CallSuper;
import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.common.util.ArrayUtils;
import com.sa.easyandroidform.ObjectUtils;
import com.saizad.mvvm.ActivityResult;
import com.saizad.mvvm.BaseNotificationModel;
import com.saizad.mvvm.Environment;
import com.saizad.mvvm.NotifyOnce;
import com.saizad.mvvm.model.DataModel;
import com.saizad.mvvm.model.ErrorModel;
import com.saizad.mvvm.model.IntPageDataModel;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.subjects.BehaviorSubject;
import retrofit2.Response;
import rx.functions.Action1;
import sa.zad.easyretrofit.observables.NeverErrorObservable;
import sa.zad.pagedrecyclerlist.ConstraintLayoutList;

public abstract class SaizadBaseViewModel extends ViewModel {

    private final BehaviorSubject<ActivityResult<?>> activityResult;
    protected CompositeDisposable disposable = new CompositeDisposable();
    private MutableLiveData<LoadingData> loadingLiveData = new MutableLiveData<>();
    private MutableLiveData<ErrorData> errorLiveData = new MutableLiveData<>();
    private MutableLiveData<ApiErrorData> apiErrorLiveData = new MutableLiveData<>();
    public MutableLiveData<Object> currentUser = new MutableLiveData<>();
    protected BehaviorSubject<NotifyOnce<?>> notification;
    public final Environment environment;

    public SaizadBaseViewModel(Environment environment) {
        this.environment = environment;
        this.activityResult = environment.navigationFragmentResult();
        this.notification = environment.getNotification();
    }

    public final <T extends BaseNotificationModel> LiveData<T> notificationListener(String[] notificationType) {
        MutableLiveData<T> notificationModelMutableLiveData = new MutableLiveData<>();
        disposable.add(notification
                .filter(notifyOnce -> ArrayUtils.contains(notificationType, notifyOnce.getType()))
                .filter(notifyOnce -> !notifyOnce.isRead())
                .observeOn(AndroidSchedulers.mainThread())
                .map(notifyOnce -> (T) notifyOnce.getNotificationModel())
                .subscribe(notificationModelMutableLiveData::setValue,
                        throwable -> {
                        }));

        return notificationModelMutableLiveData;
    }

    public final <T extends BaseNotificationModel> LiveData<T> notificationListener(String notificationType) {
        String[] types = {notificationType};
        return notificationListener(types);
    }

    protected <M> LiveData<IntPageDataModel<M>> pagedLiveData(NeverErrorObservable<IntPageDataModel<M>> observable, @NonNull ConstraintLayoutList.CallBack<IntPageDataModel<M>> callback, Action1<Throwable> errorCallback, int requestId) {
        MutableLiveData<IntPageDataModel<M>> mutableLiveData = new MutableLiveData<>();
        request(observable, requestId, dataModelResponse -> {
            final IntPageDataModel<M> data = dataModelResponse.body();
            callback.call(data);
            mutableLiveData.setValue(data);
        }).subscribe();
        return mutableLiveData;
    }

    public @NonNull
    LiveData<Void> liveDataNoResponse(NeverErrorObservable<Void> observable, int requestId) {
        MutableLiveData<Void> mutableLiveData = new MutableLiveData<>();
        request(observable, requestId, voidResponse -> mutableLiveData.setValue(null)).subscribe();
        return mutableLiveData;
    }

    public @NonNull
    <M> LiveData<M> liveData(NeverErrorObservable<DataModel<M>> observable, int requestId) {
        MutableLiveData<M> mutableLiveData = new MutableLiveData<>();
        apiRequest(observable, requestId)
                .subscribe(mDataModel -> mutableLiveData.setValue(mDataModel.data));
        return mutableLiveData;
    }

    public @NonNull
    <M> Observable<DataModel<M>> apiRequest(NeverErrorObservable<DataModel<M>> observable, int requestId) {
        return request(observable, requestId, dataModelResponse -> {});
    }

    public @NonNull
    <M> Observable<M> request(NeverErrorObservable<M> observable, int requestId, Action1<Response<M>> responseAction) {
        shootLoading(true, requestId);
        return observable
                .successResponse(responseAction)
                .timeoutException(e -> shootError(e, requestId))
                .connectionException(e -> shootError(e, requestId))
                .failedResponse(dataModelResponse -> {
                })
                .apiException(errorModel -> shootError(errorModel, requestId), ErrorModel.class)
                .exception(throwable -> shootError(throwable, requestId))
                .doFinally(() -> shootLoading(false, requestId));
    }

    public final Observable<ActivityResult<?>> activityResult(int requestCode) {
        return activityResult
                .filter(ActivityResult::isOk)
                .filter(result -> result.isRequestCode(requestCode))
                .doOnNext(result -> activityResult.onNext(new ActivityResult<>(3321235, 1, null)));
    }

    public final <V> Observable<V> activityResult(int requestCode, Class<V> cast) {
        return activityResult(requestCode)
                .map(ActivityResult::getValue)
                .cast(cast);
    }

    public final MutableLiveData<ActivityResult<?>> onNavigationResult(int requestCode) {
        MutableLiveData<ActivityResult<?>> activityResultLiveData = new MutableLiveData<>();
        disposable.add(activityResult(requestCode)
                .subscribe(activityResultLiveData::setValue, throwable -> {
                }));
        return activityResultLiveData;
    }

    public final <V> MutableLiveData<V> onNavigationResult(int requestCode, Class<V> cast) {
        MutableLiveData<V> activityResultLiveData = new MutableLiveData<>();
        disposable.add(activityResult(requestCode, cast)
                .subscribe(activityResultLiveData::setValue, throwable -> {
                }));
        return activityResultLiveData;
    }

    @CallSuper
    @Override
    protected void onCleared() {
        //Todo temp fix can't locate bug
        if (ObjectUtils.isNotNull(disposable)) {
            disposable.dispose();
        }
        super.onCleared();
    }

    @CallSuper
    public void onDestroyView() {
        disposable.dispose();
    }

    public final LiveData<ErrorData> errorLiveData() {
        return errorLiveData;
    }

    public MutableLiveData<LoadingData> loadingLiveData() {
        return loadingLiveData;
    }

    public MutableLiveData<ApiErrorData> apiErrorLiveData() {
        return apiErrorLiveData;
    }

    @CallSuper
    public void onViewCreated() {
        disposable = new CompositeDisposable();
        disposable.add(environment.currentUser().observable()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(o -> {
                    currentUser.setValue(o);
                }));
    }

    protected void shootError(ErrorModel errorModel, int id) {
        final ApiErrorData value = new ApiErrorData(new ApiErrorException(errorModel), id);
        new Handler(Looper.getMainLooper()).post(() -> {
            apiErrorLiveData.setValue(value);
        });
    }

    protected void shootError(Throwable throwable, int id) {
        final ErrorData value = new ErrorData(throwable, id);
        new Handler(Looper.getMainLooper()).post(() -> {
            errorLiveData.setValue(value);
        });
    }

    protected void shootLoading(boolean loading, int id) {
        final LoadingData value = new LoadingData(loading, id);
        new Handler(Looper.getMainLooper()).post(() -> {
            loadingLiveData.setValue(value);
        });
    }

    public abstract static class LiveDataStatus {
        private int id;
        private static final int DISCARDED_ID = 3412341;

        public LiveDataStatus(int id) {
            this.id = id;
        }

        public boolean isThisRequest(int id) {
            return this.id == id;
        }

        public int getId() {
            return id;
        }

        public boolean isDiscarded() {
            return DISCARDED_ID == this.id;
        }

        public void discard() {
            id = DISCARDED_ID;
        }

    }

    public static class ApiErrorData extends LiveDataStatus {
        private final ApiErrorException apiErrorException;

        public ApiErrorData(ApiErrorException apiErrorException, int id) {
            super(id);
            this.apiErrorException = apiErrorException;
        }

        public ApiErrorException getApiErrorException() {
            return apiErrorException;
        }
    }

    public static class ErrorData extends LiveDataStatus {
        private final Throwable throwable;

        public ErrorData(Throwable throwable, int id) {
            super(id);
            this.throwable = throwable;
        }

        public Throwable getThrowable() {
            return throwable;
        }
    }

    public static class LoadingData extends LiveDataStatus {
        private final boolean isLoading;

        public LoadingData(boolean isLoading, int id) {
            super(id);
            this.isLoading = isLoading;
        }

        public boolean isLoading() {
            return isLoading;
        }
    }

    public static class ApiErrorException extends Exception {

        private final ErrorModel errorModel;

        public ApiErrorException(ErrorModel errorModel) {
            super(new Throwable(errorModel.error.message));
            this.errorModel = errorModel;
        }

        public ErrorModel getErrorModel() {
            return errorModel;
        }

        public ErrorModel.Error getError() {
            return getErrorModel().error;
        }
    }
}
