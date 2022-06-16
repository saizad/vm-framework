package com.vm.framework

import com.vm.framework.enums.DataState
import com.vm.framework.model.DataModel
import com.vm.framework.model.ErrorModel
import com.vm.framework.utils.noContentStateToData
import com.vm.framework.utils.dataModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import sa.zad.easyretrofit.CachePolicy
import sa.zad.easyretrofit.observables.NeverErrorObservable

abstract class BaseRepo<I : IBaseSource> constructor(
    val environment: Environment
) {
    
    val currentUser = environment.currentUser

    abstract fun resolveCachePolicy(requestId: Int, payload: Any? = null): Int
    abstract fun remoteSource(): I
    abstract fun localSource(): I

    open fun repoSource(requestId: Int, payload: Any? = null): I {
        return when (resolveCachePolicy(requestId, payload)) {
            CachePolicy.LOCAL_ONLY -> {
                localSource()
            }
            else -> {
                remoteSource()
            }
        }
    }

    open class BaseRemote(private val request: VmFrameworkNetworkRequest, environment: Environment) : BaseSource(environment) {

        fun <M> makeRemoteRequestFlow(
            observable: NeverErrorObservable<M>,
            requestId: Int
        ): Flow<DataState<M>> {
            return request.flowData(observable, requestId, ErrorModel::class.java)
        }

        suspend fun <M> makeRemoteRequest(
            observable: NeverErrorObservable<DataModel<M>>,
            requestId: Int
        ): M? {
            return makeRemoteRequestFlow(observable, requestId)
                .dataModel()
                .map { it.data }
                .firstOrNull()
        }

        suspend fun makeRemoteNoContentRequest(
            observable: NeverErrorObservable<Void>,
            requestId: Int
        ): Void? {
            return makeRemoteRequestFlow(observable, requestId)
                .noContentStateToData()
                .firstOrNull()
        }
    }

    open class BaseLocal(environment: Environment) : BaseSource(environment)

    open class BaseSource(val environment: Environment) {


        val currentUser = environment.currentUser


        suspend fun user(): Any? {
            environment.currentUser
            return currentUser.loggedInUser().first()
        }
    }
}