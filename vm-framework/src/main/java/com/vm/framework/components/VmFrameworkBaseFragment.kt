package com.vm.framework.components

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.annotation.CallSuper
import androidx.annotation.DrawableRes
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelStoreOwner
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.vm.framework.ActivityResult
import com.vm.framework.delegation.fragment.FragmentAppLifecycleCallBack
import com.vm.framework.delegation.fragment.FragmentAppLifecycleDelegate
import com.vm.framework.delegation.fragment.FragmentAppLifecycleDelegateImp
import com.vm.framework.delegation.fragment.FragmentCB
import com.vm.framework.error.ApiErrorData
import com.vm.framework.error.ConnectionErrorData
import com.vm.framework.error.ErrorData
import com.vm.framework.error.TimeoutErrorData
import io.reactivex.disposables.CompositeDisposable

abstract class VmFrameworkBaseFragment<V : VmFrameworkBaseViewModel> :
    Fragment(), FragmentAppLifecycleDelegate, FragmentCB<V>,
    FragmentAppLifecycleCallBack {

   open val delegate: FragmentAppLifecycleDelegate by lazy {
       FragmentAppLifecycleDelegateImp(this, this)
   }

    override fun context(): Context {
        return requireContext()
    }

    override fun viewModelStoreOwner(): ViewModelStoreOwner {
        return this
    }

    override val lifecycleOwner: LifecycleOwner
        get() = viewLifecycleOwner


    abstract override val viewModelClassType: Class<V>

    override fun navController(): NavController {
        return findNavController()
    }

    override fun showLongToast(text: CharSequence) {
        delegate.showLongToast(text)
    }

    override fun showShortToast(value: Int) {
        delegate.showShortToast(value)
    }

    override fun showShortToast(text: CharSequence) {
        delegate.showShortToast(text)
    }

    override fun showToast(text: CharSequence, toastLength: Int) {
        delegate.showToast(text, toastLength)
    }

    override fun log(integer: Int) {
        delegate.log(integer)
    }

    override fun log(string: String) {
        delegate.log(string)
    }

    override fun <T> finishWithResult(activityResult: ActivityResult<T>) {
        delegate.finishWithResult(activityResult)
    }

    override fun finish() {
        delegate.finish()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        delegate.onViewCreated(view, savedInstanceState)
    }

    override fun onViewReady() {}

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        delegate.onCreateOptionsMenu(menu, inflater)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?, recycled: Boolean) {}

    override fun setHasOptionsMenu(hasMenu: Boolean) {
        super.setHasOptionsMenu(hasMenu)
    }

    override fun onBackPressed(): Boolean {
        return false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        delegate.onCreate(savedInstanceState)
    }

    override fun showConnectionErrorDialog(errorData: ConnectionErrorData) {
        delegate.showConnectionErrorDialog(errorData)
    }

    override fun showTimeoutErrorDialog(errorData: TimeoutErrorData) {
        delegate.showTimeoutErrorDialog(errorData)
    }

    override fun showRequestErrorDialog(errorData: ErrorData) {
        delegate.showRequestErrorDialog(errorData)
    }

    override fun showApiErrorDialog(apiErrorData: ApiErrorData) {
        delegate.showApiErrorDialog(apiErrorData)
    }

    @CallSuper
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        delegate.onActivityCreated(savedInstanceState)
    }

    @CallSuper
    override fun onPause() {
        super.onPause()
        delegate.onPause()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        delegate.onAttach(context)
    }

    @CallSuper
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        return delegate.onCreateView(inflater, container, savedInstanceState)
    }

    @CallSuper
    override fun onStart() {
        super.onStart()
        delegate.onStart()
    }

    @CallSuper
    override fun onStop() {
        super.onStop()
        delegate.onStop()
    }

    @CallSuper
    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        delegate.onViewStateRestored(savedInstanceState)
    }

    @CallSuper
    override fun onDestroy() {
        super.onDestroy()
        delegate.onDestroy()
    }

    @CallSuper
    override fun onResume() {
        super.onResume()
        delegate.onResume()
    }

    @CallSuper
    override fun onDestroyView() {
        super.onDestroyView()
        delegate.onDestroyView()
    }

    @CallSuper
    override fun onDetach() {
        super.onDetach()
        delegate.onDetach()
    }

    override fun showAlertDialogOk(
        title: String,
        message: String,
        cancelAble: Boolean
    ): LiveData<Int> {
        return delegate.showAlertDialogOk(title, message, cancelAble)
    }

    override fun showAlertDialogYesNo(
        title: String,
        message: String,
        @DrawableRes icon: Int,
        positiveName: String,
        negativeName: String
    ): LiveData<Int> {
        return delegate.showAlertDialogYesNo(title, message, icon, positiveName, negativeName)
    }

    override fun openFragment(
        @IdRes fragment: Int,
        bundle: Bundle.() -> Unit,
        navOptions: NavOptions?
    ) {
        delegate.openFragment(fragment, bundle, navOptions)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        permissionManager()
            .onRequestPermissionsResult(activity, requestCode, permissions, grantResults)
    }

    override fun viewModel(): V {
        return delegate.viewModel() as V
    }

    override fun compositeDisposable(): CompositeDisposable {
        return delegate.compositeDisposable()
    }

    override fun persistView(): Boolean {
        return true
    }

    override fun menRes(): Int {
        return 0
    }

    override fun bundle(): Bundle? {
        return arguments
    }

}