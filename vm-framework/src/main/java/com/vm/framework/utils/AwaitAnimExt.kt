package com.vm.framework.utils

import android.animation.*
import android.graphics.PointF
import android.view.View
import android.view.ViewPropertyAnimator
import android.view.animation.Animation
import android.view.animation.DecelerateInterpolator
import android.widget.ProgressBar
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.core.view.doOnLayout
import androidx.core.view.marginBottom
import androidx.core.view.updatePadding
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CancellableContinuation
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.suspendCancellableCoroutine

/**
 * @author https://gist.github.com/chrisbanes/227b4d253da94c5f76552a7d9360b542/raw/61739a529b2a0fe7d6d1b9fc9a79a46d5fce471b/code.kt
 */
suspend fun View.awaitNextLayout() = suspendCancellableCoroutine<Unit> { cont ->
    // This lambda is invoked immediately, allowing us to create
    // a callback/listener

    doOnLayout {  }
    val listener = object : View.OnLayoutChangeListener {
        override fun onLayoutChange(
            view: View,
            left: Int,
            top: Int,
            right: Int,
            bottom: Int,
            oldLeft: Int,
            oldTop: Int,
            oldRight: Int,
            oldBottom: Int
        ) {
            // The next layout has happened!
            // First remove the listener to not leak the coroutine
            view.removeOnLayoutChangeListener(this)
            // Finally resume the continuation, and
            // wake the coroutine up
            cont.resume(Unit) {}
        }
    }
    // If the coroutine is cancelled, remove the listener
    cont.invokeOnCancellation { removeOnLayoutChangeListener(listener) }
    // And finally add the listener to view
    addOnLayoutChangeListener(listener)

    // The coroutine will now be suspended. It will only be resumed
    // when calling cont.resume() in the listener above
}

fun View.toXTranslation(
    toX: Float,
    duration: Long = 200,
    interpolator: TimeInterpolator = DecelerateInterpolator(),
): ViewPropertyAnimator {

    return animate().apply {
        this.duration = duration
        this.interpolator = interpolator
        translationX(toX)
    }
}

fun View.toYTranslation(
    toY: Float,
    duration: Long = 200,
    interpolator: TimeInterpolator = DecelerateInterpolator(),
): ViewPropertyAnimator {

    return animate().apply {
        this.duration = duration
        this.interpolator = interpolator
        translationY(toY)
    }
}


suspend fun View.toXYTranslation(coroutineScope: CoroutineScope, pointF: PointF, duration: Long = 200) {

    val yAsync = coroutineScope.async {
        toYTranslation(pointF.y, duration).awaitEnd()
    }

    val xAsync = coroutineScope.async {
        toXTranslation(pointF.x, duration).awaitEnd()
    }

    yAsync.await()
    xAsync.await()
}

suspend fun CoroutineScope.hideShowAnimation(hide: View, show: View, duration: Long = 200) {

    val hideAsync = async {
        hide.invisibleAnimation(duration).awaitEnd()
    }

    val showAsync = async {
        show.visibleAnimation(duration).awaitEnd()
    }

    hideAsync.await()
    showAsync.await()
}

suspend fun CoroutineScope.scaleHideShowAnimation(hide: View, show: View, duration: Long = 200) {

    val hideAsync = async {
        hide.scaleAnimation(duration, 0f).awaitEnd()
    }

    val showAsync = async {
        show.scaleX = 0f
        show.scaleY = 0f
        show.scaleAnimation(duration, 1f).awaitEnd()
    }

    hideAsync.await()
    showAsync.await()
}

fun View.yTranslation(
    fromY: Float,
    duration: Long = 200,
    interpolator: TimeInterpolator = DecelerateInterpolator(),
): ViewPropertyAnimator {
    translationY = fromY

    return animate().apply {
        this.duration = duration
        this.interpolator = interpolator
        translationY(0f)
    }
}

suspend fun Array<out View>.yTranslationAwait(coroutineScope: CoroutineScope, fromY: Float = 100f, duration: Long = 200) {
    mergeAwait(coroutineScope) {
        it.yTranslation(fromY, duration).awaitEnd()
    }
}

fun View.invisibleAnimation(
    duration: Long = 200,
    interpolator: TimeInterpolator = DecelerateInterpolator(),
): ViewPropertyAnimator {
    return animate().apply {
        this.duration = duration
        this.interpolator = interpolator
        alpha(0f)
    }
}

