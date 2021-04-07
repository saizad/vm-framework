package com.vm.framework

import android.content.Context
import android.widget.Button
import androidx.annotation.CallSuper
import androidx.annotation.LayoutRes
import com.sa.easyandroidform.form.FormModel
import com.vm.framework.utils.throttleClick

abstract class BaseFormDialog<M, R>(context: Context, @LayoutRes layoutRes: Int) :
    BaseDialog<M, R>(context, layoutRes) {

    init {
        formActionButton().throttleClick{
            mutableLiveData.value = form().requiredBuild()
            dismiss()
        }
    }

    @CallSuper
    override fun onShow() {
        compositeDisposable.add(form().validObservable()
            .subscribe {
                formActionButton().isEnabled = it
            })
    }

    abstract fun formActionButton(): Button

    abstract fun form(): FormModel<R>
}
