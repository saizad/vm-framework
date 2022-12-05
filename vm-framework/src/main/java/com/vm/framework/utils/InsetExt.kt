package com.vm.framework.utils

import android.view.View
import androidx.core.graphics.Insets
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

val WindowInsetsCompat.statusBarInsets: Insets
    get() = getInsets(WindowInsetsCompat.Type.statusBars())

val WindowInsetsCompat.navigationBarInsets: Insets
    get() = getInsets(WindowInsetsCompat.Type.navigationBars())

fun View.insetsListener(insetsListener: (WindowInsetsCompat) -> WindowInsetsCompat) {
    ViewCompat.setOnApplyWindowInsetsListener(this) { _, insets ->
        insetsListener(insets)
    }
}

fun View.moveBelowStatusBar(statusBarHeight: Int, vararg views: View) {
    insetsListener { insets ->
        views.forEach {
            it.setMarginTop(insets.statusBarInsets.top + statusBarHeight)
        }
        insets
    }
}