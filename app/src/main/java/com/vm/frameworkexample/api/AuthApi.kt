package com.vm.frameworkexample.api

import com.vm.framework.model.DataModel
import com.vm.framework.model.LoginBody
import com.vm.frameworkexample.models.LoginResponse
import com.vm.frameworkexample.models.ReqResUser
import retrofit2.http.*
import sa.zad.easyretrofit.observables.NeverErrorObservable

interface AuthApi {

    @POST("api/login/")
    fun login(@Body loginBody: LoginBody): NeverErrorObservable<LoginResponse>

    @GET("api/users/{user}")
    fun user(@Path("user") user: Int): NeverErrorObservable<DataModel<ReqResUser>>

}