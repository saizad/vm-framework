package com.vm.frameworkexample

import com.vm.framework.enums.DataState
import com.vm.framework.model.DataModel
import com.vm.frameworkexample.Utils.API_ERROR_STATE
import com.vm.frameworkexample.Utils.ERROR_STATE
import com.vm.frameworkexample.Utils.FAKE_USER
import com.vm.frameworkexample.Utils.LOADING_FALSE_STATE
import com.vm.frameworkexample.Utils.LOADING_TRUE_STATE
import com.vm.frameworkexample.Utils.USER_STATE
import com.vm.frameworkexample.api.AuthApi
import com.vm.frameworkexample.components.auth.login.LoginViewModel
import com.vm.frameworkexample.di.auth.AuthEnvironment
import com.vm.frameworkexample.models.LoginResponse
import com.vm.frameworkexample.models.ReqResUser
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import org.junit.Assert
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.*

@ExperimentalCoroutinesApi
class LoginViewModelUnitTest: CoroutineTest() {

    lateinit var loginViewModel: LoginViewModel

    @Before
    fun setUp() {
        val environment = mock(AuthEnvironment::class.java)
        `when`(environment.api).thenReturn(mock(AuthApi::class.java))
        `when`(environment.currentUser).thenReturn(mock(MVVMExampleCurrentUser::class.java))
        loginViewModel = LoginViewModel(environment)
    }

    @Test
    fun emitForm() = coTest {
        val form = loginViewModel.loginFormLiveData.getOrAwaitValue()
        assertNotNull(form)
    }

    @Test
    fun `login loading True`() = coTest {
        login(LOADING_TRUE_STATE)
    }

    @Test
    fun `login loading False`() {
        login(LOADING_FALSE_STATE)
    }

    @Test
    fun `login ApiError`() {
        login(API_ERROR_STATE)
    }

    @Test
    fun `login Error`() {
        login(ERROR_STATE)
    }

    @Test
    fun `login success`() = coTest {
        val spy = spy(loginViewModel)
        val dataState = DataState.Success(LoginResponse("df"))
        doReturn(MutableStateFlow(dataState)).`when`(spy).loginApi()
        doReturn(MutableStateFlow(USER_STATE)).`when`(spy).fetchUser()
        Assert.assertEquals(USER_STATE, spy.login().first())
    }

    @Test
    fun login1() = coTest {
        val spy = Mockito.spy(loginViewModel)
        Mockito.doReturn(MutableStateFlow(Utils.USER_STATE)).`when`(spy).login()
        Assert.assertEquals(Utils.USER_STATE, spy.login().first())
    }

    @Test
    fun `fetch user called 1 time`() = coTest {
        val spy = spy(loginViewModel)
        val dataState = DataState.Success(LoginResponse("df"))
        doReturn(MutableStateFlow(dataState)).`when`(spy).loginApi()
        doReturn(MutableStateFlow(USER_STATE)).`when`(spy).fetchUser()
        spy.login().first()
        verify(spy, times(1)).fetchUser()
    }

    @Test
    fun `userApi loading true`() = coTest {
        userFetch(LOADING_TRUE_STATE)
    }

    @Test
    fun `userApi loading false`() = coTest {
        userFetch(LOADING_FALSE_STATE)
    }

    @Test
    fun `userApi api error`() = coTest {
        userFetch(API_ERROR_STATE)
    }

    @Test
    fun `userApi error`() = coTest {
        userFetch(ERROR_STATE)
    }

    @Test
    fun `userApi success`() = coTest {
        userFetch(USER_STATE)
    }

    @Test
    fun `userApi login user called 1 time`() = coTest {
        val spy = spy(loginViewModel)
        doReturn(MutableStateFlow(USER_STATE)).`when`(spy).userApi()
        spy.fetchUser().first()
        verify((spy.environment.currentUser as MVVMExampleCurrentUser), times(1)).login(FAKE_USER)
    }

    private fun login(dataState: DataState<LoginResponse>) = coTest {
        val spy = spy(loginViewModel)
        doReturn(MutableStateFlow(dataState)).`when`(spy).loginApi()
        Assert.assertEquals(dataState, spy.login().first())
    }

    private fun userFetch(dataState: DataState<DataModel<ReqResUser>>) = coTest {
        val spy = spy(loginViewModel)
        doReturn(MutableStateFlow(dataState)).`when`(spy).userApi()
        Assert.assertEquals(dataState, spy.fetchUser().first())
    }
}