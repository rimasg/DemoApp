package com.sid.demoapp.lifecycleobserver;

import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

import static androidx.lifecycle.Lifecycle.Event.ON_CREATE;
import static androidx.lifecycle.Lifecycle.Event.ON_PAUSE;
import static androidx.lifecycle.Lifecycle.Event.ON_RESUME;
import static androidx.lifecycle.Lifecycle.Event.ON_START;
import static androidx.lifecycle.Lifecycle.Event.ON_STOP;

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
