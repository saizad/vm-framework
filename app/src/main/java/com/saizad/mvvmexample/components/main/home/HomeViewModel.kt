package com.saizad.mvvmexample.components.main.home

import com.saizad.mvvmexample.components.main.MVVMExampleMainViewModel
import com.saizad.mvvmexample.di.main.MainEnvironment
import javax.inject.Inject

class HomeViewModel @Inject constructor(mainEnvironment: MainEnvironment) :
    MVVMExampleMainViewModel(mainEnvironment)