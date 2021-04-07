package com.vm.frameworkexample.components.main.profile.updateuser

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.vm.frameworkexample.components.main.MainViewModel
import com.vm.frameworkexample.di.main.MainEnvironment
import com.vm.frameworkexample.models.ReqResUser
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UpdateUserViewModel @Inject constructor(
    mainEnvironment: MainEnvironment,
    savedStateHandle: SavedStateHandle
) : MainViewModel(mainEnvironment){

    val form by lazy {
        ReqResUser.Form(savedStateHandle.get<ReqResUser>("user")!!)
    }

    fun save(): LiveData<ReqResUser>{
        val mutableLiveData = MutableLiveData<ReqResUser>()
        val freshUser = form.requiredBuild()
        mutableLiveData.value = freshUser
        return mutableLiveData
    }
}