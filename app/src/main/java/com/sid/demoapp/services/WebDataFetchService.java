package com.sid.demoapp.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

import com.sid.demoapp.WebDataFetch;

public class WebDataFetchService extends Service {
    public WebDataFetchService() {
    }

    private final WebDataFetch.Stub webDataFetchImpl = new WebDataFetch.Stub() {
        @Override
        public String getWebData() throws RemoteException {
            return getRandomString();
        }
    };

    private String getRandomString() {
        final String[] s = {"A", "B", "C", "D", "E", "F"};
        return s[(int) (s.length * Math.random())];
    }

    @Override
    public IBinder onBind(Intent intent) {
        return webDataFetchImpl;
    }
}
