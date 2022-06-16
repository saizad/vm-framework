package com.vm.frameworkexample.components.main.users.userpage

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.asFlow
import com.vm.frameworkexample.components.main.MainViewModel
import com.vm.frameworkexample.di.main.MainEnvironment
import com.vm.frameworkexample.models.ReqResUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class UserPageHostViewModel @Inject constructor(
    mainEnvironment: MainEnvironment,
    savedStateHandle: SavedStateHandle
) : MainViewModel(mainEnvironment) {

    val users = savedStateHandle.getLiveData<Array<ReqResUser>>("users").asFlow()
        .map { it.toList() }
    private val _currentSelected = MutableStateFlow(savedStateHandle.get<ReqResUser>("user"))

    val currentSelected: Flow<ReqResUser?> get() = _currentSelected

    fun setCurrentUser(currentUser: ReqResUser) {
        _currentSelected.value = currentUser
    }

}