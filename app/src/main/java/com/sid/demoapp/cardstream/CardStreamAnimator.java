package com.sid.demoapp.cardstream;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Context;
import android.graphics.Point;
import android.view.WindowManager;

/**
 * Created by Okis on 2017.01.07.
 */

public class CardStreamAnimator {

    private float speedFactor = 1.f;

    public ObjectAnimator getDisappearingAnmator(Context context) {
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
}
