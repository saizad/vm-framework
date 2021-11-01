package com.vm.frameworkexample.components.main.home

import androidx.lifecycle.Lifecycle
import androidx.navigation.NavController
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.RootMatchers.withDecorView
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.LargeTest
import com.vm.framework.delegation.BaseLifecycleDelegateImp
import com.vm.framework.delegation.fragment.FragmentCB
import com.vm.frameworkexample.*
import com.vm.frameworkexample.EspressoUtils.waitFor
import com.vm.frameworkexample.Utils.API_ERROR_STATE
import com.vm.frameworkexample.Utils.LOADING_FALSE_STATE
import com.vm.frameworkexample.Utils.VOID_STATE
import com.vm.frameworkexample.api.MainApi
import com.vm.frameworkexample.di.main.MainEnvironment
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import org.hamcrest.Matchers.not
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import javax.inject.Inject

@LargeTest
@HiltAndroidTest
@ExperimentalCoroutinesApi
class HomeFragmentTest : CoroutineTest() {

    @get:Rule(order = 0)
    var hiltRule = HiltAndroidRule(this)

    lateinit var viewModel: HomeViewModel
    lateinit var scenario: ActivityScenario<HiltTestActivity>
    lateinit var navController: NavController

    @Inject
    lateinit var authEnvironment: MainEnvironment

    @Before
    fun setUp() {
        hiltRule.inject()
        val environment = MainEnvironment(
            Mockito.mock(MainApi::class.java),
            authEnvironment.fcmToken,
            authEnvironment.activityResultBehaviorSubject,
            Mockito.mock(MVVMExampleCurrentUser::class.java),
            authEnvironment.permissionManager,
            authEnvironment.notificationBehaviorSubject,
        )

        viewModel = Mockito.spy(HomeViewModel(environment))
        navController = Mockito.mock(NavController::class.java)

        scenario =
            launchFragmentInHiltContainer<HomeFragment>(initialState = Lifecycle.State.CREATED) {
                val baseLifecycleDelegateImp =
                    this.delegate as BaseLifecycleDelegateImp<HomeViewModel, FragmentCB<HomeViewModel>>
                baseLifecycleDelegateImp.viewModel = viewModel
            }
    }

    @Test
    fun noContentRequest_toast_shown() {
        scenario.moveFragmentState(Lifecycle.State.RESUMED)
        onView(isRoot()).perform(waitFor(1000))
        Mockito.doReturn(MutableStateFlow(VOID_STATE)).`when`(viewModel)
            .noContentResponse(ApiRequestCodes.DELETE_USER)
        onView(withId(R.id.noContentRequest)).perform(click())

        onView(withText("No Content")).inRoot(ToastMatcher())
            .check(matches(isDisplayed()))

        onView(isRoot()).perform(waitFor(1000))
    }

    @Test
    fun noContentRequest_toast_not_shown() {
        scenario.moveFragmentState(Lifecycle.State.RESUMED)
        onView(withText("No Content")).inRoot(ToastMatcher())
            .check(matches(not(isDisplayed())))

        onView(isRoot()).perform(waitFor(1000))
        Mockito.doReturn(MutableStateFlow(Utils.ERROR_STATE)).`when`(viewModel)
            .noContentResponse(ApiRequestCodes.DELETE_USER)
        onView(withId(R.id.noContentRequest)).perform(click())
        onView(isRoot()).perform(waitFor(1000))
    }

    @Test
    fun resNotFoundErrorResponse() {
        scenario.moveFragmentState(Lifecycle.State.RESUMED)
        onView(isRoot()).perform(waitFor(1000))
        Mockito.doReturn(MutableStateFlow(API_ERROR_STATE)).`when`(viewModel)
            .resourceNotFound(ApiRequestCodes.RESOURCE_NOT_FOUND)
        onView(withId(R.id.resNotFoundErrorResponse)).perform(click())

        onView(withText(API_ERROR_STATE.apiErrorException.errorModel.message())).inRoot(ToastMatcher())
            .check(matches(isDisplayed()))

        onView(isRoot()).perform(waitFor(1000))
    }
}