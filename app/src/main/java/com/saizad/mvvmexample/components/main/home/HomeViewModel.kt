package com.saizad.mvvmexample.components.main.home

import com.saizad.mvvmexample.di.main.MainEnvironment
import com.saizad.mvvmexample.components.main.MainViewModel
import javax.inject.Inject

class HomeViewModel @Inject constructor(mainEnvironment: MainEnvironment) :
    MainViewModel(mainEnvironment)