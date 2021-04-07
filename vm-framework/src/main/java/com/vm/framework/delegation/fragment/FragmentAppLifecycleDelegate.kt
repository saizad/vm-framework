package com.vm.framework.delegation.fragment

import android.content.Context
import android.os.Bundle
import android.view.*
import com.vm.framework.ActivityResult
import com.vm.framework.delegation.BaseLifecycleDelegate

interface FragmentAppLifecycleDelegate : BaseLifecycleDelegate {
    fun onViewStateRestored(savedInstanceState: Bundle?)
    fun onAttach(context: Context)
    fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View

    fun onActivityCreated(savedInstanceState: Bundle?)
    fun onViewCreated(view: View, savedInstanceState: Bundle?)
    fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater)
    fun onDestroyView()
    fun onDetach()
    fun <T> finishWithResult(activityResult: ActivityResult<T>)
    fun finish()
}