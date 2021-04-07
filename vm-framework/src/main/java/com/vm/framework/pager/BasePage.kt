package com.vm.framework.pager

import android.os.Bundle
import android.view.View
import androidx.annotation.CallSuper
import com.vm.framework.components.VmFrameworkBaseFragment
import com.vm.framework.components.VmFrameworkBaseViewModel
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject


abstract class BasePage<VM : VmFrameworkBaseViewModel> : VmFrameworkBaseFragment<VM>(),
    PagerAdapterListener, BasePagerAdapterContract {

    private var pageLoaded = BehaviorSubject.create<Boolean>()
    var pageIndex : Int = -1

    @CallSuper
    override fun onViewCreated(view: View, savedInstanceState: Bundle?, recycled: Boolean) {
        super.onViewCreated(view, savedInstanceState, recycled)
        pageLoaded.onNext(true)
    }

    @CallSuper
    override fun onDestroyView() {
        super.onDestroyView()
        pageLoaded.onComplete()
        pageLoaded = BehaviorSubject.create()
    }

    override fun onPageSelected() {
        log("onPageSelected $this")
    }

    override fun onPageUnSelected() {
        log("onPageUnSelected $this")
    }

    override fun pageLoaded(): Observable<Boolean> {
        return pageLoaded
    }
}
