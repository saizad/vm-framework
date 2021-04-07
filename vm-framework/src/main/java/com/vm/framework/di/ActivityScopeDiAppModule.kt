package com.vm.framework.di

import com.vm.framework.components.VmFrameworkBaseViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped
import io.reactivex.subjects.PublishSubject
import javax.inject.Named

@Module
@InstallIn(ActivityRetainedComponent::class)
object ActivityScopeDiAppModule {

    @ActivityRetainedScoped
    @Provides
    @Named("loadingState")
    fun loadingState(): PublishSubject<VmFrameworkBaseViewModel.LoadingData> {
        return PublishSubject.create()
    }

}