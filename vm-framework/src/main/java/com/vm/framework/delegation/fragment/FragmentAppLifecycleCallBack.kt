package com.vm.framework.delegation.fragment

import android.os.Bundle
import android.view.View
import com.vm.framework.delegation.BaseLifecycleCallBack

interface FragmentAppLifecycleCallBack : BaseLifecycleCallBack {
    fun onViewCreated(view: View, savedInstanceState: Bundle?, recycled: Boolean)
    fun setHasOptionsMenu(hasOptionsMenu: Boolean)
    fun onBackPressed(): Boolean
}