package com.vm.frameworkexample.components.auth.splash

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.vm.framework.utils.startActivityClear
import com.vm.frameworkexample.R
import com.vm.frameworkexample.components.auth.AuthFragment
import com.vm.frameworkexample.components.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashFragment : AuthFragment<SplashViewModel>() {

    override val viewModelClassType: Class<SplashViewModel>
        get() = SplashViewModel::class.java

    override fun onViewCreated(view: View, savedInstanceState: Bundle?, recycled: Boolean) {
        currentUserType.isLoggedIn
            .observe(viewLifecycleOwner, {
                if (!it) {
                    findNavController().navigate(SplashFragmentDirections.actionSplashFragmentToLoginFragment())
                } else {
                    context().startActivityClear<MainActivity>()
                }
            })
    }

    override fun layoutRes(): Int {
        return R.layout.fragment_splash
    }
}
