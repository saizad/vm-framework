package com.saizad.mvvm.pager

import android.os.Bundle
import android.view.View
import com.saizad.mvvm.components.SaizadBaseFragment
import com.saizad.mvvm.components.SaizadBaseViewModel
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject


abstract class BasePage<VM : SaizadBaseViewModel> : SaizadBaseFragment<VM>(),
    PagerAdapterListener, BasePagerAdapterContract {

    private val pageLoaded = BehaviorSubject.create<Boolean>()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?, recycled: Boolean) {
        super.onViewCreated(view, savedInstanceState, recycled)
        pageLoaded.onNext(true)
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

    override fun onPageShowing(visiblePercent: Int) {
        log("xxxOnPageShowing $visiblePercent")
    }

    override fun onPageHiding(visiblePercent: Int) {
        log("xxxOnPageHiding $visiblePercent")
    }

    override fun onPageResetting(visiblePercent: Int) {
        log("xxxOnPageResetting $visiblePercent")
    }
}
