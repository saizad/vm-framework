package com.vm.framework.utils

import androidx.navigation.NavOptions
import com.vm.framework.VmFrameworkNetworkRequest
import com.vm.framework.components.VmFrameworkBaseViewModel
import com.vm.framework.delegation.BaseLifecycleCallBack
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach


val withSlideHorizontalBuilder: NavOptions.Builder
    get() = NavOptions.Builder()
        .setEnterAnim(com.vm.framework.R.anim.slide_in_right)
        .setPopEnterAnim(com.vm.framework.R.anim.slide_in_left)
        .setExitAnim(com.vm.framework.R.anim.slide_out_left)
        .setPopExitAnim(com.vm.framework.R.anim.slide_out_right)

val withSlideHorizontal: NavOptions
    get() = withSlideHorizontalBuilder.build()