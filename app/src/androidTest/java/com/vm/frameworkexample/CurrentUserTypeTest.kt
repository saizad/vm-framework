package com.vm.frameworkexample

import com.google.gson.Gson
import com.vm.framework.DataStoreWrapper
import com.vm.frameworkexample.Utils.FAKE_USER
import com.vm.frameworkexample.models.ReqResUser
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
@ExperimentalCoroutinesApi
class CurrentUserTypeTest : DataStoreTest() {

    private lateinit var dataStoreWrapper: DataStoreWrapper
    private lateinit var currentUser: MVVMExampleCurrentUser

    @get:Rule(order = 0)
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var gson: Gson

    @Before
    fun setUp() {
        hiltRule.inject()
        dataStoreWrapper = DataStoreWrapper(dataStore, gson)
        currentUser = MVVMExampleCurrentUser(dataStoreWrapper)
    }

    @Test
    fun loggedInUser() = coTest {
        currentUser.login(FAKE_USER)
        Assert.assertEquals(FAKE_USER, currentUser.loggedInUser().first())
    }

    @Test
    fun isLoggedIn_true() = coTest {
        currentUser.login(FAKE_USER)
        Assert.assertEquals(true,  currentUser.isLoggedIn.first())
    }

    @Test
    fun isLoggedIn_false() = coTest {
        Assert.assertEquals(false, currentUser.isLoggedIn.first())
    }

    @Test
    fun loggedOutUser() = coTest {
        currentUser.login(FAKE_USER)
        launch {
            currentUser.logout { }
            cancel()
        }
        Assert.assertEquals(null, currentUser.loggedOutUser().first())
    }

    @Test
    fun refresh() = coTest {
        val newEmail = "newEmail@gmail.com"
        currentUser.login(FAKE_USER)
        val refreshedUser = ReqResUser(1, "John", "Doe", newEmail, "")
        currentUser.refresh(refreshedUser)
        Assert.assertEquals(newEmail, currentUser.loggedInUser().first().email)
    }
}