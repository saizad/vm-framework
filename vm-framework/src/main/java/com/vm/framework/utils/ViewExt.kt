package com.vm.framework.utils


import android.graphics.PointF
import android.view.View
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.constraintlayout.widget.ConstraintLayout
import com.vm.framework.R
import com.vm.framework.components.form.ui.InputFieldView
import kotlinx.coroutines.flow.Flow

fun PointF.centerized(view: View): PointF {
    return PointF(x - view.width / 2, y - view.height / 2)
}

fun PointF.centered(view: View) {
    view.x = x - view.width / 2
    view.y = y - view.height / 2
}

fun View.calculateXWithView(view: View): Float {
    return view.x - ((width - view.width) / 2f)
}

fun View.calculateYWithView(view: View): Float {
    return view.y - height
}

fun View.offsetXYWithView(view: View): PointF {
    return PointF(calculateXWithView(view), calculateYWithView(view))
}


fun TextView.textColor(@ColorRes colorRes: Int) {
    return setTextColor(context.color(colorRes))
}

val View.requireIdName: String
    get() {
        return idName!!
    }

val View.idName: String?
    get() {
        if (id != View.NO_ID)
            return resources.getResourceEntryName(id)

        return null
    }

fun InputFieldView<*>.endIconFlowThrottleClick(): Flow<Unit> {
    return findViewById<View>(com.google.android.material.R.id.text_input_end_icon).flowThrottleClick()
}

fun View.updateHeightPercent(percentHeight: Float) {
    val lp = layoutParams as ConstraintLayout.LayoutParams
    lp.width = 0
    lp.matchConstraintPercentHeight = percentHeight
    layoutParams = lp
}

val View.heightPercent: Float
    get() {
        val lp = layoutParams as ConstraintLayout.LayoutParams
        return lp.matchConstraintPercentHeight
    }
