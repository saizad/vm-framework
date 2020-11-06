package com.saizad.mvvm.components.form.ui

import android.content.Context
import android.util.AttributeSet
import android.widget.EditText
import com.google.android.material.internal.CheckableImageButton
import com.google.android.material.textfield.TextInputLayout
import com.sa.easyandroidform.field_view.BaseInputFieldView
import com.saizad.mvvm.R
import com.saizad.mvvm.utils.KeyBoardUtils
import com.saizad.mvvm.utils.ViewUtils
import kotlinx.android.synthetic.main.lib_text_input_layout.view.*


abstract class InputFieldView<F> @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : BaseInputFieldView<F>(context, attrs, defStyleAttr) {

    private val isErrorEnabled: Boolean
    private var prevShow: Boolean = false

    init {
        ViewUtils.inflate(getContext(), R.layout.lib_text_input_layout, this, true)
        isErrorEnabled = editTextLayout.isErrorEnabled
        editTextLayout.findViewById<CheckableImageButton>(R.id.text_input_end_icon)?.let {
            if (!it.hasOnClickListeners()) {
                editTextLayout.setEndIconOnClickListener {
                    editText.clearFocus()
                    KeyBoardUtils.hide(context, editText)
                }
            }
        }
    }


    public fun getEditTextLayout(): TextInputLayout {
        return editTextLayout
    }

    final override fun getEditText(): EditText {
        return editText
    }

    override fun fieldMandatory() {
    }

    override fun displayError(show: Boolean, error: String?) {
        fieldItem?.isSet?.let {
            editTextLayout.error = error
            editTextLayout.isErrorEnabled = show && isErrorEnabled && it
        }
    }

    override fun notSetError(error: String) {
        editTextLayout.isErrorEnabled = false
    }
}
