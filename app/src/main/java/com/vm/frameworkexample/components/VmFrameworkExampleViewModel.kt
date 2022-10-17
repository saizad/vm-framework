package com.vm.frameworkexample.components

import com.vm.framework.Environment
import com.vm.framework.components.VmFrameworkBaseViewModel
import com.vm.framework.enums.DataState
import com.vm.frameworkexample.MVVMExampleCurrentUser
import com.vm.frameworkexample.models.ApiError
import kotlinx.coroutines.flow.Flow
import sa.zad.easyretrofit.observables.NeverErrorObservable

abstract class VmFrameworkExampleViewModel(
    environment: Environment
) : VmFrameworkBaseViewModel(environment){

//    @Inject
//    lateinit var gson: Gson

    fun <M> flowData(
        observable: NeverErrorObservable<M>,
        requestId: Int
    ): Flow<DataState<M>> {
        return networkRequest.flowData(observable, requestId, ApiError::class.java)
    }

    fun currentUser(): MVVMExampleCurrentUser {
        return environment.currentUser as MVVMExampleCurrentUser
    }
}