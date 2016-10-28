package com.sid.demoapp.async;

import android.widget.TextView;

/**
 * Created by Okis on 2016.10.28.
 */

public class GenericAsyncTaskOpsImpl implements GenericAsyncTaskOps<Void, Void, Void> {

    private TextView textView;

    public GenericAsyncTaskOpsImpl(TextView textView) {
        this.textView = textView;
    }

    @Override
    public void onPreExecute() {
        textView.setText("Process in progress...");
    }

    @Override
    public Void doInBackground(Void... params) {
        try {
            Thread.sleep(3000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void onPostExecute(Void aVoid) {
        textView.setText("Process finished");
    }
}
