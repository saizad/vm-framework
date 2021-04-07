package com.vm.frameworkexample.components.auth.blank

import com.vm.frameworkexample.components.auth.AuthFragment
import com.vm.frameworkexample.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BlankFragment : AuthFragment<BlankViewModel>() {

    override val viewModelClassType: Class<BlankViewModel>
        get() = BlankViewModel::class.java


    override fun layoutRes(): Int {
        return R.layout.fragment_blank
    }
}
