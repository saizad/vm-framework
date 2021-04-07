package com.vm.framework.delegation

import android.content.DialogInterface
import android.location.Location
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
import com.vm.framework.VmFrameworkLocation.GPSOffException
import com.vm.framework.components.VmFrameworkBaseViewModel
import com.vm.framework.components.VmFrameworkBaseViewModel.*
import com.vm.framework.utils.addToDisposable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import rx.functions.Action1

abstract class BaseLifecycleDelegateImp<V : VmFrameworkBaseViewModel, CB : BaseCB<V>>(
    private val baseLifecycleCallBack: BaseLifecycleCallBack,
    protected val appLifecycleDelegate: CB,
    val tag: String
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
        Log.i(tag, string)
    }

    override fun requestError(errorData: ErrorData) {
        val handle =
            baseLifecycleCallBack.serverError(errorData.throwable, errorData.id)
        if (!handle) {
            showAlertDialogOk("Error ${errorData.id}", errorData.throwable.message!!)
        }
    }

    override fun requestApiError(apiErrorData: ApiErrorData) {
        val error = apiErrorData.apiErrorException.errorModel
        val handel = baseLifecycleCallBack.serverError(
            apiErrorData.apiErrorException, apiErrorData.id
        )
        if (!handel) {
            showAlertDialogOk("${error.error()} ${apiErrorData.id}", error.message())
        }
    }

    override fun showLoading(show: Boolean) {
    }

    override fun serverError(throwable: Throwable, requestId: Int): Boolean {
        return false
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

    override fun requestLocation(locationAction: Action1<Location>) {
        showLoading(true)
        appLifecycleDelegate.appLocation()
            .getLastLocation({ location: Location ->
                showLoading(false)
                locationAction.call(location)
            }) { throwable: Throwable ->
                showLoading(false)
                var title = "Error"
                var message = throwable.message
                if (throwable is SecurityException) {
                    title = "Permission Not Granted"
                    message = "Please provide location permission from the app settings"
                } else if (throwable is GPSOffException) {
                    title = "GPS is OFF"
                    message = throwable.message
                }
                showAlertDialogOk(title, message!!, true)
            }
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
        viewModel.onViewCreated()
        compositeDisposable = CompositeDisposable()
        log("onViewReady")

        viewModel.errorSubject
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                baseLifecycleCallBack.requestError(it)
            }.addToDisposable(compositeDisposable)

        viewModel.apiErrorSubject
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                baseLifecycleCallBack.requestApiError(it)
            }.addToDisposable(compositeDisposable)
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