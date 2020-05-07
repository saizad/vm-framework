package com.saizad.mvvm.utils

import android.view.View
import com.google.android.material.chip.ChipGroup
import io.reactivex.functions.Action
import io.reactivex.functions.Consumer
import org.joda.time.DateTime


public val DateTime.appDateFormat: String
    get() {
        return toString(Utils.APP_DATE_FORMATTER)
    }


public val DateTime.appTimeFormat: String
    get() {
        return toString(Utils.APP_TIME_FORMATTER)
    }

public val DateTime.appTimeFormat24Hours: String
    get() {
        return toString(Utils.APP_TIME_FORMATTER_24_HOURS)
    }


public val DateTime.ordinalDayOfMonth: String
    get() {
        return Utils.ordinal(dayOfMonth)
    }

public val DateTime.ordinalDay: String
    get() {
        return ordinalDayOfMonth + " " + toString("MMMM")
    }

public fun View.bindClick(
    consumer: Consumer<Any>,
    throwable: Consumer<Throwable> = Consumer { },
    onComplete: Action = Action {}
) {
    ViewUtils.bindClick(this, consumer, throwable, onComplete)
}

fun <T> ChipGroup.getSelectedChipItems(): List<T> {
    return ViewUtils.getSelectedChipItems(this)
}