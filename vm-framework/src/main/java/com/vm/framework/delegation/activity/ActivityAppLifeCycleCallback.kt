package com.vm.framework.delegation.activity

import com.vm.framework.components.VmFrameworkBaseViewModel
import com.vm.framework.delegation.BaseLifecycleCallBack

interface ActivityAppLifeCycleCallback : BaseLifecycleCallBack{
    fun requestLoading(loadingData: VmFrameworkBaseViewModel.LoadingData)
}