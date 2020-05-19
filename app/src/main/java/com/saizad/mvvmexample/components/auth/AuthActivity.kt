package com.saizad.mvvmexample.components.auth

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.saizad.mvvmexample.R
import com.saizad.mvvmexample.components.MVVMExampleActivity

class AuthActivity : MVVMExampleActivity<AuthActivityViewModel>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AuthTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)
    }

    override fun getViewModelClassType(): Class<AuthActivityViewModel> {
        return AuthActivityViewModel::class.java
    }

    override fun navController(): NavController {
        return findNavController(R.id.auth_nav)
    }
}