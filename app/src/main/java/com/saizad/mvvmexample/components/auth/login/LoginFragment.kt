package com.saizad.mvvmexample.components.auth.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.saizad.mvvm.utils.bindClick
import com.saizad.mvvmexample.R
import com.saizad.mvvmexample.components.auth.MVVMExampleAuthFragment
import com.saizad.mvvmexample.components.main.MVVMExampleMainActivity
import io.reactivex.functions.Consumer
import kotlinx.android.synthetic.main.fragment_login.*

class LoginFragment : MVVMExampleAuthFragment<LoginViewModel>() {

    override fun getViewModelClassType(): Class<LoginViewModel> {
        return LoginViewModel::class.java
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?, recycled: Boolean) {

        goToMain.bindClick(Consumer {
            startActivity(Intent(context(), MVVMExampleMainActivity::class.java))
        }, Consumer {
            showLongToast(it.message)
        })
    }

    override fun layoutRes(): Int {
        return R.layout.fragment_login
    }
}
