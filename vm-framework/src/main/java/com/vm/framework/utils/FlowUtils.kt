package com.vm.framework.utils

import android.util.Log
import android.view.View
import com.sa.easyandroidform.fields.BaseField
import com.vm.framework.ActivityResult
import com.vm.framework.enums.DataState
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.ProducerScope
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import sa.zad.easyretrofit.ResponseException
import sa.zad.pagedrecyclerlist.ListSelection

fun <T> Flow<T>.throttleFirst(periodMillis: Long): Flow<T> {
    require(periodMillis > 0) { "period should be positive" }
    return flow {
        var lastTime = 0L
        collect {
            val currentTime = System.currentTimeMillis()
            if (currentTime - lastTime >= periodMillis) {
                lastTime = currentTime
                emit(it)
            }
        }
    }
}

fun <T1, T2> Flow<T1>.combineFirst(flow: Flow<T2>): Flow<T1> {
    return combine(flow) { a: T1, b: T2 -> a }
}

fun <T1, T2> Flow<T1>.combineSecond(flow: Flow<T2>): Flow<T2> {
    return combine(flow) { a: T1, b: T2 -> b }
}

fun <T1, T2> Flow<T1>.combinePair(flow: Flow<T2>): Flow<Pair<T1, T2>> {
    return combine(flow) { a: T1, b: T2 -> a to b }
}

suspend fun <T1, T2> Flow<T1>.combineCollect(
    flow: Flow<T2>,
    transform: suspend (a: T1, b: T2) -> Unit
) {
    combine(flow) { a: T1, b: T2 -> transform.invoke(a, b) }.collect()
}

fun <T> Flow<T>.viewClickCombine(view: View): Flow<T> {
    return flatMapLatest { value -> view.flowThrottleClick().map { value } }
}

fun <T> Flow<T>.log(tag: String, message: (T) -> String): Flow<T> {
    return onEach {
        Log.d(tag, message.invoke(it))
    }
}

fun <T> Flow<T>.log(tag: String): Flow<T> {
    return log(tag) {
        it.toString()
    }
}

fun View.flowThrottleClick(duration: Long = 500): Flow<Unit> = callbackFlow {
    throttleClick(duration) {
        offer(Unit)
    }
    awaitClose { setOnClickListener(null) }
}

fun View.flowThrottleClick(): Flow<Unit> = callbackFlow {
    throttleClick {
        offer(Unit)
    }
    awaitClose { setOnClickListener(null) }
}

val <T> Observable<T>.toFlow: Flow<T>
    get() {
        val block: suspend ProducerScope<T>.() -> Unit = {
            val request =
                subscribe {
                    offer(it)
                }

            awaitClose {
                request.dispose()
            }
        }
        return callbackFlow(block)
    }


fun <M> Call<M>.toDataStateFlow(requestId: Int): Flow<DataState<M>> {
    return callbackFlow {
        trySend(DataState.Loading(true, requestId))
        enqueue(object : Callback<M> {
            override fun onResponse(call: Call<M>, response: Response<M>) {
                if (response.isSuccessful) {
                    trySend(DataState.Success(response.body(), requestId))
                } else {
                    trySend(DataState.Error(ResponseException(response), requestId))
                }
                trySend(DataState.Loading(false, requestId))
            }

            override fun onFailure(call: Call<M>, t: Throwable) {
                trySend(DataState.Error(t, requestId))
                trySend(DataState.Loading(false, requestId))
            }

        })

        awaitClose {
            cancel()
        }
    }
}

fun <M> Observable<M>.toDataStateFlow(requestId: Int): Flow<DataState<M?>> {
    return callbackFlow {
        trySend(DataState.Loading(true, requestId))
        val subscribe = subscribeOn(Schedulers.io())
            .subscribe({
                trySend(DataState.Success(it, requestId))
            }, {
                trySend(DataState.Error(it, requestId))
                trySend(DataState.Loading(false, requestId))
            }, {
                trySend(DataState.Loading(false, requestId))
            })

        awaitClose {
            trySend(DataState.Loading(false, requestId))
            subscribe.dispose()
        }
    }
}

val <T> BaseField<T>.valueFlow: Flow<T?> get() = observable().toFlow.map { field }

inline fun <reified V> Flow<ActivityResult<*>>.filterRequestCode(requestCode: Int): Flow<V> {
    return filter { it.isOk && it.isRequestCode(requestCode) }
        .map { it.value() }
        .filterIsInstance<V>()
        .filterIsInstance()
}

fun <M> ListSelection<M, *>.itemClickListenerFlow(): Flow<ItemClickFields<M>> {
    val block: suspend ProducerScope<ItemClickFields<M>>.() -> Unit = {

        setItemOnClickListener { item, view, itemIndex ->
            offer(ItemClickFields(item, view, itemIndex))
        }

        awaitClose {
            setItemOnClickListener(null)
        }
    }

    return callbackFlow(block)
}
