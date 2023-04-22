package com.example.learnservice

import android.app.job.JobParameters
import android.app.job.JobService
import android.content.Context
import android.content.Intent
import android.os.Build
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

    override fun onStartJob(jobParameters: JobParameters?): Boolean {
        Log.d(TAG, "OnStartJob")
        coroutine.launch {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                var dequeuedWork = jobParameters?.dequeueWork()
                while (dequeuedWork != null) {
                    val page = dequeuedWork.intent.extras?.getInt(PAGE) ?: 0
                    for (p in 0..page) {
                        for (i in 0..5) {
                            delay(1000)
                            Log.d(TAG, "$page $i")
                        }
                    }
                    jobParameters?.completeWork(dequeuedWork)
                    dequeuedWork = jobParameters?.dequeueWork()
                }
                jobFinished(jobParameters, false)
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
        fun newIntent(context: Context, page: Int): Intent {
            return Intent(context, MyJobService::class.java).apply {
                putExtra(PAGE, page)
            }
        }

        private const val TAG = "MyJobService"
        const val JOB_ID = 121
        private const val PAGE = "page"
    }
}