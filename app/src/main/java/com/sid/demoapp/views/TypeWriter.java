package com.sid.demoapp.views;

import android.content.Context;
import android.os.Handler;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.util.AttributeSet;

import com.sid.demoapp.R;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.ContextCompat;



/**
 * Created by rimasg on 15/07/2018.
 */

/**
 * The class is custom TextView which animates a text you set to it.
 */
public class TypeWriter extends AppCompatTextView {
    private SpannableStringBuilder stringBuilder;
    private Object visibleSpan;
    private Object hiddenSpan;
    private int index;
    private long delay = 30;

    public TypeWriter(Context context) {
        super(context);
        setupTextColors(context);
    }

    public TypeWriter(Context context, AttributeSet attrs) {
        super(context, attrs);
        setupTextColors(context);
    }

    private void setupTextColors(Context context) {
        visibleSpan = new ForegroundColorSpan(ContextCompat.getColor(context, R.color.textColorLight));
        hiddenSpan = new ForegroundColorSpan(ContextCompat.getColor(context, R.color.transparent));
    }

    private Handler handler = new Handler();
    private Runnable characterAdder = new Runnable() {
        @Override
        public void run() {
            stringBuilder.setSpan(visibleSpan, 0, index++, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
            setText(stringBuilder);
            if (index <= stringBuilder.length()) {
                handler.postDelayed(characterAdder, delay);
            }
        }
    };

    public void animateText(CharSequence text) {
        stringBuilder = new SpannableStringBuilder(text);
        stringBuilder.setSpan(hiddenSpan, 0, stringBuilder.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        index = 0;

        setText(stringBuilder);
        handler.removeCallbacks(characterAdder);
        handler.postDelayed(characterAdder, delay);
    }

    public void setCharacterDelay(long mills) {
        if (mills < 0) return;
        delay = mills;
    }

    public boolean isAnimating() {
        return index < stringBuilder.length();
    }

    public void stopAnimating() {
        index = stringBuilder.length();
    }
}
