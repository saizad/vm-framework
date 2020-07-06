package com.saizad.mvvmexample.components.auth.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.saizad.mvvm.utils.throttleClick
import com.saizad.mvvmexample.R
import com.saizad.mvvmexample.components.auth.AuthFragment
import com.saizad.mvvmexample.components.main.DrawerMainActivity
import io.reactivex.functions.Consumer
import kotlinx.android.synthetic.main.fragment_login.*

class LoginFragment : AuthFragment<LoginViewModel>() {

    override fun getViewModelClassType(): Class<LoginViewModel> {
        return LoginViewModel::class.java
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?, recycled: Boolean) {

        goToMain.throttleClick(Consumer {
            startActivity(Intent(context(), DrawerMainActivity::class.java))
        }, Consumer {
            showLongToast(it.message)
        })
    }

    override fun layoutRes(): Int {
        return R.layout.fragment_login
    }
}
