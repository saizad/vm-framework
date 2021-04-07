package com.vm.framework.pager

import io.reactivex.Observable

interface BasePagerAdapterContract {
    fun pageLoaded(): Observable<Boolean>
}