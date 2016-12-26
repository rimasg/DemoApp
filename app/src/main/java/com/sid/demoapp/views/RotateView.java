package com.sid.demoapp.views;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Scroller;

import com.sid.demoapp.R;

public class RotateView extends View {
    public static final int FLING_VELOCITY_DOWNSCALE = 4;

    private int rotation;

    private Drawable drawable;
    private Scroller scroller;
    private ValueAnimator scrollAnimator;
    private GestureDetector detector;

    public RotateView(Context context) {
        super(context);
        init(null, 0);
    }

    public RotateView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public RotateView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle) {
        // Load attributes
        final TypedArray a = getContext().obtainStyledAttributes(
                attrs, R.styleable.RotateView, defStyle, 0);

        if (a.hasValue(R.styleable.RotateView_drawable)) {
            drawable = a.getDrawable(R.styleable.RotateView_drawable);
            drawable.setCallback(this);
        }

        a.recycle();

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
            scroller = new Scroller(getContext());
        } else {
            scroller = new Scroller(getContext(), null, true);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            scrollAnimator = ValueAnimator.ofFloat(0, 1.0f);
            scrollAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    tickScrollAnimation();
                }
            });
        }

        detector = new GestureDetector(RotateView.this.getContext(), new GestureListener());
        detector.setIsLongpressEnabled(false);
    }

    private void tickScrollAnimation() {
        if (!scroller.isFinished()) {
            scroller.computeScrollOffset();
            setImgRotation(scroller.getCurrY());
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                scrollAnimator.cancel();
            }
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // TODO: consider storing these as member variables to reduce allocations per draw cycle.
        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();
        int paddingRight = getPaddingRight();
        int paddingBottom = getPaddingBottom();

        int contentWidth = getWidth() - paddingLeft - paddingRight;
        int contentHeight = getHeight() - paddingTop - paddingBottom;

        // Draw the drawable.
        if (drawable != null) {
            drawable.setBounds(paddingLeft, paddingTop,
                    paddingLeft + contentWidth, paddingTop + contentHeight);
            drawable.draw(canvas);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean result = detector.onTouchEvent(event);
        if (!result) {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                stopScrolling();
                result = true;
            }
        }
        return result;
    }

    public int getImgRotation() {
        return rotation;
    }

    public void setImgRotation(int rotation) {
        rotation = (rotation % 360 + 360) % 360;
        this.rotation = rotation;
        rotateTo(rotation);
    }

    private void rotateTo(int rotation) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            setRotation(rotation);
        } else {
            invalidate();
        }
    }

    public Drawable getDrawable() {
        return drawable;
    }

    public void setDrawable(Drawable exampleDrawable) {
        drawable = exampleDrawable;
    }

    private boolean isAnimationRunning() {
        return !scroller.isFinished() || (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB);
    }

    private void stopScrolling() {
        scroller.forceFinished(true);
    }

    public static float vectorToScalar(float dx, float dy, float x, float y) {
        float l = (float) Math.sqrt(dx * dx + dy * dy);

        float crossX = -y;
        float crossY = x;

        float dot = crossX * dx + crossY * dy;
        float sign = Math.signum(dot);

        return l * sign;
    }

    private class GestureListener extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
//            return super.onFling(e1, e2, velocityX, velocityY);
            float scrollTheta = vectorToScalar(
                    velocityX,
                    velocityY,
                    e2.getX() - getPivotX(),
                    e2.getY() - getPivotY()
            );
            scroller.fling(
                    0,
                    (int) getImgRotation(),
                    0,
                    (int) scrollTheta / FLING_VELOCITY_DOWNSCALE,
                    0,
                    0,
                    Integer.MIN_VALUE,
                    Integer.MAX_VALUE
            );

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                scrollAnimator.setDuration(scroller.getDuration());
                scrollAnimator.start();
            }
            return true;
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
//            return super.onScroll(e1, e2, distanceX, distanceY);
            float scrollTheta = vectorToScalar(
                    distanceX,
                    distanceY,
                    e2.getX() - getPivotX(),
                    e2.getY() - getPivotY()
            );
            setImgRotation(getImgRotation() - (int) scrollTheta / FLING_VELOCITY_DOWNSCALE);
            return true;
        }
        @Override
        public boolean onDown(MotionEvent e) {
            if (isAnimationRunning()) {
                stopScrolling();
            }
            return true;
        }
    }
}
