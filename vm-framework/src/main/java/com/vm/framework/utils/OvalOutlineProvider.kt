package com.vm.framework.utils

import android.graphics.Outline
import android.graphics.Rect
import android.view.View
import com.vm.framework.utils.BaseOutlineProvider

class OvalOutlineProvider(
    scaleX: Float = 1f,
    scaleY: Float = 1f,
    yShift: Int = 0
) : BaseOutlineProvider(scaleX, scaleY, yShift) {

    override fun rectReady(rect: Rect, view: View, outline: Outline) {
        outline.setOval(rect)
    }
}