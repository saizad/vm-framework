package com.vm.frameworkexample.components

import com.google.gson.Gson
import com.vm.framework.Environment
import com.vm.framework.components.VmFrameworkBaseViewModel
import com.vm.framework.enums.DataState
import com.vm.frameworkexample.models.ApiError
import kotlinx.coroutines.flow.Flow
import sa.zad.easyretrofit.observables.NeverErrorObservable
import javax.inject.Inject

abstract class VmFrameworkExampleViewModel(
    environment: Environment
) : VmFrameworkBaseViewModel(environment){

    @Inject
    lateinit var gson: Gson


    fun <M> flowData(
        observable: NeverErrorObservable<M>,
        requestId: Int
    ): Flow<DataState<M>> {
        return flowData(observable, requestId, eClass =  ApiError::class.java)
    }
}