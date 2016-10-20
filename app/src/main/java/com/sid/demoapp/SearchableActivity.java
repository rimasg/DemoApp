package com.sid.demoapp;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.provider.SearchRecentSuggestions;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.animation.BounceInterpolator;
import android.widget.TextView;

import com.sid.demoapp.provider.SearchSuggestionProvider;


public class SearchableActivity extends AppCompatActivity {
    private static final String TAG = "SearchableActivity";

    TextView searchQuery;
    private boolean swiping = false;
    private int swipeSlop = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchable);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        searchQuery = (TextView) findViewById(R.id.search_query);
        searchQuery.setOnTouchListener(listener);
        handleIntent(getIntent());
    }

    private void handleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            final String query = intent.getStringExtra(SearchManager.QUERY);
            addQueryToRecentSuggestions(query);
            doSearch(query);
        }
    }

    private void addQueryToRecentSuggestions(String query) {
        final SearchRecentSuggestions suggestions = new SearchRecentSuggestions(this,
                SearchSuggestionProvider.AUTHORITY, SearchSuggestionProvider.MODE);
        suggestions.saveRecentQuery(query, null);
    }

    private void doSearch(String query) {
        searchQuery.setText(String.format(getString(R.string.search_query_text), query));
    }

    private View.OnTouchListener listener = new View.OnTouchListener() {
        private float downX;
        private float downY;

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    downX = event.getX();
                    downY = event.getY();
                    break;
                case MotionEvent.ACTION_CANCEL:
                    resetAnimatedView(v);
                    swiping = false;
                    downX = 0.f;
                    downY = 0.f;
                    break;
                case MotionEvent.ACTION_MOVE: {
                    float x = event.getX() + v.getTranslationX();
                    float y = event.getY() + v.getTranslationY();

                    downX = downX == 0.f ? x : downX;
                    downY = downY == 0.f ? y : downY;

                    float deltaX = x - downX;
                    float deltaY = y - downY;

                    if (!swiping && isSwiping(deltaX, deltaY)) {
                        swiping = true;
                        v.getParent().requestDisallowInterceptTouchEvent(true);
                    } else {
                        swipeView(v, deltaX, deltaY);
                    }
                }
                break;
                case MotionEvent.ACTION_UP: {
                    if (swiping) {
                        float x = event.getX() + v.getTranslationX();
                        float y = event.getY() + v.getTranslationY();

                        float deltaX = x - downX;
                        float deltaY = y - downY;
                        float deltaXAbs = Math.abs(deltaX);

                        handleViewSwipingIn(v, deltaX, deltaY);
                    }
                    downX = 0.f;
                    downY = 0.f;
                    swiping = false;
                }
                break;
                default:
                    return false;
            }
            return true;
        }
    };

    private void handleViewSwipingIn(final View child, float deltaX, float deltaY) {
        float deltaXAbs = Math.abs(deltaX);
        float fractionCovered = 1.f - (deltaXAbs / child.getWidth());
        long duration = Math.abs((int) (1 - fractionCovered) * 200);

        final ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(child,
                PropertyValuesHolder.ofFloat("alpha", 1.f),
                PropertyValuesHolder.ofFloat("translationX", 0.f),
                PropertyValuesHolder.ofFloat("rotationY", 0.f));
        animator.setDuration(duration).setInterpolator(new BounceInterpolator());
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                child.setTranslationX(0.f);
                child.setTranslationY(0.f);
            }
        });
        animator.setTarget(child);
        animator.start();
    }

    private void swipeView(View child, float deltaX, float deltaY) {
        float deltaXAbs = Math.abs(deltaX);
        float fractionCovered = deltaXAbs / (float) child.getWidth();

        child.setTranslationX(deltaX);
        child.setAlpha(1.f - fractionCovered);

        if (deltaX > 0) {
            child.setRotationY(-15 * fractionCovered);
        } else {
            child.setRotationY(15 * fractionCovered);
        }
    }

    private boolean isSwiping(float deltaX, float deltaY) {
        if (swipeSlop < 0) {
            swipeSlop = ViewConfiguration.get(this).getScaledTouchSlop();
        }

        boolean swiping = false;
        float absDeltaX = Math.abs(deltaX);

        if (absDeltaX > swipeSlop) {
            return true;
        }

        return swiping;
    }

    private void resetAnimatedView(View child) {
        child.setAlpha(1.f);
        child.setTranslationX(0.f);
        child.setTranslationY(0.f);
        child.setRotation(0.f);
        child.setRotationX(0.f);
        child.setRotationY(0.f);
        child.setScaleX(1.f);
        child.setScaleY(1.f);
    }
}
