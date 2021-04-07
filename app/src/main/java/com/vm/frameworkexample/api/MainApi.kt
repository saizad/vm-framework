package com.vm.frameworkexample.api

import com.vm.framework.model.DataModel
import com.vm.framework.model.IntPageDataModel
import com.vm.frameworkexample.models.ReqResUser
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Query
import sa.zad.easyretrofit.observables.NeverErrorObservable

interface MainApi {

    @GET("api/users/")
    fun users(@Query("page") page: Int?): NeverErrorObservable<IntPageDataModel<ReqResUser>>

    @GET("api/users/")
    fun delayedResponse(@Query("delay") delaySecs: Int): NeverErrorObservable<DataModel<List<ReqResUser>>>

    @GET("/api/unknown/23")
    fun resourceNotFound(): NeverErrorObservable<Void>

    @DELETE("/api/users/2")
    fun noContentResponse(): NeverErrorObservable<Void>
}