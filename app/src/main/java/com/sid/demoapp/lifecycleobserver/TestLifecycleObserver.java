package com.sid.demoapp.lifecycleobserver;

import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.OnLifecycleEvent;

import static android.arch.lifecycle.Lifecycle.Event.ON_CREATE;
import static android.arch.lifecycle.Lifecycle.Event.ON_PAUSE;
import static android.arch.lifecycle.Lifecycle.Event.ON_RESUME;
import static android.arch.lifecycle.Lifecycle.Event.ON_START;
import static android.arch.lifecycle.Lifecycle.Event.ON_STOP;

/**
 * Created by rgaina on 09/06/2018.
 */
public class TestLifecycleObserver implements LifecycleObserver {

    @OnLifecycleEvent(ON_CREATE)
    public void onCreate() {
        printLifeCycleStatus("onCreate");
    }

    @OnLifecycleEvent(ON_START)
    public void onStart() {
        printLifeCycleStatus("onStart");
    }

    @OnLifecycleEvent(ON_RESUME)
    public void onResume() {
        printLifeCycleStatus("onResume");
    }

    @OnLifecycleEvent(ON_PAUSE)
    public void onPause() {
        printLifeCycleStatus("onPause");
    }

    @OnLifecycleEvent(ON_STOP)
    public void onStop() {
        printLifeCycleStatus("onStop");
    }

    public void printLifeCycleStatus(String status) {
        System.out.println("Life Cycle Status:" + status);
    }
}
