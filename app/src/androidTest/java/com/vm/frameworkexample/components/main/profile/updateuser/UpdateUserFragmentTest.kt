package com.vm.frameworkexample.components.main.profile.updateuser

import android.os.Bundle
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.LargeTest
import com.vm.framework.components.form.fields.NameField
import com.vm.framework.components.form.ui.StringInputFieldView
import com.vm.framework.delegation.BaseCB
import com.vm.framework.delegation.BaseLifecycleDelegateImp
import com.vm.framework.delegation.fragment.FragmentAppLifecycleDelegateImp
import com.vm.framework.delegation.fragment.FragmentCB
import com.vm.frameworkexample.*
import com.vm.frameworkexample.EspressoUtils.waitFor
import com.vm.frameworkexample.Utils.FAKE_USER
import com.vm.frameworkexample.Utils.FAKE_USER_WITH_JOB
import com.vm.frameworkexample.api.MainApi
import com.vm.frameworkexample.di.main.MainEnvironment
import com.vm.frameworkexample.models.ReqResUser
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import io.reactivex.Observable
import io.reactivex.observers.TestObserver
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.firstOrNull
import org.hamcrest.Matchers.allOf
import org.hamcrest.core.Is
import org.hamcrest.core.IsNot
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.*
import javax.inject.Inject

@LargeTest
@HiltAndroidTest
@ExperimentalCoroutinesApi
class UpdateUserFragmentTest : CoroutineTest() {

    @get:Rule(order = 0)
    var hiltRule = HiltAndroidRule(this)

    lateinit var viewModel: UpdateUserViewModel
    lateinit var form: ReqResUser.Form
    lateinit var scenario: ActivityScenario<HiltTestActivity>
    lateinit var navController: NavController

    @Inject
    lateinit var authEnvironment: MainEnvironment

    @Before
    fun setUp() {
        hiltRule.inject()
        val environment = MainEnvironment(
            mock(MainApi::class.java),
            authEnvironment.fcmToken,
            authEnvironment.activityResultBehaviorSubject,
            mock(MVVMExampleCurrentUser::class.java),
            authEnvironment.permissionManager,
            authEnvironment.notificationBehaviorSubject,
        )

        val savedStateHandle = SavedStateHandle().apply {
            set("user", FAKE_USER)
        }

        viewModel = spy(UpdateUserViewModel(environment, savedStateHandle))
        form = spy(ReqResUser.Form(FAKE_USER))
        navController = mock(NavController::class.java)

        scenario =
            launchFragmentInHiltContainer<UpdateUserFragment>(fragmentArgs = Bundle().apply {
                putParcelable("user", FAKE_USER)
            }, initialState = Lifecycle.State.CREATED) {
                val baseLifecycleDelegateImp =
                    this.delegate as BaseLifecycleDelegateImp<UpdateUserViewModel, FragmentCB<UpdateUserViewModel>>
                baseLifecycleDelegateImp.viewModel = viewModel
            }

        doReturn(form).`when`(viewModel).form
    }

    @Test
    fun fillJobValue() {
        scenario.moveFragmentState(Lifecycle.State.RESUMED)

        val jobFieldValue = "Banker"
        val jobFieldEditText = onView(
            allOf(withId(R.id.editText), allOf(isDescendantOfA(withId(R.id.jobField))))
        )
        jobFieldEditText
            .perform(ViewActions.clearText())
            .perform(ViewActions.typeText(jobFieldValue))
            .perform(ViewActions.closeSoftKeyboard())

        jobFieldEditText.check(matches(withText(jobFieldValue)))

        onView(isRoot()).perform(waitFor(1000))
    }

    @Test
    fun validFormButtonEnabled() {
        doReturn(Observable.just(true)).`when`(form).validObservable()
        scenario.moveFragmentState(Lifecycle.State.RESUMED)
        onView(isRoot()).perform(waitFor(1000))
        onView(withId(R.id.save)).check(matches(Is.`is`(isEnabled())))
    }

    @Test
    fun formButtonDisabled() {
        doReturn(Observable.just(false)).`when`(form).validObservable()
        scenario.moveFragmentState(Lifecycle.State.RESUMED)
        onView(isRoot()).perform(waitFor(1000))
        onView(withId(R.id.save)).check(matches(IsNot.not(isEnabled())))
    }

    @Test
    fun fullNameField_is_set() {
        scenario.moveFragmentState(Lifecycle.State.RESUMED)
        onView(withId(R.id.fullNameField)).check { view, noViewFoundException ->
            val stringInputFieldView = view as StringInputFieldView
            Assert.assertEquals(form.fullNameField, stringInputFieldView.fieldItem)
        }
    }

    @Test
    fun jobField_is_set() {
        scenario.moveFragmentState(Lifecycle.State.RESUMED)
        onView(withId(R.id.jobField)).check { view, noViewFoundException ->
            val stringInputFieldView = view as StringInputFieldView
            Assert.assertEquals(form.jobField, stringInputFieldView.fieldItem)
        }
    }

    @Test
    fun invalid_form_save_not_called() {
        scenario.moveFragmentState(Lifecycle.State.RESUMED)
        onView(isRoot()).perform(waitFor(2000))
        onView(withId(R.id.save)).perform(click())
        verify(viewModel, never()).save()
    }

    @Test
    fun invalid_form_save_called() {

        scenario.moveFragmentState(Lifecycle.State.RESUMED)
        scenario.onActivity {
            val fragment = it.supportFragmentManager.findFragmentByTag(FRAGMENT_TAG)
            Navigation.setViewNavController(fragment!!.requireView(), navController)
        }
        form.jobField.field = "Banker"
        onView(isRoot()).perform(waitFor(1000))
        doReturn(MutableStateFlow(FAKE_USER_WITH_JOB)).`when`(viewModel).save()
        onView(withId(R.id.save)).perform(click())
        verify(viewModel, times(1)).save()
    }

    @Test
    fun invalid_form_save_called_complete() {

        scenario.moveFragmentState(Lifecycle.State.RESUMED)
        scenario.onActivity {
            val fragment = it.supportFragmentManager.findFragmentByTag(FRAGMENT_TAG)
            Navigation.setViewNavController(fragment!!.requireView(), navController)
        }
        form.jobField.field = "Banker"
        onView(isRoot()).perform(waitFor(1000))
        doReturn(MutableStateFlow(FAKE_USER_WITH_JOB)).`when`(viewModel).save()
        onView(withId(R.id.save)).perform(click())
        verify(navController, times(1)).popBackStack()
    }
}