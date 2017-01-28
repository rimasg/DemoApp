package com.sid.demoapp.rxandroid;


import android.support.annotation.NonNull;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;

/**
 * Created by Okis on 2017.01.28.
 */

public class ObservableClass {

    private final String[] items = new String[]{"A", "B", "C", "X", "Y"};

    public static void main(String[] args) {
        final ObservableClass observable = new ObservableClass();
        observable.initObservables();
    }

    @NonNull
    public void initObservables() {
        simpleObservable();
        mapObservable();
        filterObservable();
        countObservable();
    }

    @NonNull
    private void simpleObservable() {
        Observable.fromArray(items)
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        System.out.println("Simple Observable: " + s);
                    }
                });
    }

    @NonNull
    private void mapObservable() {
        Observable.fromArray(items)
                .map(new Function<String, String>() {
                    @Override
                    public String apply(String s) throws Exception {
                        return "Transform - " + s;
                    }
                })
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        System.out.println("Transformed Observable: " + s);
                    }
                });
    }

    @NonNull
    private void filterObservable() {
        Observable.fromArray(items)
                .filter(new Predicate<String>() {
                    @Override
                    public boolean test(String s) throws Exception {
                        return s.equals("A");
                    }
                })
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        System.out.println("Filtered \"A\" Observable: " + s);
                    }
                });
    }

    @NonNull
    private void countObservable() {
        Observable.fromArray(items)
                .count()
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        System.out.println("Total Observables: " + aLong);
                    }
                });
    }
}
