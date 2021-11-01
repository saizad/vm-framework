package com.vm.frameworkexample

import com.vm.framework.DataStoreWrapper
import org.junit.Before

import org.junit.Assert.*
import org.mockito.Mockito

class MVVMExampleCurrentUserTest {

    private lateinit var mvvmExampleCurrentUser: MVVMExampleCurrentUser

    @Before
    fun setUp() {
        val dataStoreWrapper = Mockito.mock(DataStoreWrapper::class.java)

//        mvvmExampleCurrentUser = MVVMExampleCurrentUser()
    }
}