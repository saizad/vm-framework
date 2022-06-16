package com.vm.framework

import android.content.Context
import android.content.DialogInterface
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.annotation.LayoutRes
import androidx.annotation.StyleRes
import com.google.android.material.bottomsheet.BottomSheetDialog
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

abstract class BaseBottomSheetDialog<M, R>(
    context: Context,
    @LayoutRes layoutRes: Int,
    @StyleRes theme: Int = 0
) : BottomSheetDialog(context, theme) {
    private val flow = MutableStateFlow<R?>(null)
    protected var data: M? = null
    protected val compositeDisposable = CompositeDisposable()
    private var dismissJob: Job? = null

    init {
        val inflate = View.inflate(context, layoutRes, null)
        setContentView(inflate)
        window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        super.setOnShowListener {
            onShow()
        }
    }

    final override fun setOnShowListener(listener: DialogInterface.OnShowListener?) {
    }

    open fun onShow() {}

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        dismissJob?.cancel()
        dismiss()
        compositeDisposable.dispose()
    }

    fun dismiss(returnData: R, delay: Long = 0) {
        dismissJob?.cancel()

        dismissJob = GlobalScope.launch(Dispatchers.Main) {
            kotlinx.coroutines.delay(delay)
            flow.value = returnData
            dismiss()
        }
    }

    @CallSuper
    open fun show(data: M? = null): Flow<R?> {
        this.data = data
        prepare(data)
        super.show()
        return flow.drop(1)
    }

    final override fun show() {
        throw RuntimeException("Invalid method call")
    }

    abstract fun prepare(data: M?)

}
