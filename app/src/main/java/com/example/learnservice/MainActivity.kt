package com.example.learnservice

import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.example.learnservice.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.simpleService.setOnClickListener {
            stopService(MyForegroundService.newIntent(this))
            startService(MyService.newIntent(this))
            showNotification()
        }

        binding.foregroundService.setOnClickListener {
            ContextCompat.startForegroundService(this, MyForegroundService.newIntent(this))
        }

        binding.intentService.setOnClickListener {
            startService(MyIntentService.newIntent(this))
        }
    }

    private fun showNotification() {
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_HIGH
            )
            notificationManager.createNotificationChannel(notificationChannel)
        }
        val notification = NotificationCompat.Builder(
            this,
            CHANNEL_ID
        ).setContentTitle(SERVICE_TITLE)
            .setContentText(SERVICE_CONTENT)
            .setSmallIcon(R.drawable.tommy)
            .build()

        notificationManager.notify(NOTIFICATION_ID, notification)
    }

    companion object {
        private const val CHANNEL_NAME = "TIMER_CHANNEL"
        private const val CHANNEL_ID = "timer_channel"
        private const val SERVICE_TITLE = "Timer"
        private const val SERVICE_CONTENT = "Timer is running"
        private const val NOTIFICATION_ID = 12
    }
}