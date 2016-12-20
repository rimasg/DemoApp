package com.sid.demoapp.jobscheduler;

import android.annotation.TargetApi;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;

import com.sid.demoapp.OtherFragment;

/**
 * Created by Okis on 2016.08.16 @ 23:12.
 */

@TargetApi(Build.VERSION_CODES.LOLLIPOP)
public class ScheduledJobService extends JobService {
    private static final String TAG = "ScheduledJobService";
    public static final String MESSENGER = "messenger";
    private OtherFragment fragment;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        final Messenger callback = intent.getParcelableExtra(MESSENGER);
        final Message message = Message.obtain();
        message.what = OtherFragment.MSG_START;
        message.obj = this;
        try {
            callback.send(message);
        } catch (RemoteException e) {
            Log.e(TAG, "Error passing service object back to fragment.");
        }
        return START_NOT_STICKY;
    }

    @Override
    public boolean onStartJob(final JobParameters params) {
        fragment.jobStarted();
        new JobTask().execute(params);
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        fragment.jobStopped();
        return false;
    }

    public void setUiCallback(OtherFragment fragment) {
        this.fragment = fragment;
    }

    private class JobTask extends AsyncTask<JobParameters, Void, Void> {
        protected JobParameters param;

        @Override
        protected Void doInBackground(JobParameters... params) {
            param = params[0];

            new Runnable() {
                @Override
                public void run() {
                    try {
                        java.util.concurrent.TimeUnit.MILLISECONDS.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }.run();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            jobFinished(param, false);
        }
    }
}
