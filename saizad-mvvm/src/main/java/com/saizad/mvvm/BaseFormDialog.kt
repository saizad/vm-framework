package com.saizad.mvvm

import android.content.Context
import android.widget.Button
import androidx.annotation.CallSuper
import androidx.annotation.LayoutRes
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sa.easyandroidfrom.form.FormModel
import com.saizad.mvvm.utils.bindClick
import io.reactivex.functions.Consumer

abstract class BaseFormDialog<M>(context: Context, @LayoutRes layoutRes: Int) : BaseDialog(context, layoutRes) {
    private val mutableLiveData = MutableLiveData<M>()

    init {
        setOnShowListener {
            onShow()
        }
        formActionButton().bindClick(Consumer {
            mutableLiveData.value = form().build()
            dismiss()
        })
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        dismiss()
    }

    @CallSuper
    open fun onShow() {
        form().isAllFieldValidObservable
            .subscribe {
                formActionButton().isEnabled = it
            }
    }

    abstract fun formActionButton(): Button

    @CallSuper
    fun show(data: M? = null): LiveData<M> {
        prepare(data)
        super.show()
        return mutableLiveData
    }

    final override fun show() {
        throw RuntimeException("Invalid method call")
    }

    abstract fun prepare(data: M?)

    abstract fun form(): FormModel<M>
}
