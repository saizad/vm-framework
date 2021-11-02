package com.vm.frameworkexample

import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class SettingsActivityTest {

  @get:Rule
  var hiltRule = HiltAndroidRule(this)


  @Before
  fun init() {
    hiltRule.inject()
  }

  @Test
  fun `happy path`() {
  }
}