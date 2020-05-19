package com.saizad.mvvm

import android.app.Dialog
import android.content.Context
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import com.saizad.mvvm.utils.ViewUtils

abstract class BaseDialog(context: Context, @LayoutRes layoutRes: Int) : Dialog(context) {

    init {
        val inflate = ViewUtils.inflate(context, layoutRes)
        setContentView(inflate)
        window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        dismiss()
    }

}
