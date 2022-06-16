package com.vm.framework.delegation

import android.os.Bundle
import androidx.annotation.DrawableRes
import androidx.annotation.IdRes
import androidx.lifecycle.LiveData
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import com.vm.framework.components.VmFrameworkBaseViewModel
import com.vm.framework.error.ApiErrorData
import com.vm.framework.error.ConnectionErrorData
import com.vm.framework.error.ErrorData
import com.vm.framework.error.TimeoutErrorData
import io.reactivex.disposables.CompositeDisposable

interface BaseLifecycleDelegate {
    fun onCreate(savedInstanceState: Bundle?)
    fun onStart()
    fun onResume()
    fun onPause()
    fun onStop()
    fun onDestroy()
    fun showLongToast(text: CharSequence)
    fun showShortToast(value: Int)
    fun showShortToast(text: CharSequence)
    fun showToast(text: CharSequence, toastLength: Int)
    fun log(integer: Int)
    fun log(string: String)
    fun showConnectionErrorDialog(errorData: ConnectionErrorData)
    fun showTimeoutErrorDialog(errorData: TimeoutErrorData)
    fun showRequestErrorDialog(errorData: ErrorData)
    fun showApiErrorDialog(apiErrorData: ApiErrorData)
    fun showAlertDialogOk(
        title: String,
        message: String,
        cancelAble: Boolean = false
    ): LiveData<Int>

    fun showAlertDialogYesNo(
        title: String,
        message: String,
        @DrawableRes icon: Int,
        positiveName: String = "Yes",
        negativeName: String = "No"
    ): LiveData<Int>

    fun viewModel(): VmFrameworkBaseViewModel
    fun compositeDisposable(): CompositeDisposable
    fun navController(): NavController
    fun openFragment(
        @IdRes fragment: Int,
        bundle: Bundle.() -> Unit = {},
        navOptions: NavOptions? = null
    )

    fun onViewReady()
}