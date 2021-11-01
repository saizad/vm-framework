package com.vm.frameworkexample.components.main.home

import com.vm.framework.enums.DataState
import com.vm.frameworkexample.ApiRequestCodes.DELAYED_RESPONSE
import com.vm.frameworkexample.ApiRequestCodes.DELETE_USER
import com.vm.frameworkexample.ApiRequestCodes.RESOURCE_NOT_FOUND
import com.vm.frameworkexample.MVVMExampleCurrentUser
import com.vm.frameworkexample.VmFrameworkExampleViewModelTest
import com.vm.frameworkexample.api.MainApi
import com.vm.frameworkexample.components.VmFrameworkExampleViewModel
import com.vm.frameworkexample.di.main.MainEnvironment
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*

@ExperimentalCoroutinesApi
class HomeViewModelTest : VmFrameworkExampleViewModelTest() {

    private val environment: MainEnvironment = mock(MainEnvironment::class.java)
    private val api: MainApi = mock(MainApi::class.java)
    private val currentUser = mock(MVVMExampleCurrentUser::class.java)
    lateinit var testSubject: HomeViewModel


    @Before
    fun setUp() {
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }
        `when`(environment.api).thenReturn(api)
        `when`(environment.currentUser).thenReturn(currentUser)
        testSubject = spy(HomeViewModel(environment))
    }

    override fun testSubjectViewModel(): VmFrameworkExampleViewModel {
        return testSubject
    }

    @Test
    fun delayedResponse() {
        allDataState(DataState.Success(null), DELAYED_RESPONSE){
            doReturn(this).`when`(api).delayedResponse(1)
            testSubject.delayed(1).first()
        }
    }

    @Test
    fun noContentResponse() {
        allDataState(DataState.Success(null), DELETE_USER){
            doReturn(this).`when`(api).noContentResponse()
            testSubject.noContentResponse().first()
        }
    }

    @Test
    fun resourceNotFound() {
        allDataState(DataState.Success(null), RESOURCE_NOT_FOUND){
            doReturn(this).`when`(api).resourceNotFound()
            testSubject.resourceNotFound().first()
        }
    }

}