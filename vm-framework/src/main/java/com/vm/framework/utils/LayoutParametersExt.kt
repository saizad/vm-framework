package com.vm.framework.utils

import android.view.View
import android.view.ViewGroup
import androidx.core.view.updateLayoutParams
import androidx.core.view.updateMargins

fun View.setMarginTop(value: Int) = updateLayoutParams<ViewGroup.MarginLayoutParams> {
    updateMargins(top = value)
}

fun View.setMarginBottom(value: Int) = updateLayoutParams<ViewGroup.MarginLayoutParams> {
    updateMargins(bottom = value)
}

fun View.setMarginStart(value: Int) = updateLayoutParams<ViewGroup.MarginLayoutParams> {
    updateMargins(left = value)
}

fun View.setMarginEnd(value: Int) = updateLayoutParams<ViewGroup.MarginLayoutParams> {
    updateMargins(right = value)
}