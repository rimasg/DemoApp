package com.sid.demoapp.web;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.os.ResultReceiver;

public class FetchDataFromWebService extends IntentService {
    public static final String KEY_RESULT_RECEIVER = "RESULT_RECEIVER";
    public static final String KEY_WEB_RESULT = "WEB_RESULT";

    private ResultReceiver resultReceiver;

    public FetchDataFromWebService() {
        super("FetchDataFromWebService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        resultReceiver = intent.getParcelableExtra(KEY_RESULT_RECEIVER);
        final String result = new WebLoader().loadDataFromWeb();
        sendResult(WebLoaderFragment.WebResultsReceiver.RESULT_CODE_OK, result);
    }

    private void sendResult(int resultCode, String result) {
        final Bundle resultData = new Bundle();
        resultData.putString(KEY_WEB_RESULT, result);
        resultReceiver.send(resultCode, resultData);
    }
}
