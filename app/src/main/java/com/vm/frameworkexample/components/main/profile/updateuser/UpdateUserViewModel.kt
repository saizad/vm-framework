package com.vm.frameworkexample.components.main.profile.updateuser

import androidx.lifecycle.SavedStateHandle
import com.vm.frameworkexample.components.main.MainViewModel
import com.vm.frameworkexample.di.main.MainEnvironment
import com.vm.frameworkexample.models.ReqResUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
open class UpdateUserViewModel @Inject constructor(
    mainEnvironment: MainEnvironment,
    savedStateHandle: SavedStateHandle
) : MainViewModel(mainEnvironment){

    open val form by lazy {
        ReqResUser.Form(savedStateHandle.get<ReqResUser>("user")!!)
    }

    open fun save(): Flow<ReqResUser>{
        return MutableStateFlow(form.requiredBuild())
    }
}