package com.vm.frameworkexample.components.auth

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.vm.framework.VmFrameworkNetworkRequest
import com.vm.frameworkexample.R
import com.vm.frameworkexample.components.VmFrameworkExampleActivity
import com.vm.frameworkexample.di.auth.AuthEnvironment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AuthActivity : VmFrameworkExampleActivity<AuthActivityViewModel>() {

    @Inject
    lateinit var authEnvironment: AuthEnvironment

    override val networkRequest: VmFrameworkNetworkRequest by lazy {
        authEnvironment.networkRequest
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AuthTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)
    }

    override val viewModelClassType: Class<AuthActivityViewModel>
        get() = AuthActivityViewModel::class.java


    override fun navController(): NavController {
        return findNavController(R.id.auth_nav)
    }
}