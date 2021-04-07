package com.vm.frameworkexample.components.main.users.userpage

import androidx.lifecycle.MutableLiveData
import com.vm.frameworkexample.components.main.MainPageViewModel
import com.vm.frameworkexample.di.main.MainEnvironment
import com.vm.frameworkexample.models.ReqResUser
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UserPageViewModel @Inject constructor(
    mainEnvironment: MainEnvironment
) : MainPageViewModel(mainEnvironment){

    private lateinit var user: ReqResUser

    val liveData = MutableLiveData<ReqResUser>()

    fun setUser(resUser: ReqResUser){
        user = resUser
        liveData.postValue(user)
    }
}