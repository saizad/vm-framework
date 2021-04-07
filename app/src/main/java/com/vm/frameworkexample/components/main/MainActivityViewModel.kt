package com.vm.frameworkexample.components.main

import com.vm.frameworkexample.di.main.MainEnvironment
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    environment: MainEnvironment
) : MainViewModel(environment)