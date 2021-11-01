package com.vm.frameworkexample

import android.view.View
import androidx.test.espresso.NoMatchingViewException
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.ViewAssertion
import androidx.test.espresso.matcher.BoundedMatcher
import androidx.test.espresso.matcher.ViewMatchers
import com.google.android.material.internal.CheckableImageButton
import com.google.android.material.textfield.TextInputLayout
import junit.framework.Assert
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher

object EspressoUtils {

    fun doesNotShowEndIcon(): Matcher<View> {

        return object : TypeSafeMatcher<View>(TextInputLayout::class.java) {

            override fun describeTo(description: Description) {
                description.appendText("TextInputLayout doesn't show end icon.")
            }

            override fun matchesSafely(item: View): Boolean {
                // Reach in and find the end icon since we don't have a public API
                // to get a reference to it
                val endIcon = item.findViewById<View>(R.id.text_input_end_icon)
                return endIcon.visibility != View.VISIBLE
            }
        }
    }

    fun showsError(): Matcher<View> {

        return object : TypeSafeMatcher<View>(TextInputLayout::class.java) {

            override fun describeTo(description: Description) {
                description.appendText("TextInputLayout doesn't show error message")
            }

            override fun matchesSafely(item: View): Boolean {
                // Reach in and find the end icon since we don't have a public API
                // to get a reference to it
                val error = item.findViewById<View>(R.id.textinput_error)
                return error.visibility == View.VISIBLE
            }
        }
    }

    fun doesNotShowsError(): Matcher<View> {

        return object : TypeSafeMatcher<View>(TextInputLayout::class.java) {

            override fun describeTo(description: Description) {
                description.appendText("TextInputLayout doesn't show error message")
            }

            override fun matchesSafely(item: View): Boolean {
                // Reach in and find the end icon since we don't have a public API
                // to get a reference to it
                val error: View? = item.findViewById(R.id.textinput_error)
                return error == null || error.visibility == View.VISIBLE
            }
        }
    }

    fun isHintExpanded(expanded: Boolean): ViewAssertion? {
        return ViewAssertion { view: View, noViewFoundException: NoMatchingViewException? ->
            assert(view is TextInputLayout)
            Assert.assertEquals(expanded, true)
        }
    }

    public fun waitFor(ms: Long): ViewAction? {
        return object : ViewAction {
            override fun getConstraints(): Matcher<View> {
                return ViewMatchers.isRoot()
            }

            override fun getDescription(): String {
                return "wait for $ms ms"
            }

            override fun perform(uiController: UiController, view: View?) {
                uiController.loopMainThreadForAtLeast(ms)
            }
        }
    }

    fun isPasswordVisibilityToggle(enabled: Boolean): Matcher<View> {
        return object : BoundedMatcher<View, TextInputLayout>(TextInputLayout::class.java) {
            override fun describeTo(description: Description) {
                description.appendText("is password visibility toggle enabled")
            }

            override fun matchesSafely(view: TextInputLayout): Boolean {
                val endIcon: CheckableImageButton = view.findViewById(R.id.text_input_end_icon)
                return enabled == endIcon.isChecked
            }
        }
    }

}