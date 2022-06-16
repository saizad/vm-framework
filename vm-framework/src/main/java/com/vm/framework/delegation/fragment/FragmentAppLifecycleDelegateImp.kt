package com.vm.framework.delegation.fragment

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.annotation.CallSuper
import com.vm.framework.ActivityResult
import com.vm.framework.components.VmFrameworkBaseViewModel
import com.vm.framework.delegation.BaseLifecycleDelegateImp
import com.vm.framework.utils.lifecycleScopeOnMain

open class FragmentAppLifecycleDelegateImp<V : VmFrameworkBaseViewModel>(
    private val fragmentAppLifecycleCallBack: FragmentAppLifecycleCallBack,
    fragmentAppLifecycleDelegate: FragmentCB<V>
) : BaseLifecycleDelegateImp<V, FragmentCB<V>>(
    fragmentAppLifecycleCallBack, fragmentAppLifecycleDelegate
), FragmentAppLifecycleDelegate {

    private var hasInitializedRootView = false
    private var rootView: View? = null

    private fun getPersistentView(
        layoutInflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
        layout: Int
    ): View {
        if (rootView == null || !appLifecycleDelegate.persistView()) {
            rootView = layoutInflater.inflate(layout, container, false)
        } else {
            val parent = rootView!!.parent
            if (parent != null) {
                (parent as ViewGroup).removeView(rootView)
            }
        }
        return rootView!!
    }

    override fun <T> finishWithResult(activityResult: ActivityResult<T>) {
        viewModel().navigationFragmentResult(activityResult)
        finish()
    }

    override fun finish() {
        navController().popBackStack()
    }

    @CallSuper
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        log("onActivityCreated")
    }

    @CallSuper
    override fun onAttach(context: Context) {
        log("onAttach")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fragmentAppLifecycleCallBack.setHasOptionsMenu(appLifecycleDelegate.menRes() != 0)
    }

    @CallSuper
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        log("onCreateView")
        return getPersistentView(
            inflater,
            container,
            savedInstanceState,
            appLifecycleDelegate.layoutRes()
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        log("onViewCreated")
        onViewReady()
        fragmentAppLifecycleCallBack.onViewCreated(view, savedInstanceState, hasInitializedRootView)
        if (!hasInitializedRootView) {
            hasInitializedRootView = appLifecycleDelegate.persistView()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        val menuRes = appLifecycleDelegate.menRes()
        if (menuRes != 0) {
            inflater.inflate(menuRes, menu)
        }
    }

    @CallSuper
    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        log("onViewStateRestored")
    }

    @CallSuper
    override fun onDestroyView() {
        log("onDestroyView")
        compositeDisposable().dispose()
    }

    @CallSuper
    override fun onDetach() {
        log("onDetach")
    }

}