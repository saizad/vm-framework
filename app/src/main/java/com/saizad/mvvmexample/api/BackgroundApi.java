package com.saizad.mvvmexample.api;

import com.saizad.mvvm.model.DataModel;
import com.saizad.mvvm.model.FCMTokenBody;
import com.saizad.mvvm.model.NameModel;
import retrofit2.http.Body;
import retrofit2.http.POST;
import sa.zad.easyretrofit.observables.NeverErrorObservable;

public interface BackgroundApi {

    @POST("fcm_register/")
    NeverErrorObservable<DataModel<NameModel>> fcmRegister(@Body FCMTokenBody fcmTokenBody);

}
