package com.sid.demoapp;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ScrollView;


public class ScrollViewFragment extends Fragment {
    public ScrollViewFragment() {
        // Required empty public constructor
    }

    public static ScrollViewFragment newInstance() {
        ScrollViewFragment fragment = new ScrollViewFragment();
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
        return inflater.inflate(R.layout.fragment_scroll_view, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // TODO: 2017-03-14 implement onScroll ToolBar fade in/out
        final ImageView vHeader = (ImageView) view.findViewById(R.id.header_image);
        final ScrollView vScroll = (ScrollView) view.findViewById(R.id.scroll_view);
        vScroll.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                ViewCompat.setTranslationY(vHeader, scrollX / 2.f);
            }
        });
    }
}
