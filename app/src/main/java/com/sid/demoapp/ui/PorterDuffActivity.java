package com.sid.demoapp.ui;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

import com.sid.demoapp.R;

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

    // TODO: 2016.12.29 implement method correctly functioning
    private void applyPorterDuff() {
        imgView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                imgView.getViewTreeObserver().removeOnGlobalLayoutListener(this);

                final int w = imgView.getWidth();
                final int h = imgView.getHeight();

                final Bitmap dstBitmap = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(res, R.drawable.ladybug), w, h, false);
                final Bitmap srcBitmap = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(res, R.drawable.flower_red), w, h, false);

                final Bitmap bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
                final Canvas canvas = new Canvas(bitmap);
                final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
                paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_ATOP));
                int posX, posY;
                posX = calcOffset(w, dstBitmap.getWidth()); posY = calcOffset(h, dstBitmap.getHeight()) ;
                canvas.drawBitmap(dstBitmap, posX, posY, null);
                posX = calcOffset(w, srcBitmap.getWidth()); posY = calcOffset(h, srcBitmap.getHeight());
                canvas.drawBitmap(srcBitmap, posX, posY, paint);

                imgView.setImageBitmap(bitmap);
            }
        });

    }

    private int calcOffset(int sizeOrigin, int sizeTarget) {
        return (sizeOrigin - sizeTarget) / 2;
    }
}
