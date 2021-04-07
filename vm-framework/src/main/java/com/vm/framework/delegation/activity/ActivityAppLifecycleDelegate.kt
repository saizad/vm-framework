package com.vm.framework.delegation.activity

import com.vm.framework.components.VmFrameworkBaseViewModel
import com.vm.framework.delegation.BaseLifecycleDelegate

interface ActivityAppLifecycleDelegate : BaseLifecycleDelegate{
    fun requestLoading(loadingData: VmFrameworkBaseViewModel.LoadingData)
}