package com.sid.demoapp.async;

/**
 * Created by Okis on 2016.10.28.
 */

public interface GenericAsyncTaskOps<Params, Progress, Result> {
    void onPreExecute();
    Result doInBackground(Params... params);
    void onPostExecute(Result result);
}
