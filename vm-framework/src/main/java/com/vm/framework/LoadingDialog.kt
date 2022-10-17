package com.vm.framework

import android.app.Dialog
import android.content.Context
import androidx.core.view.isVisible
import com.vm.framework.databinding.LoadingDialogBinding

class LoadingDialog (context: Context) : Dialog(context) {

    private var requestCounter = 0
    var loadingDialogBinding: LoadingDialogBinding = LoadingDialogBinding.inflate(layoutInflater)

    fun show(show: Boolean) {
        if (show) {
            requestCounter += 1
        } else {
            requestCounter -= 1
            requestCounter = requestCounter.coerceAtLeast(0)
        }
        if (show && (!isShowing || requestCounter == 1)) {
            show()
        } else if (!show && requestCounter == 0) {
            super.dismiss()
        } else {
           loadingDialogBinding.count.text = requestCounter.toString()
        }

        loadingDialogBinding.count.isVisible = requestCounter > 1
    }

    init {
        setContentView(loadingDialogBinding.root)
        setCancelable(true)
    }
}