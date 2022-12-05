package com.vm.framework.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import android.provider.MediaStore
import android.provider.OpenableColumns
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import androidx.annotation.AttrRes
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.updatePadding
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.chip.ChipGroup
import com.jakewharton.rxbinding2.view.RxView
import com.vm.framework.components.VmFrameworkBaseActivity
import com.vm.framework.components.VmFrameworkBaseBottomSheetDialogFragment
import com.vm.framework.components.VmFrameworkBaseFragment
import com.vm.framework.enums.DataState
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import org.joda.time.DateTime
import org.joda.time.Period
import org.joda.time.format.PeriodFormatterBuilder
import java.io.File
import java.io.FileOutputStream
import java.util.concurrent.TimeUnit
import kotlin.math.max
import kotlin.math.roundToInt


val DateTime.dobDateFormat: String
    get() {
        return toString(Utils.DOB_DATE_FORMATTER)
    }

val DateTime.appDateFormat: String
    get() {
        return toString(Utils.APP_DATE_FORMATTER)
    }


val DateTime.appTimeFormat: String
    get() {
        return toString(Utils.APP_TIME_FORMATTER)
    }

val DateTime.appTimeFormat24Hours: String
    get() {
        return toString(Utils.APP_TIME_FORMATTER_24_HOURS)
    }


val DateTime.appTimeAndDateFormat: String
    get() {
        return "$appTimeFormat $appDateFormat"
    }

val DateTime.ordinalDayOfMonth: String
    get() {
        return Utils.ordinal(dayOfMonth)
    }

val DateTime.ordinalDay: String
    get() {
        return ordinalDayOfMonth + " " + toString("MMMM")
    }

fun View.throttleClick(
    throttle: Long = 500,
    listener: () -> Unit
) {
    RxView.clicks(this)
        .throttleFirst(throttle, TimeUnit.MILLISECONDS)
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe { listener.invoke() }
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

fun VmFrameworkBaseFragment<*>.lifecycleScopeOnMain(block: suspend CoroutineScope.() -> Unit): Job {
    return lifecycleOwner.lifecycleScopeOnMain(block)
}

fun VmFrameworkBaseFragment<*>.lifecycleScopeOnMain(
    timeMillis: Long,
    block: suspend CoroutineScope.() -> Unit
): Job {
    return lifecycleOwner.lifecycleScopeOnMainWithDelay(timeMillis, block)
}

fun LifecycleOwner.lifecycleScopeOnMain(block: suspend CoroutineScope.() -> Unit): Job {
    return lifecycleScope.launchWhenStarted(block = block)
}

fun LifecycleOwner.lifecycleScopeOnMainWithDelay(
    timeMillis: Long,
    block: suspend CoroutineScope.() -> Unit
): Job {
    return lifecycleScope.launchWhenStarted {
        delay(timeMillis)
        block.invoke(this)
    }
}

val Int.appendZero: String
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
    return resources.color(colorRes)
}

