package com.vm.frameworkexample

import com.vm.framework.CurrentUserType
import com.vm.framework.DataStoreWrapper
import com.vm.frameworkexample.models.ReqResUser
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
open class MVVMExampleCurrentUser @Inject constructor(dataStoreWrapper: DataStoreWrapper) :
    CurrentUserType<ReqResUser>(dataStoreWrapper) {

    override val classType: Class<ReqResUser>
        get() = ReqResUser::class.java

    override val token: String?
        get() = null
}