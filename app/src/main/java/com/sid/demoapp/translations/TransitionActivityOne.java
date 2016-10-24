package com.sid.demoapp.translations;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.sid.demoapp.R;

public class TransitionActivityOne extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transition);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
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
    }

    private void launchActivityTransition() {
        final Bundle bundle = ActivityOptionsCompat.makeCustomAnimation(this,
                R.anim.slide_in_left,
                R.anim.slide_out_right)
                .toBundle();
        startActivity(new Intent(TransitionActivityOne.this, TransitionActivityTwo.class), bundle);
    }
}
