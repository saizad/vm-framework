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
import kotlinx.android.synthetic.main.lib_text_input_layout.view.*


abstract class InputFieldView<F> @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : BaseInputFieldView<F>(context, attrs, defStyleAttr) {

    private val isErrorEnabled: Boolean
    private var prevShow: Boolean = false

    init {
        val view = View.inflate(getContext(), R.layout.lib_text_input_layout, this)
        isErrorEnabled = view.editTextLayout.isErrorEnabled
        view.editTextLayout.findViewById<CheckableImageButton>(com.google.android.material.R.id.text_input_end_icon)?.let {
            if (!it.hasOnClickListeners()) {
                view.editTextLayout.setEndIconOnClickListener {
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
