package com.sid.demoapp.rxandroid;


import android.support.annotation.NonNull;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by Okis on 2017.01.28.
 */

public class ObservableClass {

    private final String[] items = new String[]{"A", "B", "C"};

    @NonNull
    public Disposable initObservable() {
        return Observable.fromArray(items)
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        System.out.println(s);
                    }
                });
    }

    public static void main(String[] args) {
        final ObservableClass observable = new ObservableClass();
        observable.initObservable();
    }
}
