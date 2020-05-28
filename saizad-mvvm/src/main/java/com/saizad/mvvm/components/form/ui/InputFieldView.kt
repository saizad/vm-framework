package com.saizad.mvvm.components.form.ui

import android.content.Context
import android.os.Handler
import android.util.AttributeSet
import android.widget.EditText
import com.google.android.material.textfield.TextInputLayout
import com.sa.easyandroidform.field_view.BaseInputFieldView
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
        /*handler.postDelayed( {
            editTextLayout.isEndIconVisible = false
        }, 500)
        editText.setOnFocusChangeListener { v, hasFocus ->
            editTextLayout.isEndIconVisible = hasFocus
        }*/
    }


    public  fun getEditTextLayout(): TextInputLayout{
        return editTextLayout
    }

    final override fun getEditText(): EditText {
        return editText
    }

    override fun fieldMandatory() {
    }

    override fun displayError(show: Boolean, error: String?) {
        editTextLayout.error = error
        editTextLayout.isErrorEnabled = show
    }
}
