package com.saizad.mvvm.components

import android.os.Bundle
import androidx.navigation.findNavController
import com.saizad.mvvm.R

abstract class AuthActivity<VM : SaizadBaseViewModel> : SaizadBaseActivity<VM>() {

    override fun onSupportNavigateUp() = this.findNavController(R.id.auth_nav).navigateUp()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)
    }

}
