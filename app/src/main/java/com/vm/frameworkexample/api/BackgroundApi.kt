package com.vm.frameworkexample.api

import com.vm.framework.model.DataModel
import com.vm.framework.model.FCMTokenBody
import com.vm.framework.model.NameModel
import retrofit2.http.Body
import retrofit2.http.POST
import sa.zad.easyretrofit.observables.NeverErrorObservable

interface BackgroundApi {
    @POST("fcm_register/")
    fun fcmRegister(@Body fcmTokenBody: FCMTokenBody): NeverErrorObservable<DataModel<NameModel>>
}