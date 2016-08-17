package com.sid.demoapp.jobscheduler;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Intent;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;

import com.sid.demoapp.MainActivity;

/**
 * Created by Okis on 2016.08.16 @ 23:12.
 */

public class ScheduledJobService extends JobService {
    private static final String TAG = "ScheduledJobService";
    private MainActivity activity;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        final Messenger callback = intent.getParcelableExtra("messenger");
        final Message message = Message.obtain();
        message.what = MainActivity.MSG_START;
        message.obj = this;
        try {
            callback.send(message);
        } catch (RemoteException e) {
            Log.e(TAG, "Error passing service object back to activity.");
        }
        return START_NOT_STICKY;
    }

    @Override
    public boolean onStartJob(JobParameters params) {
        activity.jobStarted();
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        activity.jobStopped();
        return false;
    }

    public void setUiCallback(MainActivity activity) {
        this.activity = activity;
    }
}
