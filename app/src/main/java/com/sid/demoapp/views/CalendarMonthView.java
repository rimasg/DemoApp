package com.sid.demoapp.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Outline;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewOutlineProvider;

import com.sid.demoapp.R;

import java.util.Calendar;
import java.util.Locale;

/**
 * Created by Okis on 2016.12.11.
 */

public class CalendarMonthView extends View implements OnTouchListener {
    private static final String TAG = "CalendarMonthView";
    public static final int WEEK_DAY_COUNT = 7;
    private Calendar calendar;
    private float dayTextSize;
    private int dayViewSize;
    private int maximumDays;
    private String name;
    private float nameTextSize;
    private Paint paint;
    private Paint boundaryPaint;
    private Drawable backgroundDrawable;
    private int width;
    private int height;
    private int startX;
    private int startY;
    private int startDay;
    private Rect tmpRect;
    private OnDateSelectedListener onDateSelectedListener;

    public CalendarMonthView(Context context) {
        this(context, null);
    }

    public CalendarMonthView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CalendarMonthView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        calendar = Calendar.getInstance(Locale.getDefault());
        paint = new Paint();
        boundaryPaint = new Paint();
        backgroundDrawable = getResources().getDrawable(R.drawable.calendar_bg);
        tmpRect = new Rect();
        init();
    }

    private void init() {
        initCalendar();
        dayTextSize = 24.0f;
        nameTextSize = 24.0f;
        paint.setTextSize(dayTextSize);
        paint.setAntiAlias(true);
        boundaryPaint.setStyle(Paint.Style.STROKE);
        boundaryPaint.setStrokeWidth(2.0f);
        maximumDays = (calendar.getActualMaximum(Calendar.DAY_OF_MONTH) + getMonthStartDate()) - 1;

        // setOutlineProvider(new ClipOutlineProvider());
        // setClipToOutline(true);
        setOnTouchListener(this);
    }

    private void initCalendar() {
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
    }

    private int getMonthStartDate() {
        final int i = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        if (i == 0) {
            return 7;
        }
        return i;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint.setColor(ViewCompat.MEASURED_STATE_MASK);
        for (int position = getMonthStartDate() - 1; position < maximumDays; position++) {
            int day = (position - getMonthStartDate()) + 1;
            canvas.save();
            canvas.translate((float) ((position % 7) * dayViewSize), (float) ((position / 7) * dayViewSize));
            final String s = String.valueOf(day + 1);
            paint.getTextBounds(s, 0, s.length(), tmpRect);
            backgroundDrawable.setBounds(0, 0, dayViewSize, dayViewSize);
            backgroundDrawable.draw(canvas);
            canvas.drawText(s, (float) ((dayViewSize - tmpRect.right) / 2), (float) ((dayViewSize - tmpRect.top) / 2), paint);
            canvas.restore();
        }
        canvas.getClipBounds(tmpRect);
        canvas.drawRect(tmpRect, boundaryPaint);
    }

    public void setOnDateSelectedListener(OnDateSelectedListener listener) {
        onDateSelectedListener = listener;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        width = MeasureSpec.getSize(widthMeasureSpec);
        dayViewSize = width / 7;
        height = (int) (((double) dayViewSize) * Math.ceil((double) (((float) maximumDays) / 7.0f)));
        setMeasuredDimension(width, height);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            startX = (int) (event.getX() / (float) dayViewSize) + 1;
            startY = (int) (event.getY() / (float) dayViewSize);
            startDay = (startX + (startY * 7)) - (getMonthStartDate() - 1);

        } else if (event.getAction() == MotionEvent.ACTION_CANCEL) {
            startDay = -1;
        }
        if (onDateSelectedListener != null) {
            onDateSelectedListener.getSelectedDate(startDay);
        }
        return true;
    }

    public interface OnDateSelectedListener {
        void getSelectedDate(int selectedDate);
    }

    private class ClipOutlineProvider extends ViewOutlineProvider {

        @Override
        public void getOutline(View view, Outline outline) {
            final int margin = Math.min(view.getWidth(), view.getHeight()) / 10;
            outline.setRoundRect(margin, margin, view.getWidth() - margin, view.getHeight() - margin, margin / 2);
        }
    }
}
