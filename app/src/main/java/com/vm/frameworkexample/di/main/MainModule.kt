package com.vm.frameworkexample.di.main

import com.vm.framework.*
import com.vm.frameworkexample.MVVMExampleCurrentUser
import com.vm.frameworkexample.api.MainApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.reactivex.subjects.BehaviorSubject
import sa.zad.easypermission.PermissionManager
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
object MainModule {

    @Provides
    fun providesMainApi(retrofit: VmFrameworkEasyRetrofit): MainApi {
        return retrofit.provideRetrofit().create(MainApi::class.java)
    }
}