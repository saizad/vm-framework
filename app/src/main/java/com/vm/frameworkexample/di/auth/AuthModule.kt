package com.vm.frameworkexample.di.auth

import com.vm.framework.*
import com.vm.frameworkexample.MVVMExampleCurrentUser
import com.vm.frameworkexample.api.AuthApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.reactivex.subjects.BehaviorSubject
import sa.zad.easypermission.PermissionManager
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
object AuthModule {

    @Provides
    fun providesAuthEnvironment(
        authApi: AuthApi,
        fcmToken: FCMToken,
        currentUser: MVVMExampleCurrentUser,
        navigationFragmentResult: BehaviorSubject<ActivityResult<*>>,
        @Named("notification") notifyOnceBehaviorSubject: BehaviorSubject<NotifyOnce<*>>,
        permissionManager: PermissionManager
    ): AuthEnvironment {
        return AuthEnvironment(
            authApi,
            fcmToken,
            navigationFragmentResult,
            currentUser,
            notifyOnceBehaviorSubject,
            permissionManager
        )
    }


    @Provides
    fun providesAuthApi(retrofit: VmFrameworkEasyRetrofit): AuthApi {
        return retrofit.provideRetrofit().create(AuthApi::class.java)
    }
}