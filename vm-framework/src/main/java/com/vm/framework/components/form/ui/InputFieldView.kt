package com.vm.framework.components.form.ui

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.EditText
import com.google.android.material.internal.CheckableImageButton
import com.google.android.material.textfield.TextInputLayout
import com.sa.easyandroidform.field_view.BaseInputFieldView
import com.vm.framework.R
import com.vm.framework.utils.KeyBoardUtils


abstract class InputFieldView<F> @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : BaseInputFieldView<F>(context, attrs, defStyleAttr) {

    private val isErrorEnabled: Boolean
    private var prevShow: Boolean = false

    init {
        val view = View.inflate(getContext(), R.layout.lib_text_input_layout, this)
        val editTextLayout = findViewById<TextInputLayout>(R.id.editTextLayout)
        val editText = findViewById<EditText>(R.id.editText)
        isErrorEnabled = editTextLayout.isErrorEnabled
        editTextLayout.findViewById<CheckableImageButton>(com.google.android.material.R.id.text_input_end_icon)?.let {
            if (!it.hasOnClickListeners()) {
                editTextLayout.setEndIconOnClickListener {
                    editText.clearFocus()
                    KeyBoardUtils.hide(context, editText)
                }
            }
        }
    }


    public fun getEditTextLayout(): TextInputLayout {
        val editTextLayout = findViewById<TextInputLayout>(R.id.editTextLayout)
        return editTextLayout
    }

    final override fun getEditText(): EditText {
        val editText = findViewById<EditText>(R.id.editText)
        return editText
    }

    override fun fieldMandatory() {
    }

    override fun displayError(show: Boolean, error: String?) {
        val editTextLayout = findViewById<TextInputLayout>(R.id.editTextLayout)
        fieldItem?.isSet?.let {
            editTextLayout.error = error
            editTextLayout.isErrorEnabled = show && isErrorEnabled && it
        }
    }

    override fun notSetError(error: String) {
        val editTextLayout = findViewById<TextInputLayout>(R.id.editTextLayout)
        editTextLayout.isErrorEnabled = false
    }
}
