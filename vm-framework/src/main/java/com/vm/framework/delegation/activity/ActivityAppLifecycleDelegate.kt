package com.vm.framework.delegation.activity

import com.vm.framework.delegation.BaseLifecycleDelegate

interface ActivityAppLifecycleDelegate : BaseLifecycleDelegate {
    fun showApiRequestLoadingDialog(show: Boolean)
}