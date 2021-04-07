package com.vm.frameworkexample.components.main.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.vm.framework.enums.DataState
import com.vm.framework.model.DataModel
import com.vm.frameworkexample.ApiRequestCodes.DELAYED_RESPONSE
import com.vm.frameworkexample.ApiRequestCodes.DELETE_USER
import com.vm.frameworkexample.ApiRequestCodes.RANDOM_REQUEST
import com.vm.frameworkexample.ApiRequestCodes.RESOURCE_NOT_FOUND
import com.vm.frameworkexample.components.main.MainViewModel
import com.vm.frameworkexample.di.main.MainEnvironment
import com.vm.frameworkexample.models.ReqResUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    environment: MainEnvironment
) : MainViewModel(environment) {

    fun delayed(
        delay: Int,
        requestId: Int = DELAYED_RESPONSE
    ): Flow<DataState<DataModel<List<ReqResUser>>>> {
        return flowData(api.delayedResponse(delay), requestId)
    }

    fun logout(): LiveData<Void?> {
        val mutableLiveData = MutableLiveData<Void?>()
        viewModelScope.launch {
            currentUserType.logout {
                mutableLiveData.postValue(null)
            }
        }
        return mutableLiveData
    }

    fun resourceNotFound(requestId: Int = RESOURCE_NOT_FOUND): Flow<DataState<Void>> {
        return flowData(api.resourceNotFound(), requestId)
    }

    fun noContentResponse(requestId: Int = DELETE_USER): Flow<DataState<Void>> {
        return flowData(api.noContentResponse(), requestId)
    }

    override fun showError(apiErrorData: ApiErrorData) {
        if(!apiErrorData.isThisRequest(RESOURCE_NOT_FOUND)) {
            super.showError(apiErrorData)
        }
    }

    override fun showLoading(loadingData: LoadingData) {
        if(!loadingData.isThisRequest(RANDOM_REQUEST)) {
            super.showLoading(loadingData)
        }
    }
}