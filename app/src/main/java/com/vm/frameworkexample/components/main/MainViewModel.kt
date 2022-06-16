package com.vm.frameworkexample.components.main

import com.vm.frameworkexample.MVVMExampleCurrentUser
import com.vm.frameworkexample.RequestCodes
import com.vm.frameworkexample.api.MainApi
import com.vm.frameworkexample.components.VmFrameworkExampleViewModel
import com.vm.frameworkexample.di.main.MainEnvironment
import com.vm.frameworkexample.models.ReqResUser

abstract class MainViewModel constructor(
    environment: MainEnvironment,
) : VmFrameworkExampleViewModel(environment){

    val api: MainApi = environment.api
    var currentUserType: MVVMExampleCurrentUser = environment.currentUser as MVVMExampleCurrentUser

}