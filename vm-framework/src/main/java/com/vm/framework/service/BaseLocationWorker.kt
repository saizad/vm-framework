package com.vm.framework.service

import android.content.Context
import android.location.Location
import android.os.Looper
import androidx.annotation.WorkerThread
import androidx.work.Data
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.vm.framework.VmFrameworkLocation
import sa.zad.easypermission.AppPermission

abstract class BaseLocationWorker(
    context: Context,
    workerParameters: WorkerParameters,
    protected val vmFrameworkLocation: VmFrameworkLocation,
    protected var locationPermission: AppPermission
) : Worker(context, workerParameters) {

    override fun doWork(): Result {

        if (!locationPermission.isPermissionGranted(applicationContext)) {
            return success(false)
        }

        return try {
            val location = vmFrameworkLocation.getLocationObservable(
                VmFrameworkLocation
                    .Builder()
                    .fastInterval(1)
                    .interval(1)
                    .build(),
                Looper.getMainLooper()
            ).blockingFirst()

            locationDoWork(location)
        } catch (e: Exception) {
            success(false)
        }
    }

    @WorkerThread
    protected abstract fun locationDoWork(location: Location): Result

    companion object {
        const val WORK_COMPLETED = "work_completed"
        protected fun success(success: Boolean): Result {
            return Result.success(
                Data.Builder().putBoolean(WORK_COMPLETED, success).build()
            )
        }
    }

}