package com.sid.demoapp.model;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

/**
 * Created by rgaina on 08/07/2018.
 */
public class LiveDataModel extends ViewModel {
    public MutableLiveData<String> msg = new MutableLiveData<>();
}
