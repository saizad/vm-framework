package com.vm.frameworkexample.di.auth

import com.vm.framework.VmFrameworkEasyRetrofit
import com.vm.frameworkexample.MVVMExampleCurrentUser
import com.vm.frameworkexample.api.AuthApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import sa.zad.easypermission.PermissionManager

@Module
@InstallIn(SingletonComponent::class)
object AuthModule {

    @Provides
    fun providesAuthApi(retrofit: VmFrameworkEasyRetrofit): AuthApi {
        return retrofit.provideRetrofit().create(AuthApi::class.java)
    }

}