package com.sid.demoapp.cardstream;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.LayoutTransition;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.LinearLayout;

/**
 * Created by Okis on 2017.01.06.
 */

public class CardStreamLinearLayout extends LinearLayout {

    private boolean layouted = false;
    private boolean swiping = false;
    private int swipeSlope = -1;

    private CardStreamAnimator animators;
    private OnTouchListener touchListener = new OnTouchListener() {

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
                    float x = v.getTranslationX() + event.getX();
                    float y = v.getTranslationY() + event.getY();

                    downX = downX == 0.f ? x : downX;
                    downY = downY == 0.f ? y : downY;

                    float deltaX = x - downX ;
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
                        float x = v.getTranslationX() + event.getX();
                        float y = v.getTranslationY() + event.getY();

                        float deltaX = x - downX;
                        float deltaY = y - downY;

                        float deltaXAbs = Math.abs(deltaX);
                        boolean remove = deltaXAbs > v.getWidth() / 2;
                        if (remove) {
                            handleViewSwipingOut(v, deltaX, deltaY);
                        } else {
                            handleViewSwipingIn(v, deltaX, deltaY);
                        }
                    }
                    swiping = false;
                    downX = 0.f;
                    downY = 0.f;
                }
                break;
                default:
                    return false;
            }
            return true;
        }
    };

    private boolean isSwiping(float deltaX, float deltaY) {
        if (swipeSlope < 0) {
            swipeSlope = ViewConfiguration.get(getContext()).getScaledWindowTouchSlop();
        }

        float deltaYAbs = Math.abs(deltaX);
        if (deltaYAbs > swipeSlope) {
            return true;
        }
        return false;
    }

    private void resetAnimatedView(View v) {
        v.setAlpha(1.f);
        v.setTranslationX(0.f);
        v.setTranslationY(0.f);
        v.setRotation(0.f);
        v.setRotationX(0.f);
        v.setRotationY(0.f);
        v.setScaleX(1.f);
        v.setScaleY(1.f);
    }

    private void swipeView(View v, float deltaX, float deltaY) {
        float deltaXAbs = Math.abs(deltaX);
        float fractionCovered = deltaXAbs / (float) v.getWidth();

        v.setTranslationX(deltaX);
        v.setAlpha(1.f - fractionCovered);

        if (deltaX > 0) {
            v.setRotationY(-15.f * fractionCovered);
        } else {
            v.setRotationY(15.f * fractionCovered);
        }
    }

    private void handleViewSwipingIn(final View v, float deltaX, float deltaY) {
        final ObjectAnimator animator = animators.getViewSwipeInAnimator(v, deltaX, deltaY);
        if (animator != null) {
            animator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    v.setTranslationX(0.f);
                    v.setTranslationY(0.f);
                }
            });
        } else {
            v.setTranslationX(0.f);
            v.setTranslationY(0.f);
        }

        if (animator != null) {
            animator.setTarget(v);
            animator.start();
        }
    }

    private void handleViewSwipingOut(final View v, float deltaX, float deltaY) {
        final ObjectAnimator animator = animators.getViewSwipeOutAnimator(v, deltaX, deltaY);
        if (animator != null) {
            animator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    removeView(v);
                }
            });
        } else {
            removeView(v);
        }

        if (animator != null) {
            animator.setTarget(v);
            animator.start();
        }
    }

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
        resetAnimatedView(cardView);
        cardView.setOnTouchListener(touchListener);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);

        if (changed && !layouted) {
            layouted = true;

            ObjectAnimator animator;
            LayoutTransition layoutTransition = new LayoutTransition();

            animator = animators.getDisappearingAnimator(getContext());
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