@ColorInt
fun Resources.color(@ColorRes colorRes: Int): Int {
    return ResourcesCompat.getColor(this, colorRes, null)
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

fun View.paddingTop(dp: Float) {
    val px = context.dpToPx(dp).roundToInt()
    updatePadding(top = px)
}

fun View.paddingBottom(dp: Float) {
    val px = context.dpToPx(dp).roundToInt()
    updatePadding(bottom = px)
}

fun Context.themeColor(@AttrRes attrRes: Int): Int {
    val typedValue = TypedValue()
    theme.resolveAttribute(attrRes, typedValue, true)
    return typedValue.data
}

fun Context.inPortraitMode(): Boolean =
    resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT

inline fun <reified T : Activity> Context.startActivity(config: Intent.() -> Unit = {}) =
    startActivity(componentIntent<T>(config))

inline fun <reified T : Activity> Context.startActivityClear(config: Intent.() -> Unit = {}) =
    startActivity(componentIntent<T> {
        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        config.invoke(this)
    })

inline fun Activity.startActivityForResult(requestCode: Int, config: Intent.() -> Unit = {}) =
    startActivityForResult(Intent().apply(config), requestCode)

inline fun <reified T> Context.componentIntent(config: Intent.() -> Unit = {}) =
    Intent(this, T::class.java).apply(config)

val Activity.hideKeyboard: Unit
    get() {
        KeyBoardUtils.hide(this)
    }

fun Disposable.addToComposite(vmFrameworkBaseFragment: VmFrameworkBaseFragment<*>) {
    vmFrameworkBaseFragment.compositeDisposable().add(this)
}

fun Disposable.addToDisposable(disposable: CompositeDisposable) {
    disposable.add(this)
}

fun ViewPager2.next(smoothScroll: Boolean = true) {
    setCurrentItem(currentItem + 1, smoothScroll)
}

fun ViewPager2.prev(smoothScroll: Boolean = true) {
    setCurrentItem(currentItem - 1, smoothScroll)
}

val ViewPager2.isLastPage: Boolean
    get() {
        return currentItem == adapter!!.itemCount - 1
    }

val ViewPager2.isFirstPage: Boolean
    get() {
        return currentItem == 0
    }

fun View.setEqualSize(size: Int) {
    val layoutParams: ViewGroup.LayoutParams = layoutParams
    layoutParams.width = size
    layoutParams.height = size
    this.layoutParams = layoutParams
}

fun Disposable.addToComposite(vmFrameworkBaseActivity: VmFrameworkBaseActivity<*>) {
    vmFrameworkBaseActivity.compositeDisposable().add(this)
}

fun Disposable.addToComposite(bottomSheetDialogFragment: VmFrameworkBaseBottomSheetDialogFragment<*>) {
    bottomSheetDialogFragment.compositeDisposable().add(this)
}

inline fun <T> T.alsoLog(tag: String, block: (T) -> String): T {
    Log.d(tag, block(this))
    return this
}

fun <T> T.alsoLog(tag: String): T {
    alsoLog(tag) {
        it.toString()
    }
    return this
}

fun intent(): Intent {
    val intent = Intent(
        Intent.ACTION_PICK,
        MediaStore.Images.Media.INTERNAL_CONTENT_URI
    )
    intent.type = "image/*"
    intent.putExtra("crop", "true")
    intent.putExtra("scale", true)
    intent.putExtra("outputX", 256)
    intent.putExtra("outputY", 256)
    intent.putExtra("aspectX", 1)
    intent.putExtra("aspectY", 1)
    intent.putExtra("return-customers", true)
    return intent
}

fun Fragment.openPicker(intent: Intent?, requestCode: Int) {
    parentFragment!!.activity?.startActivityForResult(intent ?: intent(), requestCode)
}

fun Intent.selectedFile(context: Context): List<String> {
    val list = ArrayList<String>()
    // checking multiple selection or not
    if (null != clipData) {
        for (i in 0 until clipData!!.itemCount) {
            val uri: Uri = clipData!!.getItemAt(i).uri
            list.add(extractFile(uri, context).absolutePath)
        }
    } else {
        val uri: Uri = data!!
        list.add(extractFile(uri, context).absolutePath)
    }
    return list
}

private fun extractFile(uri: Uri, context: Context): File {
    val fileName = getFileName(uri, context)
    val extension = File(fileName).extension
    val file =
        File.createTempFile((111111..9999999).random().toString(), ".$extension")
    sa.zad.easyretrofit.Utils.writeStreamToFile(
        context.contentResolver.openInputStream(uri)!!,
        file
    )
    return file
}

@SuppressLint("Range")
private fun getFileName(uri: Uri, context: Context): String {
    var result: String? = null
    if (uri.scheme == "content") {
        val cursor = context.contentResolver.query(uri, null, null, null, null)
        try {
            if (cursor != null && cursor.moveToFirst()) {
                result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME))
            }
        } finally {
            cursor!!.close()
        }
    }
    if (result == null) {
        result = uri.path
        val cut = result!!.lastIndexOf('/')
        if (cut != -1) {
            result = result.substring(cut + 1)
        }
    }
    return result
}

fun Bitmap.save(file: File, compressFormat: Bitmap.CompressFormat = Bitmap.CompressFormat.JPEG) {
    if (file.exists()) {
        file.delete()
    }
    try {
        val out = FileOutputStream(file)
        compress(compressFormat, 90, out)
        out.flush()
        out.close()
    } catch (e: java.lang.Exception) {
        e.printStackTrace()
    }
}
