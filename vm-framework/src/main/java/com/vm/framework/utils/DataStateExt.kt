package com.vm.framework.utils

import com.vm.framework.components.VmFrameworkBaseViewModel
import com.vm.framework.enums.DataState
import com.vm.framework.model.DataModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach

fun <T> Flow<DataState<T>>.failOrSuccess(): Flow<DataState<T>> {
    return filter {
        it !is DataState.Loading
    }
}

fun <R> Flow<DataState<R>>.stateToData(): Flow<R> {
    return filter { it is DataState.Success<R> }
        .map { (it as DataState.Success<R>).data!! }
}

fun <R> Flow<DataState<R>>.isSuccess(): Flow<DataState<R>> {
    return filter { it is DataState.Success }
}

fun <R> Flow<DataState<R>>.noContentStateToData(): Flow<R?> {
    return filter { it is DataState.Success<R> }
        .map { (it as DataState.Success<R>).data }
}


fun <T> Flow<DataState<T>>.successState(successState: (T?) -> Unit): Flow<DataState<T>> {
    return onEach {
        if (it is DataState.Success) {
            successState(it.data)
        }
    }
}

fun <T> Flow<DataState<T>>.apiErrorState(apiErrorState: (VmFrameworkBaseViewModel.ApiErrorException) -> Unit): Flow<DataState<T>> {
    return onEach {
        if (it is DataState.ApiError) {
            apiErrorState(it.apiErrorException)
        }
    }
}

fun <T> Flow<DataState<T>>.errorState(errorState: (Throwable) -> Unit): Flow<DataState<T>> {
    return onEach {
        if (it is DataState.Error) {
            errorState(it.throwable)
        }
    }
}

fun <T> Flow<DataState<T>>.onError(errorState: () -> Unit): Flow<DataState<T>> {
    return onEach {
        if (it is DataState.Error || it is DataState.ApiError) {
            errorState()
        }
    }
}

fun <T> Flow<DataState<T>>.loadingState(errorState: (Boolean) -> Unit): Flow<DataState<T>> {
    return onEach {
        if (it is DataState.Loading) {
            errorState(it.isLoading)
        }
    }
}

fun <T, R> Flow<DataState<T>>.removeDataModel(successState: (T) -> R): Flow<DataState<R>> {
    return map {
        when (it) {
            is DataState.Loading -> {
                DataState.Loading(it.isLoading)
            }
            is DataState.ApiError -> {
                DataState.ApiError(it.apiErrorException)
            }
            is DataState.Error -> {
                DataState.Error(it.throwable)
            }
            is DataState.Success -> {
                DataState.Success(successState(it.data!!))
            }
        }
    }
}

fun <T> Flow<DataState<DataModel<T>>>.removeDataModel(): Flow<DataState<T>> {
    return map {
        when (it) {
            is DataState.Loading -> {
                DataState.Loading(it.isLoading)
            }
            is DataState.ApiError -> {
                DataState.ApiError(it.apiErrorException)
            }
            is DataState.Error -> {
                DataState.Error(it.throwable)
            }
            is DataState.Success -> {
                DataState.Success(it.data?.data)
            }
        }
    }
}