package com.swati.newsfeed.firebase

import android.widget.Toast
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.swati.newsfeed.notification.NotificationUtils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyFirebaseMessagingService : FirebaseMessagingService() {

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        val clickAction = remoteMessage.notification?.clickAction
        val data = remoteMessage.data
        val notificationData = remoteMessage.notification

        NotificationUtils.getInstance(context = applicationContext).displayNotification(
            channelName = "",
            channelDescription = "",
            channelID = notificationData?.channelId ?: "",
            textTitle = notificationData?.title ?: "",
            textContent = notificationData?.body ?: "",
            imageUrl = notificationData?.imageUrl.toString() ?: "",
            clickAction = clickAction ?: "",
            url = if (data.containsKey("url")) data["url"] else "",
            type = if (data.containsKey("type")) data["type"] else "",
            notifyId = if (data.containsKey("id")) data["id"] else "",
        )
    }

    override fun onNewToken(token: String) {
        Toast.makeText(this, "New FCM Token received", Toast.LENGTH_SHORT).show()
    }
}
