package com.saizad.mvvmexample.components.auth.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.saizad.mvvm.utils.bindClick
import com.saizad.mvvmexample.MVVMExampleApplication
import com.saizad.mvvmexample.R
import com.saizad.mvvmexample.RequestCodes
import com.saizad.mvvmexample.components.auth.AuthBaseFragment
import com.saizad.mvvmexample.components.home.AppMainActivity
import io.reactivex.functions.Consumer
import kotlinx.android.synthetic.main.fragment_login.*

class LoginFragment : AuthBaseFragment<LoginViewModel>() {

    override fun getViewModelClassType(): Class<LoginViewModel> {
        return LoginViewModel::class.java
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?, recycled: Boolean) {

        goToMain.bindClick(Consumer {
            environment().permissionManager()
                .requestPermission(this, RequestCodes.LOCATION_PERMISSION_REQUEST_CODE, RequestCodes.STORAGE_PERMISSION_REQUEST_CODE)
                .subscribe ({
                    showLongToast(it.toString())
                    MVVMExampleApplication.getInstance().runLocationWorker()
                }, {
                    showLongToast(it.message)
                })
            startActivity(Intent(context(), AppMainActivity::class.java))
        }, Consumer {
            showLongToast(it.message)
        })
    }

    override fun layoutRes(): Int {
        return R.layout.fragment_login
    }
}
