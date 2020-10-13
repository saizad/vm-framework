package com.saizad.mvvmexample.components.auth.splash

import android.os.Bundle
import android.view.View
import com.saizad.mvvmexample.R
import com.saizad.mvvmexample.components.auth.AuthFragment
import io.reactivex.Observable
import kotlinx.android.synthetic.main.fragment_splash.*
import java.util.concurrent.TimeUnit
import androidx.navigation.fragment.findNavController

class SplashFragment : AuthFragment<SplashViewModel>() {

    override fun getViewModelClassType(): Class<SplashViewModel> {
        return SplashViewModel::class.java
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?, recycled: Boolean) {
        Observable.just("")
            .subscribeOn(schedulerProviderIO)
            .delay(100, TimeUnit.MILLISECONDS)
            .observeOn(schedulerProviderUI)
            .subscribe {
                findNavController().navigate(SplashFragmentDirections.actionSplashFragmentToLoginFragment())
            }
    }

    override fun layoutRes(): Int {
        return R.layout.fragment_splash
    }
}
