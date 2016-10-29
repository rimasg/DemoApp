package com.sid.demoapp.scheduledjobs;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

/**
 * Created by Okis on 2016.09.04 @ 09:53.
 */

public final class AlarmManagerHelper {
    private static AlarmManager manager = null;
    private Activity activity;

    public AlarmManagerHelper(Activity activity) {
        this.activity = activity;
        if (manager == null) {
            manager = (AlarmManager) activity.getSystemService(Context.ALARM_SERVICE);
        }
    }

    public void scheduleAlarmAfter(int seconds) {
        PendingIntent operation = buildNotificationReceiverIntent();
        manager.set(AlarmManager.RTC, System.currentTimeMillis() + seconds * 1000, operation);
    }

    private PendingIntent buildNotificationReceiverIntent() {
        final Intent notificationReceiverIntent = new Intent(activity, AlarmNotificationReceiver.class);
        return PendingIntent.getBroadcast(activity, 0, notificationReceiverIntent, 0);
    }
}
