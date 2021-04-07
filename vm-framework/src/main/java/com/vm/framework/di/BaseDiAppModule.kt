package com.vm.framework.di

import android.app.Application
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.vm.framework.ActivityResult
import com.vm.framework.NotifyOnce
import com.vm.framework.VmFrameworkLocation
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.reactivex.subjects.BehaviorSubject
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object BaseDiAppModule {

    @Singleton
    @Provides
    fun providesAppLocation(application: Application): VmFrameworkLocation {
        return VmFrameworkLocation(application)
    }

    @Singleton
    @Provides
    fun providesSharedPreferences(application: Application): SharedPreferences {
        return PreferenceManager.getDefaultSharedPreferences(application)
    }

    @Singleton
    @Provides
    fun provideGson(): Gson {
        val gsonBuilder = GsonBuilder().serializeNulls()
        gsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
        return gsonBuilder.create()
    }

    @Singleton
    @Provides
    fun provideFragmentNavigationResult(): BehaviorSubject<ActivityResult<*>> {
        return BehaviorSubject.create()
    }

    @Singleton
    @Provides
    @Named("notification")
    fun notificationResultBehaviorSubject(): BehaviorSubject<NotifyOnce<*>> {
        return BehaviorSubject.create()
    }
}