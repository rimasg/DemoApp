package com.sid.demoapp.services

import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import timber.log.Timber

class DemoFirebaseMessagingService : FirebaseMessagingService() {

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        Timber.d("FCM Message From: " + remoteMessage.from)

        remoteMessage.data.isNotEmpty().let {
            Timber.d("FCM Message Data payload: " + remoteMessage.data)
        }

        remoteMessage.notification?.let {
            Timber.d("FCM Message Notification Body: " + it.body)
        }
    }

    override fun onNewToken(token: String) {
        Timber.d("Refreshed FCM token: $token")
    }
}
