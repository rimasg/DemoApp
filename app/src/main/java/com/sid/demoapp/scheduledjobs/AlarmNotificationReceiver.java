package com.sid.demoapp.scheduledjobs;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.sid.demoapp.MainActivity;

/**
 * Created by Okis on 2016.09.04 @ 10:42.
 */
public class AlarmNotificationReceiver extends BroadcastReceiver{
    private static final int NOTIFICATION_ID = 966;

    @Override
    public void onReceive(Context context, Intent intent) {
        final Intent notificationIntent = new Intent(context, MainActivity.class);
        final PendingIntent contentIntent = PendingIntent.getActivity(context, 0, notificationIntent, PendingIntent.FLAG_IMMUTABLE);

        final Notification.Builder notificationBuilder = new Notification.Builder(context)
                .setTicker("DemoApp started")
                .setSmallIcon(android.R.drawable.ic_notification_overlay)
                .setContentTitle("DemoApp")
                .setContentText("Go ahead and run DemoApp")
                .setContentIntent(contentIntent);

        final NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(NOTIFICATION_ID, notificationBuilder.build());
    }
}
