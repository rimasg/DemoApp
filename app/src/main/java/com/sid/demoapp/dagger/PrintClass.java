package com.sid.demoapp.dagger;

import javax.inject.Inject;

/**
 * Created by Okis on 2017.01.22.
 */

public class PrintClass {
    private static final String TAG = PrintClass.class.getSimpleName();

    @Inject
    public PrintClass() { }

    public void print() {
        System.out.println(TAG);
    }
}
