package com.saizad.mvvmexample.components.home.main

import com.saizad.mvvmexample.R
import com.saizad.mvvmexample.components.home.HomeFragment

class MainFragment : HomeFragment<MainViewModel>() {

    override fun getViewModelClassType(): Class<MainViewModel> {
        return MainViewModel::class.java
    }

    override fun layoutRes(): Int {
        return R.layout.fragment_main
    }
}
