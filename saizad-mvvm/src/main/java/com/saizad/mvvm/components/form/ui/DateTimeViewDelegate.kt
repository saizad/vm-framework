package com.saizad.mvvm.components.form.ui

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import com.saizad.mvvm.ui.CustomSelectorConstraintLayout
import org.joda.time.DateTime

abstract class DateTimeViewDelegate @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    abstract fun getDateView(): CustomSelectorConstraintLayout
    abstract fun getTimeView(): CustomSelectorConstraintLayout
    abstract fun getDateTimeView(): CustomSelectorConstraintLayout

    fun bind(dateTime: DateTime?) {
        printDate(getDateView(), dateTime)
        printTime(getTimeView(), dateTime)
    }

    fun remove() {
//        printInfo(getDateView())
//        printInfo(timeView)
    }

    fun timeView() {
        getDateView().isVisible = false
        getTimeView().isVisible = true
    }

    fun dateView() {
        getTimeView().isVisible = false
        getDateView().isVisible = true
    }

    fun dateTimeView() {
        getTimeView().isVisible = true
        getDateView().isVisible = true
    }

    protected abstract fun printDate(dateView: View, dateTime: DateTime? = null)

    protected abstract fun printTime(timeView: View, dateTime: DateTime? = null)

}
