package com.saizad.mvvmexample.components.main.home

import com.saizad.mvvmexample.R
import com.saizad.mvvmexample.components.main.MVVMExampleMainFragment

class HomeFragment : MVVMExampleMainFragment<HomeViewModel>() {

    override fun getViewModelClassType(): Class<HomeViewModel> {
        return HomeViewModel::class.java
    }

    override fun layoutRes(): Int {
        return R.layout.fragment_home
    }
}
