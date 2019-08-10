package com.sid.demoapp.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.BatteryManager;
import android.os.Bundle;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

/**
 * Created by Okis on 2016.08.13 @ 09:43.
 */

public class BatteryStatusListener extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        final Bundle extras = intent.getExtras();
        final int batteryLevel = extras.getInt(BatteryManager.EXTRA_LEVEL);
        final int iconResource = extras.getInt(BatteryManager.EXTRA_ICON_SMALL);
        final NotificationCompat.Builder notification = new NotificationCompat.Builder(context)
                .setTicker("Battery status changed: " + batteryLevel)
                .setContentTitle("Battery status: " + batteryLevel)
                .setSmallIcon(iconResource);
        NotificationManagerCompat.from(context).notify(1, notification.build());
    }
}
