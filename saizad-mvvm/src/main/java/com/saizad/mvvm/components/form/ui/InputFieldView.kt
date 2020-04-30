package com.saizad.mvvm.components.form.ui

import android.content.Context
import android.os.Handler
import android.util.AttributeSet
import android.widget.EditText
import com.sa.easyandroidfrom.ObjectUtils
import com.sa.easyandroidfrom.field_view.BaseInputFieldView
import com.saizad.mvvm.R
import com.saizad.mvvm.utils.ViewUtils
import kotlinx.android.synthetic.main.lib_text_input_layout.view.*


abstract class InputFieldView<F> @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0) :
    BaseInputFieldView<F>(context, attrs, defStyleAttr) {

    init {
        ViewUtils.inflate(getContext(), R.layout.lib_text_input_layout, this, true)
        val handler = Handler()
        handler.postDelayed( {
            editTextLayout.isEndIconVisible = false
        }, 1000)
        editText.setOnFocusChangeListener { v, hasFocus ->
            editTextLayout.isEndIconVisible = hasFocus
        }
    }


    final override fun getEditText(): EditText {
        return editText
    }

    override fun fieldMandatory() {
    }

    override fun error() {
    }

    override fun edited() {
    }

    override fun neutral() {
    }

    override fun displayError(show: Boolean, error: String?) {
        editTextLayout.error = error
        editTextLayout.isErrorEnabled = show
    }

    final override fun isSame(value: F?, prevValue: F?): Boolean {

        if (ObjectUtils.isNull(value) && ObjectUtils.isNull(prevValue)) {
            return true
        } else if (ObjectUtils.isNotNull(value) && ObjectUtils.isNull(prevValue)) {
            return false
        } else if(value != null && prevValue != null){
            return compare(value, prevValue)
        }
        return false
    }

    abstract fun compare(value: F, prevValue: F): Boolean

}
