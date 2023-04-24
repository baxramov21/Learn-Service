package com.example.learnservice

import android.content.Context
import android.util.Log
import androidx.work.*

class MyWorkManager(
    context: Context,
    private val workerParameters: WorkerParameters
) : Worker(context, workerParameters) {

    override fun doWork(): Result {
        log("doWork")
        val page = workerParameters.inputData.getInt(PAGE, 0)
        for (i in 0..5) {
            Thread.sleep(1000)
            log("$page $i")
        }
        return Result.success()
    }

    private fun log(msg: String) {
        Log.d(TAG, msg)
    }

    companion object {
        private const val TAG = "MyWorkManager"
        private const val PAGE = "page"
        const val WORK_NAME = "BaxramovWork"

        fun makeWorkRequest(page: Int): OneTimeWorkRequest {
            return OneTimeWorkRequestBuilder<MyWorkManager>().apply {
                setInputData(workDataOf(PAGE to page))
                    .setConstraints(setConstraints())
            }.build()
        }

        private fun setConstraints() =
            Constraints.Builder().setRequiresCharging(true)
                .setRequiredNetworkType(NetworkType.UNMETERED)
                .build()
    }

}