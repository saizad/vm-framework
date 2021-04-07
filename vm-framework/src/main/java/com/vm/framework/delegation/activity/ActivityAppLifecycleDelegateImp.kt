package com.vm.framework.delegation.activity

import com.vm.framework.LoadingDialog
import com.vm.framework.components.VmFrameworkBaseViewModel
import com.vm.framework.delegation.BaseLifecycleDelegateImp
import com.vm.framework.utils.addToDisposable
import io.reactivex.android.schedulers.AndroidSchedulers

class ActivityAppLifecycleDelegateImp<V : VmFrameworkBaseViewModel>(
    val activityAppLifeCycleCallback: ActivityAppLifeCycleCallback,
    activityCB: ActivityCB<V>,
    tag: String
) : BaseLifecycleDelegateImp<V, ActivityCB<V>>(activityAppLifeCycleCallback, activityCB, tag),
    ActivityAppLifecycleDelegate{

    val loadingDialog: LoadingDialog by lazy {
        LoadingDialog(appLifecycleDelegate.context())
    }

    override fun requestLoading(loadingData: VmFrameworkBaseViewModel.LoadingData) {
        loadingDialog.show(loadingData.isLoading)
    }

    override fun showLoading(show: Boolean) {
        super.showLoading(show)
        loadingDialog.show(show)
    }

    override fun onDestroy() {
        super.onDestroy()
        loadingDialog.dismiss()
    }

    override fun onViewReady() {
        super.onViewReady()
        viewModel.loadingSubject
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                activityAppLifeCycleCallback.requestLoading(it)
            }.addToDisposable(compositeDisposable)
    }
}