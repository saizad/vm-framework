package com.saizad.mvvm

import android.content.Context
import android.widget.Button
import androidx.annotation.CallSuper
import androidx.annotation.LayoutRes
import com.sa.easyandroidform.form.FormModel
import com.saizad.mvvm.utils.bindClick
import io.reactivex.functions.Consumer

abstract class BaseFormDialog<M, R>(context: Context, @LayoutRes layoutRes: Int) : BaseDialog<M, R>(context, layoutRes) {

    init {
        formActionButton().bindClick(Consumer {
            mutableLiveData.value = form().requiredBuild()
            dismiss()
        })
    }

    @CallSuper
    override fun onShow() {
        form().validObservable()
            .subscribe {
                formActionButton().isEnabled = it
            }
    }

    abstract fun formActionButton(): Button

    abstract fun form(): FormModel<R>
}
