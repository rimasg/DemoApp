package com.sid.demoapp.dagger;

import javax.inject.Inject;

/**
 * Created by Okis on 2017.01.22.
 */

public class DrawClass {
    private static final String TAG = DrawClass.class.getSimpleName();

    @Inject
    public DrawClass() { }

    public void draw() {
        System.out.println(TAG);
    }
}
