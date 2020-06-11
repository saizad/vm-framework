package com.saizad.mvvm

import android.content.Context
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.annotation.LayoutRes
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.saizad.mvvm.utils.ViewUtils

abstract class BaseBottomSheetDialog<M, R>(context: Context, @LayoutRes layoutRes: Int) : BottomSheetDialog(context) {
    protected val mutableLiveData = MutableLiveData<R>()
    protected var data: M? = null

    init {
        val inflate = ViewUtils.inflate(context, layoutRes)
        setContentView(inflate)
        window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        setOnShowListener {
            onShow()
        }
    }

    open fun onShow() {}

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        dismiss()
    }

    fun dismiss(returnData: R){
        mutableLiveData.value = returnData
        dismiss()
    }

    @CallSuper
    fun show(data: M? = null): LiveData<R> {
        this.data = data
        prepare(data)
        super.show()
        return mutableLiveData
    }

    final override fun show() {
        throw RuntimeException("Invalid method call")
    }

    abstract fun prepare(data: M?)
}
