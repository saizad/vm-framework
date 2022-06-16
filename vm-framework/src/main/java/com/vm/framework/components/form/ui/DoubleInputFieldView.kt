package com.vm.framework.components.form.ui

import android.content.Context
import android.util.AttributeSet
import com.sa.easyandroidform.ObjectUtils

open class DoubleInputFieldView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : InputFieldView<Double>(context, attrs, defStyleAttr) {


    override fun resolveFrom(charSequence: CharSequence): Double? {
        return try {
            return charSequence.toString().toDouble()
        } catch (e: Exception) {
            null
        }
    }

    override fun resolve(value: Double?): CharSequence? {
        return if (ObjectUtils.isNull(value)) {
            null
        } else value.toString()
    }

    override fun isSame(value: Double, prevValue: Double): Boolean {
        return value == prevValue
    }
}