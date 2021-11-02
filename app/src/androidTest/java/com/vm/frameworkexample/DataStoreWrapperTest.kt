package com.vm.frameworkexample

import com.google.gson.Gson
import com.vm.framework.DataStoreWrapper
import com.vm.frameworkexample.models.ReqResUser
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import org.junit.Assert
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class DataStoreWrapperTest: DataStoreTest() {

    private lateinit var dataStoreWrapper: DataStoreWrapper

    @Before
    fun setUp() {
        dataStoreWrapper = DataStoreWrapper(dataStore, Gson())
    }

    @Test
    fun putValue() = coTest {
        val value = "test value"
        dataStoreWrapper.putValue("prefKey", value)
        Assert.assertEquals(value, dataStoreWrapper.getValue("prefKey").first())
    }

    @Test
    fun put_value_and_remove() = coTest {
        val value = "test value"
        val prefKey = "prefKey"
        dataStoreWrapper.putValue(prefKey, value)
        dataStoreWrapper.remove(prefKey)
        Assert.assertEquals(null, dataStoreWrapper.getValue(prefKey).first())
    }

    @Test
    fun putObject() = coTest {
        val prefKey = "user"
        val reqResUser = ReqResUser(1, "John", "Doe", "JohnDoe@gmail.com", "")
        dataStoreWrapper.putObject(prefKey, reqResUser)
        Assert.assertEquals(
            reqResUser,
            dataStoreWrapper.getObject(prefKey, ReqResUser::class.java).first()
        )
    }

    @Test
    fun putObjectAndRemove() = coTest {
        val prefKey = "user"
        val reqResUser = ReqResUser(1, "John", "Doe", "JohnDoe@gmail.com", "")
        dataStoreWrapper.putObject(prefKey, reqResUser)
        dataStoreWrapper.remove(prefKey)
        Assert.assertEquals(
            null,
            dataStoreWrapper.getObject(prefKey, ReqResUser::class.java).first()
        )
    }

    @Test
    fun removeAll() = coTest {
        val objectKey = "user"
        val reqResUser = ReqResUser(1, "John", "Doe", "JohnDoe@gmail.com", "")
        dataStoreWrapper.putObject(objectKey, reqResUser)

        val value = "test value"
        val valueKey = "prefKey"
        dataStoreWrapper.putValue(valueKey, value)

        dataStoreWrapper.removeAll {  }

        Assert.assertEquals(
            null,
            dataStoreWrapper.getObject(objectKey, ReqResUser::class.java).first()
        )

        Assert.assertEquals(null, dataStoreWrapper.getValue(valueKey).first())

    }
}