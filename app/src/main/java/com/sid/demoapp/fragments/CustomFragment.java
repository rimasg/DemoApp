package com.sid.demoapp.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.ViewTreeObserver;

/**
 * Created by Okis on 2016.11.26.
 */

public class CustomFragment extends Fragment {
    private ViewTreeObserver.OnGlobalLayoutListener globalLayoutListener;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        globalLayoutListener = new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {

            }
        };
    }
}
