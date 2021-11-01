package com.vm.frameworkexample

import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.Mockito

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {

    @Test
    fun whenStubASpy_thenStubbed() {
        val list: List<String> = ArrayList()
        val spyList = Mockito.spy(list)
        assertEquals(0, spyList.size)
        Mockito.doReturn(100).`when`(spyList).size
        assertEquals(100, spyList.size)
    }
}
