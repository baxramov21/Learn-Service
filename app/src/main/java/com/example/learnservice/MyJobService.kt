package com.example.learnservice

import android.app.job.JobParameters
import android.app.job.JobService
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MyJobService : JobService() {

    override fun onCreate() {
        super.onCreate()
        Log.d(TAG, "OnCreate")
    }

    private val coroutine = CoroutineScope(Dispatchers.Main)

    override fun onStartJob(p0: JobParameters?): Boolean {
        Log.d(TAG, "OnStartJob")
        coroutine.launch {
            for (i in 0..100) {
                delay(1000)
                Log.d(TAG, i.toString())
            }
        }
        return true
    }

    override fun onStopJob(p0: JobParameters?): Boolean {
        Log.d(TAG, "onStopJob")
        return true
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "OnDestroy")
    }


    companion object {
        private const val TAG = "MyJobService"
        const val JOB_ID = 121
    }
}