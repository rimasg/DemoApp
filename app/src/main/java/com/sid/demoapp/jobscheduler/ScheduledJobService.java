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
    public static final String MESSENGER = "messenger";
    private MainActivity activity;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        final Messenger callback = intent.getParcelableExtra(MESSENGER);
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
    public boolean onStartJob(final JobParameters params) {
        activity.jobStarted();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    java.util.concurrent.TimeUnit.MILLISECONDS.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                jobFinished(params, false);
            }
        }).start();
        return true;
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
