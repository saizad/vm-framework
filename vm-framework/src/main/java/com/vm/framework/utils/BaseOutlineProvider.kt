package com.vm.framework.utils

import android.graphics.Outline
import android.graphics.Rect
import android.view.View
import android.view.ViewOutlineProvider
import androidx.annotation.FloatRange

abstract class BaseOutlineProvider(
    var scaleX: Float = 1f,
    var scaleY: Float = 1f,
    var yShift: Int = 0,
    var xShift: Int = 0
) : ViewOutlineProvider() {

    companion object {
        fun scale(scale: Float): Float {
            return 1 + scale / 100f
        }
    }

    init {
        scaleX = scale(scaleX)
        scaleY = scale(scaleY)
    }

    private val rect: Rect = Rect()

    final override fun getOutline(view: View, outline: Outline) {
        view.background?.copyBounds(rect)
        rect.scale(scaleX, scaleY)
        rect.offset(xShift, yShift)
        rectReady(rect, view, outline)
    }

    abstract fun rectReady(rect: Rect, view: View, outline: Outline)

}

fun Rect.scale(
    @FloatRange(from = -1.0, to = 1.0) scaleX: Float,
    @FloatRange(from = -1.0, to = 1.0) scaleY: Float
) {
    val newWidth = width() * scaleX
    val newHeight = height() * scaleY
    val deltaX = (width() - newWidth) / 2
    val deltaY = (height() - newHeight) / 2

    set(
        (left + deltaX).toInt(),
        (top + deltaY).toInt(),
        (right - deltaX).toInt(),
        (bottom - deltaY).toInt()
    )
}