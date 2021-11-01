package com.vm.frameworkexample.components.auth.splash

import androidx.test.filters.MediumTest
import com.vm.frameworkexample.CoroutineTest
import com.vm.frameworkexample.di.auth.AuthEnvironment
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import javax.inject.Inject

@MediumTest
@HiltAndroidTest
@ExperimentalCoroutinesApi
class SplashViewModelTest: CoroutineTest() {

    @get:Rule(order = 0)
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var authEnvironment: AuthEnvironment

    lateinit var splashViewModel: SplashViewModel

    @Before
    fun setUp() {
        hiltRule.inject()
        splashViewModel = SplashViewModel(authEnvironment)
    }

}