package com.sid.demoapp.ui;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.os.Bundle;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

import com.sid.demoapp.R;

import androidx.appcompat.app.AppCompatActivity;

public class PorterDuffActivity extends AppCompatActivity {

    private Resources res;
    private ImageView imgView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_porter_duff);

        res = getResources();
        imgView = (ImageView) findViewById(R.id.imageView);

        applyPorterDuff();
    }

    private void applyPorterDuff() {
        imgView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                imgView.getViewTreeObserver().removeOnGlobalLayoutListener(this);

                final int w = imgView.getWidth();
                final int h = imgView.getHeight();

                final Bitmap dstBitmap = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(res, R.drawable.brunette_girl), w, h, false);
                final Bitmap srcBitmap = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(res, R.drawable.white_pink_sakura), w, h, false);

                final Bitmap bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
                final Canvas canvas = new Canvas(bitmap);
                final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
                paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_ATOP));
                int posX, posY;
                posX = calcOffset(w, dstBitmap.getWidth()); posY = calcOffset(h, dstBitmap.getHeight()) ;
                canvas.drawBitmap(dstBitmap, posX, posY, null);
                posX = calcOffset(w, srcBitmap.getWidth()); posY = calcOffset(h, srcBitmap.getHeight());
                canvas.drawBitmap(srcBitmap, posX, posY, paint);
                // Draw frame around picture
                final Paint rectPaint = new Paint();
                rectPaint.setStyle(Paint.Style.STROKE);
                rectPaint.setStrokeWidth(2.0f);
                canvas.drawRect(0, 0, w, h, rectPaint);

                imgView.setImageBitmap(bitmap);
            }
        });

    }

    private int calcOffset(int sizeOrigin, int sizeTarget) {
        return (sizeOrigin - sizeTarget) / 2;
    }
}