fun View.visibleAnimation(
    duration: Long = 200,
    interpolator: TimeInterpolator = DecelerateInterpolator(),
): ViewPropertyAnimator {
    alpha = 0f
    return animate().apply {
        this.duration = duration
        this.interpolator = interpolator
        alpha(1f)
    }
}

fun View.scaleAnimation(
    duration: Long = 200,
    scale: Float = 0f,
    interpolator: TimeInterpolator = DecelerateInterpolator(),
): ViewPropertyAnimator {
    return animate().apply {
        this.duration = duration
        this.interpolator = interpolator
        scaleY(scale)
        scaleX(scale)
    }
}
fun View.scaleInAnimation(
    duration: Long = 200,
    interpolator: TimeInterpolator = DecelerateInterpolator(),
): ViewPropertyAnimator {
    scaleX = 0f
    scaleY = 0f
    return animate().apply {
        this.duration = duration
        this.interpolator = interpolator
        scaleY(1f)
        scaleX(1f)
    }
}

fun View.scaleYAnimation(
    duration: Long = 200,
    upTo: Float = 0f,
    interpolator: TimeInterpolator = DecelerateInterpolator(),
): ViewPropertyAnimator {
    return animate().apply {
        this.duration = duration
        this.interpolator = interpolator
        scaleY(upTo)
    }
}

suspend fun Array<out View>.invisibleAnimationAwait(coroutineScope: CoroutineScope, duration: Long, interpolator: TimeInterpolator = DecelerateInterpolator()) {
    mergeAwait(coroutineScope) {
        it.invisibleAnimation(duration, interpolator).awaitEnd()
    }
}

suspend fun <T>  Array<out T>.mergeAwait(coroutineScope: CoroutineScope, listener: suspend (T) -> Unit) {
    map {
        coroutineScope.async {
            listener(it)
        }
    }.forEach {
        it.await()
    }
}

suspend fun ViewPropertyAnimator.awaitEnd() = suspendCancellableCoroutine<Unit> { cont ->
    setListener(awaitAnimatorListenerAdapter(cont))
}

suspend fun Animator.awaitEnd() = suspendCancellableCoroutine<Unit> { cont ->
    addListener(awaitAnimatorListenerAdapter(cont))
}

suspend fun List<Animator>.startAwaitEnd(coroutineScope: CoroutineScope) {
    toTypedArray().mergeAwait(coroutineScope){
        it.start()
        it.awaitEnd()
    }
}

fun awaitAnimatorListenerAdapter(cont: CancellableContinuation<Unit>): AnimatorListenerAdapter {
    // Add an invokeOnCancellation listener. If the coroutine is
    // cancelled, cancel the animation too that will notify
    // listener's onAnimationCancel() function
    cont.invokeOnCancellation { cont.cancel() }
    return object : AnimatorListenerAdapter() {
        private var endedSuccessfully = true

        override fun onAnimationCancel(animation: Animator) {
            // Animator has been cancelled, so flip the success flag
            endedSuccessfully = false
        }

        override fun onAnimationEnd(animation: Animator) {
            // Make sure we remove the listener so we don't keep
            // leak the coroutine continuation
            animation.removeListener(this)

            if (cont.isActive) {
                // If the coroutine is still active...
                if (endedSuccessfully) {
                    // ...and the Animator ended successfully, resume the coroutine
                    cont.resume(Unit) {}
                } else {
                    // ...and the Animator was cancelled, cancel the coroutine too
                    cont.cancel()
                }
            }
        }
    }
}

suspend fun Animation.waitToLoadAnimation() = suspendCancellableCoroutine<Unit> { cont ->

    setAnimationListener(object : Animation.AnimationListener {
        private var endedSuccessfully = true

        override fun onAnimationStart(animation: Animation) {
        }

        override fun onAnimationEnd(animation: Animation) {
            // Make sure we remove the listener so we don't keep
            // leak the coroutine continuation
            setAnimationListener(null)

            if (cont.isActive) {
                // If the coroutine is still active...
                if (endedSuccessfully) {
                    // ...and the Animator ended successfully, resume the coroutine
                    cont.resume(Unit) {}
                } else {
                    // ...and the Animator was cancelled, cancel the coroutine too
                    cont.cancel()
                }
            }
        }

        override fun onAnimationRepeat(animation: Animation?) {
//            endedSuccessfully = true
        }

    })
}

