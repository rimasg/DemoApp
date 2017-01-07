package com.sid.demoapp.cardstream;

import android.animation.LayoutTransition;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

/**
 * Created by Okis on 2017.01.06.
 */

public class CardStreamLinearLayout extends LinearLayout {

    private boolean layouted = false;

    private CardStreamAnimator animators;
    private OnTouchListener touchListener = new OnTouchListener() {
        // TODO: 2017.01.07 implement method
        private float downX;
        private float downY;

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN: {

                }
                break;
                case MotionEvent.ACTION_CANCEL: {

                }
                break;
                case MotionEvent.ACTION_MOVE: {

                }
                break;
                case MotionEvent.ACTION_UP: {

                }
                break;
                default:
                    return false;
            }
            return false;
        }
    };

    public CardStreamLinearLayout(Context context) {
        this(context, null);
    }

    public CardStreamLinearLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CardStreamLinearLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        animators = new CardStreamAnimator();
    }

    public void addCard(View cardView) {
        // TODO: 2017.01.07 implement method
        if (cardView.getParent() == null) {
            initCard(cardView);

            ViewGroup.LayoutParams params = cardView.getLayoutParams();
            if (params == null) {
                params = generateDefaultLayoutParams();
            }
            super.addView(cardView, -1, params);
        }
    }

    private void initCard(View cardView) {
        cardView.setOnTouchListener(touchListener);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);

        if (changed && !layouted) {
            layouted = true;

            ObjectAnimator animator;
            LayoutTransition layoutTransition = new LayoutTransition();

            animator = animators.getDisappearingAnmator(getContext());
            layoutTransition.setAnimator(LayoutTransition.DISAPPEARING, animator);

            animator = animators.getAppearingAnimator(getContext());
            layoutTransition.setAnimator(LayoutTransition.APPEARING, animator);

            // TODO: 2017.01.07 implement code
//            layoutTransition.addTransitionListener();

            if (animator != null) {
                layoutTransition.setDuration(animator.getDuration());
            }

            setLayoutTransition(layoutTransition);
        }
    }
}
