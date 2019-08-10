package com.sid.demoapp.model;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

/**
 * Created by rgaina on 08/07/2018.
 */
public class LiveDataModel extends ViewModel {
    public MutableLiveData<String> msg = new MutableLiveData<>();
}