suspend fun RecyclerView.waitToLoadAnimation() = suspendCancellableCoroutine<Unit> { cont ->

    adapter?.let {
        if (it.itemCount == 0) {
            cont.resume(Unit) {}
        }
    }

    layoutAnimationListener = object : Animation.AnimationListener {
        private var endedSuccessfully = true

        override fun onAnimationStart(animation: Animation) {
        }

        override fun onAnimationEnd(animation: Animation) {
            // Make sure we remove the listener so we don't keep
            // leak the coroutine continuation
            layoutAnimationListener = null

            if (cont.isActive) {
                // If the coroutine is still active...
                if (endedSuccessfully) {
                    // ...and the Animator ended successfully, resume the coroutine
                    cont.resume(Unit) {}
                } else {
                    // ...and the Animator was cancelled, cancel the coroutine too
                    cont.cancel()
                }
            }
        }

        override fun onAnimationRepeat(animation: Animation?) {
//            endedSuccessfully = true
        }

    }
}

fun MotionLayout.setTransitionListenerOnCompleted(listener: (Int) -> Unit) {

    setTransitionListener(object : MotionLayoutTransitionListenerAdapter() {
        override fun onTransitionCompleted(motionLayout: MotionLayout?, currentId: Int) {
            listener.invoke(currentId)
        }
    })
}

suspend fun MotionLayout.setTransitionListenerOnCompletedAwait() = suspendCancellableCoroutine<Unit> { cont ->

    setTransitionListener(object : MotionLayoutTransitionListenerAdapter() {
        private var endedSuccessfully = true

        override fun onTransitionCompleted(motionLayout: MotionLayout?, currentId: Int) {

            removeTransitionListener(this)

            if (cont.isActive) {
                // If the coroutine is still active...
                if (endedSuccessfully) {
                    // ...and the Animator ended successfully, resume the coroutine
                    cont.resume(Unit) {}
                } else {
                    // ...and the Animator was cancelled, cancel the coroutine too
                    cont.cancel()
                }
            }
        }
    })
}

suspend fun ProgressBar.animate(from: Int, to: Int): ValueAnimator {
    return ObjectAnimator.ofInt(this, "progress", from, to)
        .apply {
            duration = 5000
            start()
        }
}

fun View.animateSize(from: Int = height, to: Int, duration: Long = 400): ValueAnimator {
    return ObjectAnimator.ofInt(from, to)
        .apply {
            this.duration = duration
            addUpdateListener {
                val value = it.animatedValue as Int
                this@animateSize.setEqualSize(value)
            }
            start()
        }
}

fun View.paddingTopAnimation(
    padding: Int,
    animDuration: Long? = 400,
    interpolator: TimeInterpolator? = null
): ValueAnimator? {
    if (paddingTop != padding)
        return simpleIntValueAnimator(paddingTop, padding, animDuration, interpolator) {
            updatePadding(top = it)
        }
    return null
}

fun View.paddingBottomAnimation(
    padding: Int,
    animDuration: Long? = 400,
    interpolator: TimeInterpolator? = null
): ValueAnimator? {
    if (paddingBottom != padding)
        return simpleIntValueAnimator(paddingBottom, padding, animDuration, interpolator) {
            updatePadding(bottom = it)
        }
    return null
}

fun View.marginBottomAnimation(
    newMarginBottom: Int,
    animDuration: Long? = 400,
    interpolator: TimeInterpolator? = null
): ValueAnimator? {
    if (marginBottom != newMarginBottom)
        return simpleIntValueAnimator(marginBottom, newMarginBottom, animDuration, interpolator) {
            setMarginBottom(it)
        }
    return null
}

fun simpleIntValueAnimator(
    from: Int,
    to: Int,
    animDuration: Long?,
    interpolator: TimeInterpolator?,
    listener: (Int) -> Unit
): ValueAnimator {
    return ObjectAnimator.ofInt(from, to).apply {
        animDuration?.let { this.duration = it }
        interpolator?.let { this.interpolator = it }
        start()
        addUpdateListener {
            val value = it.animatedValue as Int
            listener.invoke(value)
        }
    }
}