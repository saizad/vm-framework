package com.vm.frameworkexample.components.auth.login

import android.icu.util.Currency
import android.os.Build
import android.os.Bundle
import android.view.View
import com.vm.framework.utils.*
import com.vm.frameworkexample.R
import com.vm.frameworkexample.components.auth.AuthFragment
import com.vm.frameworkexample.components.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.coroutines.flow.collect
import java.util.*

@AndroidEntryPoint
class LoginFragment : AuthFragment<LoginViewModel>() {

    override val viewModelClassType: Class<LoginViewModel>
        get() = LoginViewModel::class.java

    override fun onViewCreated(view: View, savedInstanceState: Bundle?, recycled: Boolean) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            val locale = Locale("en", "NG")
            val currency = Currency.getInstance("NGN")
            val symbol = currency.getSymbol(locale)
        }

        login.throttleClick {
            lifecycleScopeOnMain {
                val viewModel = viewModel()
                viewModel.login()
                    .stateToData()
                    .collect {
                        context().startActivityClear<MainActivity>()
                    }
            }
        }

        viewModel().loginFormLiveData.observe(viewLifecycleOwner, { emailLoginForm ->
            emailField.setField(emailLoginForm.emailField)
            passwordField.setField(emailLoginForm.passwordField)
            emailLoginForm.validObservable()
                .subscribe {
                    login.isEnabled = it
                }
        })
    }



    override fun layoutRes(): Int {
        return R.layout.fragment_login
    }

    override fun persistView(): Boolean {
        return false
    }
}
