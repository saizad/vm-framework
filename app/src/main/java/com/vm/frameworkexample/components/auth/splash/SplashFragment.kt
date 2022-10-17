package com.vm.frameworkexample.components.auth.splash

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.vm.framework.utils.lifecycleScopeOnMain
import com.vm.framework.utils.startActivityClear
import com.vm.framework.utils.stateToDataRemoveDataModel
import com.vm.frameworkexample.R
import com.vm.frameworkexample.components.auth.AuthFragment
import com.vm.frameworkexample.components.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect


@AndroidEntryPoint
open class SplashFragment : AuthFragment<SplashViewModel>() {

    override val viewModelClassType: Class<SplashViewModel>
        get() = SplashViewModel::class.java

    override fun onViewCreated(view: View, savedInstanceState: Bundle?, recycled: Boolean) {
        lifecycleScopeOnMain {
            viewModel().result
                .stateToDataRemoveDataModel()
                .collect {
                    if (!it) {
                        findNavController().navigate(SplashFragmentDirections.actionSplashFragmentToLoginFragment())
                    } else {
                        context().startActivityClear<MainActivity>()
                    }
                }
        }
    }

    override fun layoutRes(): Int {
        return R.layout.fragment_splash
    }

}
