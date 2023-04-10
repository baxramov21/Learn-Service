package com.example.learnservice

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MyForegroundService : Service() {

    private lateinit var notification: Notification

    override fun onCreate() {
        super.onCreate()
        showNotification()
        startForeground(NOTIFICATION_ID,notification)
        Log.d("MyForegroundService", "OnCreate")
    }

    private fun showNotification() {
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager.createNotificationChannel(notificationChannel)
        }

        notification = NotificationCompat.Builder(
            applicationContext,
            CHANNEL_ID
        ).setContentTitle(NOTIFICATION_TITLE)
            .setContentText(NOTIFICATION_CONTENT)
            .setSmallIcon(R.drawable.tommy)
            .build()

        notificationManager.notify(NOTIFICATION_ID,notification)
    }

    private val coroutine = CoroutineScope(Dispatchers.Main)

    override fun onBind(intent: Intent): IBinder {
        TODO("Return the communication channel to the service.")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        coroutine.launch {
            for (i in 0..10) {
                delay(1000)
                stopSelf()
                Log.d("MyForegroundService", i.toString())
            }
        }
        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("MyForegroundService", "OnDestroy")
    }

    companion object {
        private const val CHANNEL_ID = "timer_channel_id"
        private const val CHANNEL_NAME = "Timer Channel"
        private const val NOTIFICATION_TITLE = "Timer"
        private const val NOTIFICATION_CONTENT = "Timer is running in the background"
        private const val NOTIFICATION_ID = 21
        fun newIntent(context: Context): Intent {
            return Intent(context, MyForegroundService::class.java)
        }
    }
}