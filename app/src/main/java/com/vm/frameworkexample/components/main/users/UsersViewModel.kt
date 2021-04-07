package com.vm.frameworkexample.components.main.users

import com.vm.framework.enums.DataState
import com.vm.framework.model.IntPageDataModel
import com.vm.frameworkexample.ApiRequestCodes
import com.vm.frameworkexample.components.main.MainViewModel
import com.vm.frameworkexample.di.main.MainEnvironment
import com.vm.frameworkexample.models.ReqResUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class UsersViewModel @Inject constructor(
    mainEnvironment: MainEnvironment
) : MainViewModel(mainEnvironment){

    fun users(
        page: Int?,
        requestId: Int = ApiRequestCodes.LIST_OF_USERS
    ): Flow<DataState<IntPageDataModel<ReqResUser>>> {
        return flowData(api.users(page), requestId)
    }}