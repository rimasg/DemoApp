package com.sid.demoapp.battery;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.BatteryManager;
import android.os.Build;
import android.os.Bundle;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.sid.demoapp.R;

/**
 * Created by Okis on 2016.08.13 @ 09:43.
 */

public class BatteryStatusReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        final Bundle extras = intent.getExtras();
        final int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
        final int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
        final float batteryLevel = level * 100 / (float)  scale;

        final int iconResource = extras.getInt(BatteryManager.EXTRA_ICON_SMALL);
        final String channelId = "battery_notification_channel";
        final NotificationCompat.Builder notification = new NotificationCompat.Builder(context, channelId)
                .setSmallIcon(iconResource)
                .setTicker("Battery status changed: " + batteryLevel)
                .setContentTitle("Battery status: " + batteryLevel)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            final String channelName = context.getString(R.string.app_name);
            final NotificationChannel channel = new NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManagerCompat.from(context).createNotificationChannel(channel);
        }

        NotificationManagerCompat.from(context).notify(1, notification.build());
    }
}
