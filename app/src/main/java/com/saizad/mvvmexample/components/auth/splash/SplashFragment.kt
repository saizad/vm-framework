package com.saizad.mvvmexample.components.auth.splash

import android.os.Bundle
import android.view.View
import com.saizad.mvvmexample.R
import com.saizad.mvvmexample.components.auth.AuthBaseFragment
import io.reactivex.Observable
import kotlinx.android.synthetic.main.fragment_splash.*
import java.util.concurrent.TimeUnit

class SplashFragment : AuthBaseFragment<SplashViewModel>() {

    override fun getViewModelClassType(): Class<SplashViewModel> {
        return SplashViewModel::class.java
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?, recycled: Boolean) {
        Observable.just("")
            .subscribeOn(schedulerProviderIO)
            .delay(1000, TimeUnit.MILLISECONDS)
            .observeOn(schedulerProviderUI)
            .subscribe {
                openClosableFragment(R.id.loginFragment)
//                findNavController().navigate(SplashFragmentDirections.actionSplashFragmentToLoginFragment())
            }
        if(!recycled) {
            tv.text = (1..100000).random().toString()
        }
    }

    override fun layoutRes(): Int {
        return R.layout.fragment_splash
    }
}
