package com.sid.demoapp.views;

import android.content.Context;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Okis on 2016.04.02 @ 19:09.
 */
public class BallView extends View {

    private final Paint paint;
    private final Camera camera;
    private final Matrix matrix;

    {
        paint = new Paint();
        paint.setColor(Color.BLUE);
        paint.setStyle(Paint.Style.FILL);
        camera = new Camera();
        matrix = new Matrix();
    }

    public BallView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // TODO: 2016.12.24 finish Matrix 3D rotation implementation
        canvas.save();
        camera.save();
        camera.rotate(0f, 45.0f, 0f);
        camera.getMatrix(matrix);
        camera.restore();
        matrix.preTranslate(-getWidth() / 2, -getHeight() / 2);
        matrix.postTranslate(getWidth() / 2, getHeight() / 2);
        canvas.setMatrix(matrix);
        canvas.drawCircle(100.0f, 100.0f, 100.0f, paint);
        canvas.restore();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int minW = getPaddingLeft() + getPaddingRight() + getSuggestedMinimumWidth();
        int w = Math.max(MeasureSpec.getSize(widthMeasureSpec), minW);
        int minH = getPaddingTop() + getPaddingBottom() + w;
        int h = Math.min(MeasureSpec.getSize(heightMeasureSpec), minH);

        setMeasuredDimension(w, h);
    }
}
