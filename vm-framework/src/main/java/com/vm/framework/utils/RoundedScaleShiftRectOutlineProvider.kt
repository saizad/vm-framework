package com.vm.framework.utils

import android.graphics.Outline
import android.graphics.Rect
import android.view.View
import android.view.ViewOutlineProvider

class RoundedScaleShiftRectOutlineProvider(
    private val cornerRadius: Float,
    private val insetX: Int = 0,
    private val insetY: Int = 0,
    private val shiftY: Int = insetY
) : ViewOutlineProvider() {

    private val rect: Rect = Rect()

    override fun getOutline(view: View, outline: Outline) {
        view.background?.copyBounds(rect)
        rect.inset(insetX, insetY)
        rect.offset(0, shiftY)
        outline.setRoundRect(rect, cornerRadius)
    }

}