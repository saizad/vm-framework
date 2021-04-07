package com.vm.framework.pager

interface PageListener<F : BasePagerAdapterContract> {
    fun onPageReady(page: F)
    fun onPageLoaded(page: F, position: Int)
}