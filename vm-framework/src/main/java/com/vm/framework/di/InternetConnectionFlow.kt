package com.vm.framework.di

import com.github.pwittchen.reactivenetwork.library.rx2.ReactiveNetwork
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filterNotNull
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class InternetConnectionFlow @Inject constructor(){

    private val _flow = MutableStateFlow<Boolean?>(null)
    val flow: Flow<Boolean>
        get() = _flow.filterNotNull()

    init {
        ReactiveNetwork
            .observeInternetConnectivity()
            .subscribeOn(Schedulers.io())
            .subscribe {
                _flow.value = it
            }
    }
}