package com.sid.demoapp.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

import com.sid.demoapp.R;

/**
 * TODO: document your custom view class.
 */
public class TitleView extends View {
    private String text; // TODO: use a default from R.string...
    private int color = Color.RED; // TODO: use a default from R.color...
    private float dimension = 0; // TODO: use a default from R.dimen...
    private Drawable drawable;

    private TextPaint textPaint;
    private float textWidth;
    private float textHeight;

    public TitleView(Context context) {
        super(context);
        init(null, 0);
    }

    private void init(AttributeSet attrs, int defStyle) {
        // Load attributes
        final TypedArray a = getContext().obtainStyledAttributes(
                attrs, R.styleable.TitleView, defStyle, 0);

        text = a.getString(
                R.styleable.TitleView_text);
        color = a.getColor(
                R.styleable.TitleView_color,
                color);
        // Use getDimensionPixelSize or getDimensionPixelOffset when dealing with
        // values that should fall on pixel boundaries.
        dimension = a.getDimension(
                R.styleable.TitleView_dimension,
                dimension);

        if (a.hasValue(R.styleable.TitleView_drawable)) {
            drawable = a.getDrawable(
                    R.styleable.TitleView_drawable);
            drawable.setCallback(this);
        }

        a.recycle();

        // Set up a default TextPaint object
        textPaint = new TextPaint();
        textPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        textPaint.setTextAlign(Paint.Align.LEFT);

        // Update TextPaint and text measurements from attributes
        invalidateTextPaintAndMeasurements();
    }

    private void invalidateTextPaintAndMeasurements() {
        textPaint.setTextSize(dimension);
        textPaint.setColor(color);
        textWidth = textPaint.measureText(text);

        Paint.FontMetrics fontMetrics = textPaint.getFontMetrics();
        textHeight = fontMetrics.bottom;
    }

    public TitleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public TitleView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // TODO: consider storing these as member variables to reduce
        // allocations per draw cycle.
        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();
        int paddingRight = getPaddingRight();
        int paddingBottom = getPaddingBottom();

        int contentWidth = getWidth() - paddingLeft - paddingRight;
        int contentHeight = getHeight() - paddingTop - paddingBottom;

        // Draw the  drawable on top of the text.
        if (drawable != null) {
            drawable.setBounds(paddingLeft, paddingTop,
                    paddingLeft + contentWidth, paddingTop + contentHeight);
            drawable.draw(canvas);
        }

        // Draw the text.
        canvas.drawText(text,
                paddingLeft + (contentWidth - textWidth) / 2,
                paddingTop + (contentHeight + textHeight) / 2,
                textPaint);
    }

    /**
     * Gets the string attribute value.
     *
     * @return The string attribute value.
     */
    public String getString() {
        return text;
    }

    /**
     * Sets the view's  string attribute value. In the  view, this string
     * is the text to draw.
     *
     * @param String The  string attribute value to use.
     */
    public void setString(String String) {
        text = String;
        invalidateTextPaintAndMeasurements();
    }

    /**
     * Gets the color attribute value.
     *
     * @return The color attribute value.
     */
    public int getColor() {
        return color;
    }

    /**
     * Sets the view's color attribute value. In the  view, this color
     * is the font color.
     *
     * @param Color The color attribute value to use.
     */
    public void setColor(int Color) {
        color = Color;
        invalidateTextPaintAndMeasurements();
    }

    /**
     * Gets the dimension attribute value.
     *
     * @return The dimension attribute value.
     */
    public float getDimension() {
        return dimension;
    }

    /**
     * Sets the view's dimension attribute value. In the  view, this dimension
     * is the font size.
     *
     * @param Dimension The dimension attribute value to use.
     */
    public void setDimension(float Dimension) {
        dimension = Dimension;
        invalidateTextPaintAndMeasurements();
    }

    /**
     * Gets the drawable attribute value.
     *
     * @return The drawable attribute value.
     */
    public Drawable getDrawable() {
        return drawable;
    }

    /**
     * Sets the view's drawable attribute value. In the  view, this drawable is
     * drawn above the text.
     *
     * @param Drawable The  drawable attribute value to use.
     */
    public void setDrawable(Drawable Drawable) {
        drawable = Drawable;
    }

}
