package com.sid.demoapp.services

import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import timber.log.Timber

class DemoFirebaseMessagingService : FirebaseMessagingService() {

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        Timber.d("From: " + remoteMessage.from)

        remoteMessage.data.isNotEmpty().let {
            Timber.d("Message Data payload: " + remoteMessage.data)
        }

        remoteMessage.notification?.let {
            Timber.d("Message Notification Body: " + it.body)
        }
    }

    override fun onNewToken(token: String) {
        Timber.d("Refreshed FCM token: $token")
    }
}