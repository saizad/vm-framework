package com.vm.frameworkexample.di

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SampleInject @Inject constructor() {

    private val time = System.currentTimeMillis()

    fun toastMessage() : String{
        return time.toString()
    }
}
