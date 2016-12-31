package com.sid.demoapp.translations;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.TransitionDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.sid.demoapp.R;
import com.sid.demoapp.views.CalendarMonthView;

public class TransitionActivityOne extends AppCompatActivity implements CalendarMonthView.OnDateSelectedListener {

    private CalendarMonthView calendarMonthView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        setContentView(R.layout.activity_transition);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(Color.GRAY);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        final TextView activityName = (TextView) findViewById(R.id.activity_name);
        activityName.setText(this.getClass().getSimpleName());
        final Button btnAction = (Button) findViewById(R.id.action);
        btnAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchActivityTransition();
            }
        });
        calendarMonthView = (CalendarMonthView) findViewById(R.id.calendar_month_view);
        calendarMonthView.setOnDateSelectedListener(this);
        ((TransitionDrawable) ((ImageView) findViewById(R.id.color_image)).getDrawable()).startTransition(3000);
        final ImageView imgPropeller = (ImageView) findViewById(R.id.propeller);
        final Animation anim = AnimationUtils.loadAnimation(this, R.anim.rotation);
        imgPropeller.startAnimation(anim);
    }

    @Override
    protected void onPause() {
        super.onPause();
        overridePendingTransition(R.anim.slide_out_right, R.anim.slide_in_left);
    }

    private void launchActivityTransition() {
        final ActivityOptionsCompat options = ActivityOptionsCompat.makeCustomAnimation(
                this, R.anim.slide_in_left, R.anim.slide_out_right);
        startActivity(new Intent(this, TransitionActivityTwo.class), options.toBundle());
    }

    @Override
    public void getSelectedDate(int selectedDate) {
        Snackbar.make(calendarMonthView, "Date selected: " + selectedDate, Snackbar.LENGTH_SHORT).show();
    }
}
