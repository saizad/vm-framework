package com.vm.frameworkexample

import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.navigation.NavController
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.vm.frameworkexample.models.ReqResUser
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import java.util.*

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {

    @Test
    fun userLoggedIn() {

    }

    @Test
    fun useAppContext() {
        // Context of the app under test.
        val user = Mockito.mock(NavController::class.java)

//        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
//        assertEquals("com.vm.frameworkexample", appContext.packageName)
    }
}
