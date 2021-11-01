package com.vm.frameworkexample.components.auth.splash

import android.util.Log
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.truth.content.IntentSubject
import androidx.test.filters.MediumTest
import androidx.test.platform.app.InstrumentationRegistry
import androidx.work.Configuration
import androidx.work.impl.utils.SynchronousExecutor
import androidx.work.testing.WorkManagerTestInitHelper
import com.google.common.collect.Iterables
import com.vm.frameworkexample.MVVMExampleCurrentUser
import com.vm.frameworkexample.R
import com.vm.frameworkexample.Utils.FAKE_USER
import com.vm.frameworkexample.components.main.MainActivity
import com.vm.frameworkexample.di.auth.AuthEnvironment
import com.vm.frameworkexample.launchFragmentInHiltContainer
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runBlockingTest
import org.junit.*
import org.mockito.Mockito.*
import javax.inject.Inject


@MediumTest
@HiltAndroidTest
@ExperimentalCoroutinesApi
class SplashFragmentTest {

    @get:Rule(order = 0)
    var hiltRule = HiltAndroidRule(this)

    lateinit var viewModel: SplashViewModel

    @Inject
    lateinit var authEnvironment: AuthEnvironment

    @Before
    fun setUp() {
        hiltRule.inject()
        viewModel = SplashViewModel(authEnvironment)
    }

    @Test
    fun userNotLoggedIn() {
        val navController = mock(NavController::class.java)

        runBlockingTest {
            val job = launch {
                (authEnvironment.currentUser as MVVMExampleCurrentUser).logout {  }
            }
            job.cancel()
        }

        launchFragmentInHiltContainer<SplashFragment> {
            Navigation.setViewNavController(requireView(), navController)
        }

        Espresso.onView(withId(R.id.tv)).perform(ViewActions.click())

        verify(navController).navigate (
            SplashFragmentDirections.actionSplashFragmentToLoginFragment()
        )
    }

    @Test
    fun userLoggedIn() {

        val context = InstrumentationRegistry.getInstrumentation().targetContext
        val config = Configuration.Builder()
            .setMinimumLoggingLevel(Log.DEBUG)
            .setExecutor(SynchronousExecutor())
            .build()
        WorkManagerTestInitHelper.initializeTestWorkManager(context, config)

        Intents.init()

        runBlockingTest {
            val job = launch {
                (authEnvironment.currentUser as MVVMExampleCurrentUser).login(FAKE_USER)
            }
            job.cancel()
        }

        launchFragmentInHiltContainer<SplashFragment> {}

        Espresso.onView(withId(R.id.tv)).perform(ViewActions.click())

        IntentSubject.assertThat(Iterables.getLast(Intents.getIntents())).hasComponentClass(
            MainActivity::class.java
        )
        Intents.release()

    }
}