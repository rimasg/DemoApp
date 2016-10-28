package com.sid.demoapp.async;

import android.widget.TextView;

import com.sid.demoapp.R;

/**
 * Created by Okis on 2016.10.28.
 */

public class GenericAsyncTaskOpsImpl implements GenericAsyncTaskOps<Void, Integer, Void> {

    private final TextView textView;

    public GenericAsyncTaskOpsImpl(TextView textView) {
        this.textView = textView;
    }

    @Override
    public void onPreExecute() {
        textView.setText(R.string.in_progress);
    }

    @Override
    public Void doInBackground(Void... params) {
        for (int i = 10; i > 0; i--) {
            try {
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            final int progressCount = i;
            textView.post(new Runnable() {
                @Override
                public void run() {
                    textView.setText(String.valueOf(progressCount));
                }
            });
        }
        return null;
    }

    @Override
    public void onPostExecute(Void aVoid) {
        textView.setText("Process finished");
    }
}
