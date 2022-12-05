package com.vm.framework

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

abstract class CurrentUserType<U> protected constructor(
    private val dataStoreWrapper: DataStoreWrapper
) {
    val currentUserFlow = MutableStateFlow<U?>(null)

    protected abstract val classType: Class<U>

    companion object {
        private const val KEY_USER_INFO = "user_info"
    }

    init {

        GlobalScope.launch {
            currentUserFlow
                .drop(1)
                .collect {
                    dataStoreWrapper.putObject(KEY_USER_INFO, it)
                }
        }

        GlobalScope.launch {
            val value = dataStoreWrapper.getObject(KEY_USER_INFO, classType).firstOrNull()
            currentUserFlow.emit(value)
        }

    }

    open fun login(newUser: U) {
        refresh(newUser)
    }

    open suspend fun logout(listener: () -> Unit) {
        dataStoreWrapper.removeAll {
            currentUserFlow.emit(null)
            listener.invoke()
        }
    }

    fun refresh(freshUser: U) {
        currentUserFlow.value = freshUser
    }

    val isLoggedIn: Flow<Boolean>
        get() = currentUserFlow
            .map {
                it != null
            }

    fun loggedInUser(): Flow<U> {
        return currentUserFlow
            .filter {
                it != null
            }.map { it!! }
    }

    fun loggedOutUser(): Flow<Void?> {
        return currentUserFlow
            .filter {
                it == null
            }.map { null }
    }

    abstract val token: String?

}