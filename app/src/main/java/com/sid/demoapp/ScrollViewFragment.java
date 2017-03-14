package com.sid.demoapp;


import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.github.ksoichiro.android.observablescrollview.ObservableScrollView;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks;
import com.github.ksoichiro.android.observablescrollview.ScrollState;
import com.github.ksoichiro.android.observablescrollview.ScrollUtils;


public class ScrollViewFragment extends Fragment implements ObservableScrollViewCallbacks {

    private ImageView vHeader;
    private Toolbar toolbar;

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
        trySetupToolbar();
        vHeader = (ImageView) view.findViewById(R.id.header_image);
        final ObservableScrollView vScroll = (ObservableScrollView) view.findViewById(R.id.scroll_view);
        vScroll.setScrollViewCallbacks(this);
    }

    private void trySetupToolbar() {
        if (getActivity() instanceof MainActivity) {
            MainActivity activity = (MainActivity) getActivity();
            toolbar = activity.getToolbar();
        }
    }

    @Override
    public void onScrollChanged(int scrollY, boolean firstScroll, boolean dragging) {
        ViewCompat.setTranslationY(vHeader, scrollY / 2.f);
        final int parallaxImageHeight = vHeader.getMeasuredHeight();
        float alpha = Math.min(1, (float) scrollY / parallaxImageHeight);
        if (toolbar != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                toolbar.setBackgroundColor(ScrollUtils.getColorWithAlpha(alpha, getResources().getColor(R.color.colorPrimary, null)));
            } else {
                toolbar.setBackgroundColor(ScrollUtils.getColorWithAlpha(alpha, getResources().getColor(R.color.colorPrimary)));
            }
        }
    }

    @Override
    public void onDownMotionEvent() {
        /* no-op */
    }

    @Override
    public void onUpOrCancelMotionEvent(ScrollState scrollState) {
        /* no-op */
    }
}
