package com.bitcodetech.notifications

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.NotificationChannelCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.bitcodetech.notifications.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var notificationManager: NotificationManagerCompat

    @SuppressLint("MissingPermission")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        notificationManager = NotificationManagerCompat.from(this)
        createNotificationChannels()

        /*notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager*/


        binding.btnNotify.setOnClickListener {

            val bitmap = BitmapFactory.decodeResource(
                resources,
                R.mipmap.ic_launcher
            )

            val actionPendingIntent =
                PendingIntent.getActivity(
                    this@MainActivity,
                    1,
                    Intent(this@MainActivity, DemoActivity::class.java),
                    0
                )

            val notification =
                NotificationCompat.Builder(this@MainActivity, "updates")
                    .setContentTitle("BitCode Update!")
                    .setContentText("This is a sample update text....")
                    .setColor(Color.RED)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setLargeIcon(bitmap)
                    .setAutoCancel(true)
                    .setLights(Color.RED, 300, 400)
                    //.setSound(Uri.parse("/storage/bitcode/audio.mp3"))
                    .setVibrate(
                        arrayOf(400L, 500L, 300L, 500L, 400L, 500L).toLongArray()
                    )
                    .setOngoing(true)
//                  .setChannelId("updates")
                    .setContentIntent(actionPendingIntent)
                    .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                    .build()

            notificationManager.notify(1, notification)

        }

        binding.btnCancel.setOnClickListener {
            notificationManager.cancel(1)
        }


    }

    private fun createNotificationChannels() {
        /*val channelUpdates =
            NotificationChannel(
                "updates",
                "Bitcode Updates",
                NotificationManager.IMPORTANCE_HIGH
            )*/
        val updatesChannel = NotificationChannelCompat.Builder(
            "updates",
            NotificationManager.IMPORTANCE_HIGH
        ).setDescription("Receive updates about events at BitCode")
            .setName("BitCode Updates")
            .build()

        notificationManager.createNotificationChannel(updatesChannel)

        val placementChannel = NotificationChannelCompat.Builder(
            "placement",
            NotificationManager.IMPORTANCE_HIGH
        ).setDescription("Receive updates about placements at BitCode")
            .setName("BitCode Placement Updates")
            .build()

        notificationManager.createNotificationChannel(placementChannel)
    }
}