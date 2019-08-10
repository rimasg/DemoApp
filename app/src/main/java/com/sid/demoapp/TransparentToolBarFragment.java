package com.sid.demoapp;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;


public class TransparentToolBarFragment extends Fragment {

    public TransparentToolBarFragment() {
        // Required empty public constructor
    }

    public static TransparentToolBarFragment newInstance() {
        TransparentToolBarFragment fragment = new TransparentToolBarFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_transparent_tool_bar, container, false);
    }

}
