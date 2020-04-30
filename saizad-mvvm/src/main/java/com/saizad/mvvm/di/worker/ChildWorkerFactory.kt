package com.saizad.mvvm.di.worker

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerParameters

interface ChildWorkerFactory {
    fun create(arg0: Context, arg1: WorkerParameters): ListenableWorker
}