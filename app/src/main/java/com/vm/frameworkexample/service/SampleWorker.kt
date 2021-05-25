package com.vm.frameworkexample.service

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.vm.frameworkexample.di.SampleInject
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class SampleWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted params: WorkerParameters,
    user: SampleInject
) : Worker(context, params) {

    init {
        Log.d("dadfasdf", "$user")
    }
    override fun doWork(): Result {
        return Result.success()
    }

}