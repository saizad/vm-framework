package com.vm.frameworkexample

import com.vm.framework.enums.DataState
import com.vm.frameworkexample.components.VmFrameworkExampleViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.Assert
import org.mockito.Mockito
import sa.zad.easyretrofit.observables.NeverErrorObservable

@ExperimentalCoroutinesApi
abstract class VmFrameworkExampleViewModelTest: CoroutineTest() {


    protected fun allDataState(
        dataState: DataState<*>,
        requestId: Int,
        call: suspend NeverErrorObservable<*>.() -> DataState<*>
    ) {

        flowData(Utils.LOADING_FALSE_STATE, requestId) {
            call()
        }

        flowData(Utils.LOADING_TRUE_STATE, requestId) {
            call()
        }

        flowData(Utils.ERROR_STATE, requestId) {
            call()
        }

        flowData(Utils.API_ERROR_STATE, requestId) {
            call()
        }

        flowData(dataState, requestId) {
            call()
        }
    }

    protected fun flowData(
        dataState: DataState<*>,
        requestId: Int,
        call: suspend NeverErrorObservable<*>.() -> DataState<*>
    ) = coTest {
        val neverErrorObservable = Mockito.mock(NeverErrorObservable::class.java)
        Mockito.doReturn(MutableStateFlow(dataState)).`when`(testSubjectViewModel())
            .flowData(neverErrorObservable, requestId)
        val invoke = call.invoke(neverErrorObservable as NeverErrorObservable<*>)
        Assert.assertEquals(dataState, invoke)
    }

    abstract fun testSubjectViewModel(): VmFrameworkExampleViewModel
}