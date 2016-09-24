package com.sid.demoapp;

/**
 * Created by Okis on 2016.09.24 @ 19:50.
 */

public interface BaseView<T extends BasePresenter> {
    void setPresenter(T presenter);
}
