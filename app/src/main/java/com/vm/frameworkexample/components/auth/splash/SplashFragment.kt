package com.vm.frameworkexample.components.auth.splash

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.vm.framework.enums.DataState
import com.vm.framework.utils.startActivityClear
import com.vm.frameworkexample.R
import com.vm.frameworkexample.components.auth.AuthFragment
import com.vm.frameworkexample.components.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
open class SplashFragment : AuthFragment<SplashViewModel>() {

    override val viewModelClassType: Class<SplashViewModel>
        get() = SplashViewModel::class.java

    override fun onViewCreated(view: View, savedInstanceState: Bundle?, recycled: Boolean) {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel().result.collect {
                    when(it) {
                        is DataState.Success -> {
                            if (it.data == false) {
                                findNavController().navigate(SplashFragmentDirections.actionSplashFragmentToLoginFragment())
                            } else {
                                userIsLoggedIn()
                            }
                        }
                    }
                }
            }
        }
    }

    fun userIsLoggedIn(){
        context().startActivityClear<MainActivity>()
    }

    override fun layoutRes(): Int {
        return R.layout.fragment_splash
    }
}
