package com.sid.demoapp.rxandroid;


import android.support.annotation.NonNull;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;

/**
 * Created by Okis on 2017.01.28.
 */

public class ObservableClass {

    private final String[] items = new String[]{"A", "B", "C", "X", "Y"};
    private final ExecutorService executor;

    public ObservableClass() {
        executor = Executors.newSingleThreadExecutor();
    }

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
        asyncCountObservable();
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
                        System.out.println("Total Observables: " + aLong + "\n");
                    }
                });
    }

    @NonNull
    private void asyncCountObservable() {
        final long startTime = System.currentTimeMillis();
        final long delay = 3000L;

        System.out.println("Start Observables (async): " + startTime);
        final Future<List<String>> futureList = executor.submit(new Callable<List<String>>() {
            @Override
            public List<String> call() throws Exception {
                return Arrays.asList(items);
            }
        });

        Observable.fromFuture(futureList)
                .subscribe(new Consumer<List<String>>() {
                    @Override
                    public void accept(List<String> strings) throws Exception {
                        System.out.println("Total Observables (async): " + strings.toString());
                    }
                });

        elapsedTime(startTime);
    }

    private void elapsedTime(long startTime) {
        final long endTime = System.currentTimeMillis();
        final long elapsedTime = (endTime - startTime) / 1000L;
        System.out.println("End Observables (async):" + endTime + "\nElapsed time: " + elapsedTime + " sec.\n");
    }
}
