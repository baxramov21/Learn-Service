package com.example.learnservice

import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MyService : Service() {

    override fun onCreate() {
        super.onCreate()
        Log.d("SERVICE_Of_NAZAR", "OnCreate")
    }

    private val coroutine = CoroutineScope(Dispatchers.Main)


    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        coroutine.launch {
            for (i in 0.. 100) {
                delay(1000)
                Log.d("SERVICE_Of_NAZAR", i.toString())
            }
        }
        return  START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("SERVICE_Of_NAZAR", "OnDestroy")
    }

    override fun onBind(p0: Intent?): IBinder? {
        TODO("Not yet implemented")
    }

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, MyService::class.java)
        }
    }
}