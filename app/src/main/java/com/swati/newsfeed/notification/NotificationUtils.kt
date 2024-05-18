package com.swati.newsfeed.notification

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.swati.newsfeed.R
import java.lang.ref.WeakReference
import java.util.Random

class NotificationUtils(val context: Context) {
    private val contextWeakReference: WeakReference<Context> by lazy {
        WeakReference(context)
    }
    private val channelName = context.applicationInfo.loadLabel(context.packageManager).toString()
    private val notificationId: Int = 1
    private var channelId = "com.swati.newsfeed.notification"

    fun displayNotification(
        channelName: String,
        channelDescription: String,
        channelID: String,
        textTitle: String,
        textContent: String,
        imageUrl: String,
        clickAction: String,
        url: String?,
        type: String?,
        notifyId: String?,
    ) {
        createNotificationChannel(channelName, channelDescription, channelID)
        val builder =
            createNotifications(
                textTitle = textTitle,
                textContent = textContent,
                clickAction = clickAction,
                url = url,
                type = type,
            )
        if (builder != null) {
            with(contextWeakReference.get()?.let { NotificationManagerCompat.from(it) }) {
                if (ActivityCompat.checkSelfPermission(
                        context,
                        Manifest.permission.POST_NOTIFICATIONS,
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    return
                }
                this?.notify(notificationId, builder.build())
            }
        }
    }

    private fun createNotifications(
        textTitle: String,
        textContent: String,
        clickAction: String,
        url: String?,
        type: String?,
    ): NotificationCompat.Builder? {
        return contextWeakReference.get()?.let {
            val resultIntent = PendingIntent.getActivity(
                contextWeakReference.get(),
                Random().nextInt(),
                if (clickAction.isEmpty()) {
                    Intent()
                } else {
                    Intent(clickAction).apply {
                        setPackage("com.swati.newsfeed")
                        putExtra("url", url)
                        putExtra("type", type)
                    }
                },
                PendingIntent.FLAG_IMMUTABLE,
            )
            NotificationCompat.Builder(it, channelId)
                .setSmallIcon(R.drawable.ic_author)
                .setColor(it.resources.getColor(R.color.red))
                .setContentIntent(resultIntent)
                .setContentTitle(textTitle)
                .setContentText(textContent)
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setAutoCancel(true)
        }
    }

    private fun createNotificationChannel(
        channelName: String,
        channelDescription: String,
        channelID: String,
    ) {
        if (channelID.isNotEmpty()) channelId = channelID
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = channelName.ifEmpty { this.channelName }
            val descriptionText = channelDescription.ifEmpty { "" }
            val important = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(channelID, name, important).apply {
                description = descriptionText
            }
            val notificationManager: NotificationManager = contextWeakReference.get()
                ?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    companion object {
        fun getInstance(context: Context) = NotificationUtils(context = context)
    }
}