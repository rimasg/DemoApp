package com.sid.demoapp.cardstream;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Context;
import android.graphics.Point;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.BounceInterpolator;

/**
 * Created by Okis on 2017.01.07.
 */

public class CardStreamAnimator {

    private float speedFactor = 1.f;

    public ObjectAnimator getDisappearingAnimator(Context context) {
        ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(new Object(),
                PropertyValuesHolder.ofFloat("alpha", 1.f, 0.f),
                PropertyValuesHolder.ofFloat("scaleX", 1.f, 0.f),
                PropertyValuesHolder.ofFloat("scaleY", 1.f, 0.f),
                PropertyValuesHolder.ofFloat("rotation", 0.f, 270.f));

        animator.setDuration((long) (200 * speedFactor));
        return animator;
    }

    public ObjectAnimator getAppearingAnimator(Context context) {
        final Point outPoint = new Point();
        final WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getSize(outPoint);

        ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(new Object(),
                PropertyValuesHolder.ofFloat("alpha", 0.f, 1.f),
                PropertyValuesHolder.ofFloat("translationY", outPoint.y / 2.f, 0.f),
                PropertyValuesHolder.ofFloat("rotation", -45.f, 0.f));

        animator.setDuration((long) (200 * speedFactor));
        return animator;
    }

    public ObjectAnimator getViewSwipeInAnimator(View v, float deltaX, float deltaY) {
        float deltaXAbs = Math.abs(deltaX);
        float fractionCovered = 1.f - (deltaXAbs / v.getWidth());
        long duration = Math.abs((int) ((1 - fractionCovered) * 200 * speedFactor));

        final ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(v,
                PropertyValuesHolder.ofFloat("alpha", 1.f),
                PropertyValuesHolder.ofFloat("translationX", 0.f),
                PropertyValuesHolder.ofFloat("rotationY", 0.f));
        animator.setDuration(duration).setInterpolator(new BounceInterpolator());
        return animator;
    }

    public ObjectAnimator getViewSwipeOutAnimator(View v, float deltaX, float deltaY) {
        float endX;
        float endRotationY;

        float deltaXAbs = Math.abs(deltaX);

        float fractionCovered = 1.f - (deltaXAbs / v.getWidth());
        long duration = Math.abs((int) ((1 - fractionCovered) * 200 * speedFactor));

        endX = deltaX < 0 ? -v.getWidth() : v.getWidth();
        if (deltaX > 0) {
            endRotationY = -15.f;
        } else {
            endRotationY = 15.f;
        }

        final ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(v,
                PropertyValuesHolder.ofFloat("alpha", 0.f),
                PropertyValuesHolder.ofFloat("translationX", endX),
                PropertyValuesHolder.ofFloat("rotationY", endRotationY));
        animator.setDuration(duration);
        return animator;
    }
}
