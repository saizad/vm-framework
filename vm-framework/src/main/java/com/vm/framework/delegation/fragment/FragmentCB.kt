package com.vm.framework.delegation.fragment

import androidx.annotation.LayoutRes
import com.vm.framework.components.VmFrameworkBaseViewModel
import com.vm.framework.delegation.BaseCB

interface FragmentCB<V : VmFrameworkBaseViewModel> : BaseCB<V> {
    @LayoutRes
    fun layoutRes(): Int
    fun persistView(): Boolean
}