package com.example.learnservice

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.JobIntentService
import androidx.core.app.JobIntentService.enqueueWork
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MyJobIntentService : JobIntentService() {

    private val coroutine = CoroutineScope(Dispatchers.Main)

    override fun onHandleWork(intent: Intent) {
        log("onHandleWork")
        val page = intent.getIntExtra(PAGE, 1)
            for (i in 0..5) {
                Thread.sleep(1000)
                log("$page $i")
            }
    }

    private fun log(msg: String) {
        Log.d(TAG, msg)
    }

    companion object {
        private const val TAG = "MyJobIntentService"
        private const val JOB_ID = 77
        private const val PAGE = "page"

        fun enqueue(context: Context, page: Int) {
            enqueueWork(
                context,
                MyJobIntentService::class.java,
                JOB_ID,
                newIntent(context, page)
            )
        }

        private fun newIntent(context: Context, page: Int): Intent {
            return Intent(context, MyJobIntentService::class.java).apply {
                putExtra(PAGE, page)
            }
        }
    }
}