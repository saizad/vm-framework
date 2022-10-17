package com.vm.frameworkexample.components.auth.login

import android.icu.util.Currency
import android.os.Build
import android.os.Bundle
import android.view.View
import com.vm.framework.utils.*
import com.vm.frameworkexample.R
import com.vm.frameworkexample.components.auth.AuthFragment
import com.vm.frameworkexample.components.main.MainActivity
import com.vm.frameworkexample.databinding.FragmentLoginBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import java.util.*

@AndroidEntryPoint
class LoginFragment : AuthFragment<LoginViewModel>() {

    private lateinit var binding: FragmentLoginBinding

    override val viewModelClassType: Class<LoginViewModel>
        get() = LoginViewModel::class.java

    override fun onViewCreated(view: View, savedInstanceState: Bundle?, recycled: Boolean) {
        binding = FragmentLoginBinding.bind(view)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            val locale = Locale("en", "NG")
            val currency = Currency.getInstance("NGN")
            val symbol = currency.getSymbol(locale)
        }

        binding.login.throttleClick {
            lifecycleScopeOnMain {
                val viewModel = viewModel()
                viewModel.login()
                    .loadingState {
                        vmFrameworkExampleActivity.showApiRequestLoadingDialog(it.isLoading)
                    }
                    .dataModel()
                    .collect {
                        context().startActivityClear<MainActivity>()
                    }
            }
        }

        viewModel().loginFormLiveData.observe(viewLifecycleOwner) { emailLoginForm ->
            binding.emailField.setField(emailLoginForm.emailField)
            binding.passwordField.setField(emailLoginForm.passwordField)
            emailLoginForm.validObservable()
                .subscribe {
                    binding.login.isEnabled = it
                }
        }
    }



    override fun layoutRes(): Int {
        return R.layout.fragment_login
    }

    override fun persistView(): Boolean {
        return false
    }
}
