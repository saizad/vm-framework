package com.vm.frameworkexample.components.main.home

import com.vm.framework.enums.DataState
import com.vm.framework.model.DataModel
import com.vm.framework.utils.filterRequestCode
import com.vm.frameworkexample.ApiRequestCodes.DELAYED_RESPONSE
import com.vm.frameworkexample.ApiRequestCodes.DELETE_USER
import com.vm.frameworkexample.ApiRequestCodes.RANDOM_REQUEST
import com.vm.frameworkexample.ApiRequestCodes.RESOURCE_NOT_FOUND
import com.vm.frameworkexample.RequestCodes
import com.vm.frameworkexample.components.main.MainViewModel
import com.vm.frameworkexample.di.main.MainEnvironment
import com.vm.frameworkexample.models.ReqResUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
open class HomeViewModel @Inject constructor(
    environment: MainEnvironment
) : MainViewModel(environment) {

    val x: Flow<ReqResUser> = environment.activityResultFlow.filterRequestCode(RequestCodes.USER)

    open fun delayed(
        delay: Int,
        requestId: Int = DELAYED_RESPONSE
    ): Flow<DataState<DataModel<List<ReqResUser>>>> {
        return flowData(api.delayedResponse(delay), requestId)
    }

    open fun resourceNotFound(requestId: Int = RESOURCE_NOT_FOUND): Flow<DataState<Void>> {
        return flowData(api.resourceNotFound(), requestId)
    }

    open fun noContentResponse(requestId: Int = DELETE_USER): Flow<DataState<Void>> {
        return flowData(api.noContentResponse(), requestId)
    }

}