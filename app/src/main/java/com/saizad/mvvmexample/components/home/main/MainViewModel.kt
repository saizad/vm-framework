package com.saizad.mvvmexample.components.home.main

import com.saizad.mvvmexample.MainEnvironment
import com.saizad.mvvmexample.components.home.HomeViewModel
import javax.inject.Inject

class MainViewModel @Inject constructor(mainEnvironment: MainEnvironment) :
    HomeViewModel(mainEnvironment)