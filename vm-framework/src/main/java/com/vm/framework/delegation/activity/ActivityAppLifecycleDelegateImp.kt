package com.vm.framework.delegation.activity

import com.vm.framework.LoadingDialog
import com.vm.framework.components.VmFrameworkBaseViewModel
import com.vm.framework.delegation.BaseLifecycleDelegateImp

open class ActivityAppLifecycleDelegateImp<V : VmFrameworkBaseViewModel>(
    activityAppLifeCycleCallback: ActivityAppLifeCycleCallback,
    activityCB: ActivityCB<V>
) : BaseLifecycleDelegateImp<V, ActivityCB<V>>(activityAppLifeCycleCallback, activityCB),
    ActivityAppLifecycleDelegate {

    private val loadingDialog: LoadingDialog by lazy {
        LoadingDialog(appLifecycleDelegate.context())
    }

    override fun showApiRequestLoadingDialog(show: Boolean) {
        loadingDialog.show(show)
    }

    override fun onDestroy() {
        super.onDestroy()
        loadingDialog.dismiss()
    }
}