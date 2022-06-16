package com.vm.framework

import android.app.Activity

data class ActivityResult<V>(var requestCode: Int, val resultCode: Int, private val value: V?) {

    constructor(requestCode: Int, value: V?) : this(requestCode, OK(), value)

    val isCanceled: Boolean
        get() = resultCode == Activity.RESULT_CANCELED

    val isOk: Boolean
        get() = resultCode == OK()

    fun isRequestCode(requestCode: Int): Boolean {
        return this.requestCode == requestCode
    }

    fun value() : V? {
        requestCode = Activity.RESULT_CANCELED
        return value
    }

    companion object {
        fun OK(): Int {
            return Activity.RESULT_OK
        }
    }

}