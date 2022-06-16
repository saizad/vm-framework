package com.vm.framework.delegation

import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.annotation.CallSuper
import androidx.annotation.DrawableRes
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import com.vm.framework.components.VmFrameworkBaseViewModel
import com.vm.framework.error.ApiErrorData
import com.vm.framework.error.ConnectionErrorData
import com.vm.framework.error.ErrorData
import com.vm.framework.error.TimeoutErrorData
import io.reactivex.disposables.CompositeDisposable

abstract class BaseLifecycleDelegateImp<V : VmFrameworkBaseViewModel, CB : BaseCB<V>>(
    private val baseLifecycleCallBack: BaseLifecycleCallBack,
    protected val appLifecycleDelegate: CB
) : BaseLifecycleDelegate {

    lateinit var compositeDisposable: CompositeDisposable
    lateinit var viewModel: V

    private fun getFragmentViewModel(viewModel: Class<V>): V {
        return ViewModelProvider(appLifecycleDelegate.viewModelStoreOwner()).get(viewModel)
    }

    override fun showLongToast(text: CharSequence) {
        showToast(text, Toast.LENGTH_LONG)
    }

    override fun showShortToast(value: Int) {
        showToast(value.toString(), Toast.LENGTH_SHORT)
    }

    override fun showShortToast(text: CharSequence) {
        showToast(text, Toast.LENGTH_SHORT)
    }

    override fun showToast(text: CharSequence, toastLength: Int) {
        Toast.makeText(appLifecycleDelegate.context(), text, toastLength).show()
    }

    override fun log(integer: Int) {
        log(integer.toString())
    }

    override fun log(string: String) {
        Log.i(this.javaClass.simpleName, string)
    }

    override fun showRequestErrorDialog(errorData: ErrorData) {
        showAlertDialogOk("Error ${errorData.requestId}", errorData.error.message!!)
    }

    override fun showConnectionErrorDialog(errorData: ConnectionErrorData) {
        showAlertDialogOk("Connection Error ${errorData.requestId}", errorData.error.message!!)
    }

    override fun showTimeoutErrorDialog(errorData: TimeoutErrorData) {
        showAlertDialogOk("Timeout Error ${errorData.requestId}", errorData.error.message!!)
    }

    override fun showApiErrorDialog(apiErrorData: ApiErrorData) {
        val error = apiErrorData.error.errorModel
        showAlertDialogOk("${error.error()} ${apiErrorData.requestId}", error.message())
    }

    override fun showAlertDialogOk(
        title: String,
        message: String,
        cancelAble: Boolean
    ): LiveData<Int> {
        val liveData = MutableLiveData<Int>()
        AlertDialog.Builder(appLifecycleDelegate.context())
            .setTitle(title)
            .setMessage(message)
            .setCancelable(cancelAble)
            .setPositiveButton(
                android.R.string.ok
            ) { dialog: DialogInterface?, which: Int ->
                liveData.setValue(which)
            }
            .show()
        return liveData
    }

    override fun showAlertDialogYesNo(
        title: String,
        message: String,
        @DrawableRes icon: Int,
        positiveName: String,
        negativeName: String
    ): LiveData<Int> {
        val liveData = MutableLiveData<Int>()
        AlertDialog.Builder(appLifecycleDelegate.context())
            .setTitle(title)
            .setMessage(message)
            .setIcon(icon)
            .setPositiveButton(
                positiveName
            ) { _: DialogInterface?, which: Int ->
                liveData.setValue(which)
            }
            .setNegativeButton(
                negativeName
            ) { _: DialogInterface?, which: Int ->
                liveData.setValue(which)
            }
            .show()
        return liveData
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        log("onCreate")
        viewModel = getFragmentViewModel(appLifecycleDelegate.viewModelClassType)
        //compositeDisposable = new CompositeDisposable();
    }

    @CallSuper
    override fun onResume() {
        log("onResume")
    }

    @CallSuper
    override fun onStart() {
        log("onStart")
    }

    @CallSuper
    override fun onPause() {
        log("onPause")
    }

    @CallSuper
    override fun onStop() {
        log("onStop")
    }

    @CallSuper
    override fun onDestroy() {
        log("onDestroy")
        try {
            compositeDisposable.dispose()
        } catch (e: Exception) {
            log(e.toString())
        }
    }

    @CallSuper
    override fun onViewReady() {
        compositeDisposable = CompositeDisposable()
        log("onViewReady")
    }

    override fun viewModel(): VmFrameworkBaseViewModel {
        return viewModel
    }

    override fun compositeDisposable(): CompositeDisposable {
        return compositeDisposable
    }

    override fun openFragment(fragment: Int, bundle: Bundle.() -> Unit, navOptions: NavOptions?) {
        navController().navigate(fragment, Bundle().apply(bundle), navOptions)
    }

    override fun navController(): NavController {
        return appLifecycleDelegate.navController()
    }
}