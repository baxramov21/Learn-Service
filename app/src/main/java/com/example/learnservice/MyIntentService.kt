package com.example.learnservice

import android.app.IntentService
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat

/**
 * This class is deprecated but it would be better to know abut it
 */

class MyIntentService : IntentService(SERVICE_NAME) {

    private lateinit var notification: Notification

    override fun onCreate() {
        super.onCreate()
        setIntentRedelivery(true)
        showNotification()
        startForeground(NOTIFICATION_ID, notification)
        Log.d(TAG, "OnCreate")
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

        notificationManager.notify(NOTIFICATION_ID, notification)
    }

    override fun onHandleIntent(p0: Intent?) {
        for (i in 0..10) {
            Thread.sleep(1000)
            Log.d(TAG, i.toString())
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "OnDestroy")
    }

    companion object {

        private const val TAG = "MyIntentService"
        private const val CHANNEL_ID = "timer_channel_id"
        private const val CHANNEL_NAME = "Timer Channel"
        private const val NOTIFICATION_TITLE = "Timer"
        private const val NOTIFICATION_CONTENT = "Timer is running in the background"
        private const val NOTIFICATION_ID = 21
        private const val SERVICE_NAME = "INTENT_SERVICE"

        fun newIntent(context: Context): Intent {
            return Intent(context, MyIntentService::class.java)
        }
    }
}