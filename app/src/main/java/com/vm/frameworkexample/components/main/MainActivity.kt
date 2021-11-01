package com.vm.frameworkexample.components.main

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.vm.framework.utils.lifecycleScopeOnMain
import com.vm.framework.utils.startActivityClear
import com.vm.frameworkexample.R
import com.vm.frameworkexample.components.VmFrameworkExampleActivity
import com.vm.frameworkexample.components.auth.AuthActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class MainActivity : VmFrameworkExampleActivity<MainActivityViewModel>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_drawer)
    }

    override val viewModelClassType: Class<MainActivityViewModel>
        get() = MainActivityViewModel::class.java


    override fun navController(): NavController {
        return findNavController(R.id.main_nav)
    }

    override fun onViewReady() {
        super.onViewReady()
        lifecycleScopeOnMain {
            viewModel().currentUserType
                .loggedOutUser()
                .collect {
                    startActivityClear<AuthActivity>()
                }
        }
    }
}