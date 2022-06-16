package com.vm.framework.components

import androidx.annotation.CallSuper
import androidx.lifecycle.ViewModel
import com.sa.easyandroidform.ObjectUtils
import com.vm.framework.ActivityResult
import com.vm.framework.Environment
import com.vm.framework.VmFrameworkNetworkRequest
import com.vm.framework.model.BaseApiError
import io.reactivex.disposables.CompositeDisposable
import java.net.ConnectException
import java.util.concurrent.TimeoutException
import javax.inject.Inject

abstract class VmFrameworkBaseViewModel(
    val environment: Environment,
) : ViewModel() {


    protected var disposable = CompositeDisposable()

    @Inject
    lateinit var networkRequest: VmFrameworkNetworkRequest

    @CallSuper
    override fun onCleared() {
        //Todo temp fix can't locate bug
        if (ObjectUtils.isNotNull(disposable)) {
            disposable.dispose()
        }
        super.onCleared()
    }

    fun navigationFragmentResult(activityResult: ActivityResult<*>) {
        environment.activityResultFlow.value = activityResult
    }
}