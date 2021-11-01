package com.vm.frameworkexample.components.auth.login

import android.util.Log
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.truth.content.IntentSubject
import androidx.test.filters.LargeTest
import androidx.test.platform.app.InstrumentationRegistry
import androidx.work.Configuration
import androidx.work.impl.utils.SynchronousExecutor
import androidx.work.testing.WorkManagerTestInitHelper
import com.google.common.collect.Iterables
import com.vm.framework.delegation.BaseLifecycleDelegateImp
import com.vm.framework.delegation.fragment.FragmentCB
import com.vm.frameworkexample.*
import com.vm.frameworkexample.EspressoUtils.doesNotShowEndIcon
import com.vm.frameworkexample.EspressoUtils.doesNotShowsError
import com.vm.frameworkexample.EspressoUtils.isPasswordVisibilityToggle
import com.vm.frameworkexample.EspressoUtils.showsError
import com.vm.frameworkexample.EspressoUtils.waitFor
import com.vm.frameworkexample.Utils.FAKE_USER
import com.vm.frameworkexample.api.AuthApi
import com.vm.frameworkexample.components.main.MainActivity
import com.vm.frameworkexample.di.auth.AuthEnvironment
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import org.hamcrest.Matchers.allOf
import org.hamcrest.core.IsNot.not
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import javax.inject.Inject


@LargeTest
@HiltAndroidTest
@ExperimentalCoroutinesApi
class LoginFragmentTest : CoroutineTest() {

    @get:Rule(order = 0)
    var hiltRule = HiltAndroidRule(this)

    lateinit var loginViewModel: LoginViewModel

    @Inject
    lateinit var authEnvironment: AuthEnvironment

    @Before
    fun setUp() {
        hiltRule.inject()
        val environment = AuthEnvironment(
            Mockito.mock(AuthApi::class.java),
            authEnvironment.fcmToken,
            authEnvironment.activityResultBehaviorSubject,
            Mockito.mock(MVVMExampleCurrentUser::class.java),
            authEnvironment.notificationBehaviorSubject,
            authEnvironment.permissionManager
        )
        loginViewModel = LoginViewModel(authEnvironment)
    }

    @Test
    fun emptyEmailDisableButton() {
        val navController = Mockito.mock(NavController::class.java)

        launchFragmentInHiltContainer<LoginFragment> {
            Navigation.setViewNavController(requireView(), navController)
        }

        onView(allOf(withId(R.id.editText), allOf(isDescendantOfA(withId(R.id.emailField)))))
            .perform(clearText())
            .perform(closeSoftKeyboard())

        onView(isRoot()).perform(waitFor(1000))
        onView(withId(R.id.login)).check(matches(not(isEnabled())))
    }

    @Test
    fun invalidEmailDisableButton_and_showsError() {
        val navController = Mockito.mock(NavController::class.java)

        launchFragmentInHiltContainer<LoginFragment> {
            Navigation.setViewNavController(requireView(), navController)
        }

        onView(allOf(withId(R.id.editText), allOf(isDescendantOfA(withId(R.id.emailField)))))
            .perform(clearText())
            .perform(typeText("invalidEmail"))
            .perform(closeSoftKeyboard())

        onView(isRoot()).perform(waitFor(1000))
        onView(withId(R.id.login)).check(matches(not(isEnabled())))
        onView(allOf(withId(R.id.editTextLayout), allOf(isDescendantOfA(withId(R.id.emailField)))))
            .check(matches(showsError()))
    }

    @Test
    fun emailClearText_endIconNotVisible_and_doesNotShowsError() {
        val navController = Mockito.mock(NavController::class.java)

        launchFragmentInHiltContainer<LoginFragment> {
            Navigation.setViewNavController(requireView(), navController)
        }

        onView(
            allOf(
                withId(R.id.text_input_end_icon),
                allOf(isDescendantOfA(withId(R.id.emailField)))
            )
        )
            .perform(click())

        onView(isRoot()).perform(waitFor(1000))
        onView(withId(R.id.login)).check(matches(not(isEnabled())))
        onView(allOf(withId(R.id.editTextLayout), allOf(isDescendantOfA(withId(R.id.emailField)))))
            .check(matches(doesNotShowEndIcon()))
        onView(allOf(withId(R.id.editTextLayout), allOf(isDescendantOfA(withId(R.id.emailField)))))
            .check(matches(doesNotShowsError()))
    }

    @Test
    fun passwordToggle_enabled() {
        val navController = Mockito.mock(NavController::class.java)

        launchFragmentInHiltContainer<LoginFragment> {
            Navigation.setViewNavController(requireView(), navController)
        }

        onView(
            allOf(
                withId(R.id.text_input_end_icon),
                allOf(isDescendantOfA(withId(R.id.passwordField)))
            )
        )
            .perform(click())

        onView(isRoot()).perform(waitFor(1000))
        onView(
            allOf(
                withId(R.id.editTextLayout),
                allOf(isDescendantOfA(withId(R.id.passwordField)))
            )
        )
            .check(matches(isPasswordVisibilityToggle(true)))
    }

    @Test
    fun passwordToggle_not_enabled() {
        val navController = Mockito.mock(NavController::class.java)

        launchFragmentInHiltContainer<LoginFragment> {
            Navigation.setViewNavController(requireView(), navController)
        }

        onView(isRoot()).perform(waitFor(1000))
        onView(
            allOf(
                withId(R.id.editTextLayout),
                allOf(isDescendantOfA(withId(R.id.passwordField)))
            )
        )
            .check(matches(isPasswordVisibilityToggle(false)))
    }

    @Test
    fun passwordClear_login_disabled() {
        val navController = Mockito.mock(NavController::class.java)

        launchFragmentInHiltContainer<LoginFragment> {
            Navigation.setViewNavController(requireView(), navController)
        }

        onView(allOf(withId(R.id.editText), allOf(isDescendantOfA(withId(R.id.passwordField)))))
            .perform(clearText())
            .perform(closeSoftKeyboard())

        onView(isRoot()).perform(waitFor(1000))
        onView(withId(R.id.login)).check(matches(not(isEnabled())))
    }

    @Test
    fun login() = coTest {

        //initialize workmanager as this test will be launching MainActivity
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        val config = Configuration.Builder()
            .setMinimumLoggingLevel(Log.DEBUG)
            .setExecutor(SynchronousExecutor())
            .build()
        WorkManagerTestInitHelper.initializeTestWorkManager(context, config)

        Intents.init()

        launchFragmentInHiltContainer<LoginFragment> {
            val spy = Mockito.spy(loginViewModel)
            Mockito.doReturn(MutableStateFlow(Utils.USER_STATE)).`when`(spy).login()
            (this.delegate as BaseLifecycleDelegateImp<LoginViewModel, FragmentCB<LoginViewModel>>).viewModel = spy
        }

        loginViewModel.currentUser().login(FAKE_USER)

        onView(isRoot()).perform(waitFor(1000))
        onView(withId(R.id.login)).perform(click())

        IntentSubject.assertThat(Iterables.getLast(Intents.getIntents())).hasComponentClass(
            MainActivity::class.java
        )

        Intents.release()

    }
}