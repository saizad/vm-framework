package com.saizad.mvvm.utils

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.TypedValue
import android.view.View
import androidx.annotation.AttrRes
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.google.android.material.chip.ChipGroup
import com.jakewharton.rxbinding2.view.RxView
import com.saizad.mvvm.components.SaizadBaseFragment
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.coroutines.*
import org.joda.time.DateTime
import org.joda.time.Period
import org.joda.time.format.PeriodFormatterBuilder
import java.util.concurrent.TimeUnit
import kotlin.math.max
import kotlin.math.roundToInt


public val DateTime.dobDateFormat: String
    get() {
        return toString(Utils.DOB_DATE_FORMATTER)
    }

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


public val DateTime.appTimeAndDateFormat: String
    get() {
        return "$appTimeFormat $appDateFormat"
    }

public val DateTime.ordinalDayOfMonth: String
    get() {
        return Utils.ordinal(dayOfMonth)
    }

public val DateTime.ordinalDay: String
    get() {
        return ordinalDayOfMonth + " " + toString("MMMM")
    }

public fun View.throttleClick(
    throttle: Long = 500,
    listener: () -> Unit
) {
    RxView.clicks(this)
        .throttleFirst(throttle, TimeUnit.MILLISECONDS)
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe { listener.invoke() }
}

fun <T> ChipGroup.getSelectedChipItems(): List<T> {
    return ViewUtils.getSelectedChipItems(this)
}

val DateTime.notificationTimeStamp: String
    get() {
        val period = Period(this, DateTime())
        val formatter = PeriodFormatterBuilder()
            .appendSeconds().appendSuffix(" sec", " secs").appendSuffix(" ago\n")
            .appendMinutes().appendSuffix(" min", " mins").appendSuffix(" ago\n")
            .appendHours().appendSuffix(" hour", " hours").appendSuffix(" ago\n")
            .appendDays().appendSuffix(" day", " days").appendSuffix(" ago\n")
            .appendWeeks().appendSuffix(" week", " weeks").appendSuffix(" ago\n")
            .appendMonths().appendSuffix(" month", " month").appendSuffix(" ago\n")
            .appendYears().appendSuffix(" year", " years").appendSuffix(" ago\n")
            .printZeroNever().rejectSignedValues(false)
            .toFormatter()
        val lines = formatter.print(period).lines()
        return lines[max(lines.size - 2, 0)]
    }

public fun SaizadBaseFragment<*>.lifecycleScopeOnMain(block: suspend CoroutineScope.() -> Unit): Job {
    return viewLifecycleOwner.lifecycleScopeOnMain(block)
}

public fun SaizadBaseFragment<*>.lifecycleScopeOnMain(
    timeMillis: Long,
    block: suspend CoroutineScope.() -> Unit
): Job {
    return viewLifecycleOwner.lifecycleScopeOnMainWithDelay(timeMillis, block)
}

public fun LifecycleOwner.lifecycleScopeOnMain(block: suspend CoroutineScope.() -> Unit): Job {
    return lifecycleScope.launch(Dispatchers.Main, block = block)
}

public fun LifecycleOwner.lifecycleScopeOnMainWithDelay(
    timeMillis: Long,
    block: suspend CoroutineScope.() -> Unit
): Job {
    return lifecycleScope.launch(Dispatchers.Main) {
        delay(timeMillis)
        block.invoke(this)
    }
}

public val Int.appendZero: String
    get() {
        if (this < 10) {
            return "0${this}"
        }
        return this.toString()
    }

fun Context.getQuantityStringZero(
    quantity: Int,
    pluralResId: Int,
    zeroResId: Int? = null
): String {
    return if (zeroResId != null && quantity == 0) {
        resources.getString(zeroResId)
    } else {
        resources.getQuantityString(pluralResId, quantity, quantity)
    }
}

fun Context.color(@ColorRes colorRes: Int): Int {
    return ResourcesCompat.getColor(resources, colorRes, null)
}

fun Context.drawable(@DrawableRes drawableRes: Int): Drawable? {
    return AppCompatResources.getDrawable(this, drawableRes)
}

fun Context.pxToDp(px: Float): Float {
    return MetricsUtil.convertPixelsToDp(px, this)
}

fun Context.pxToDpInt(px: Float): Int {
    return pxToDp(px).roundToInt()
}

fun Context.dpToPx(dp: Float): Float {
    return MetricsUtil.convertDpToPixel(dp, this)
}

fun Context.dpToPxInt(dp: Float): Int {
    return dpToPx(dp).roundToInt()
}

fun View.padding(dp: Float) {
    val px = context.dpToPx(dp).roundToInt()
    this.setPadding(px, px, px, px)
}

fun Context.themeColor(@AttrRes attrRes: Int): Int {
    val typedValue = TypedValue()
    theme.resolveAttribute(attrRes, typedValue, true)
    return typedValue.data
}