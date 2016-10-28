package com.sid.demoapp.async;

import android.os.AsyncTask;

/**
 * Created by Okis on 2016.10.28.
 */

public class GenericAsyncTask<Params, Progress, Result, Ops extends GenericAsyncTaskOps<Params, Progress, Result>>
        extends AsyncTask<Params, Progress, Result> {

    Ops ops;

    public GenericAsyncTask(Ops ops) {
        this.ops = ops;
    }

    @Override
    protected void onPreExecute() {
        ops.onPreExecute();
    }

    @Override
    protected void onPostExecute(Result result) {
        ops.onPostExecute(result);
    }

    @Override
    protected Result doInBackground(Params... params) {
        return ops.doInBackground(params);
    }
}
