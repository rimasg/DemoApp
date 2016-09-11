package com.sid.demoapp;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.sid.demoapp.databinding.ActivityDummyBinding;

public class DummyActivity extends AppCompatActivity implements View.OnClickListener{

    private ActivityDummyBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_dummy);
        binding.actionAnimate.setOnClickListener(this);
    }

    public void animateView() {
        Animation animation = AnimationUtils.loadAnimation(this, android.R.anim.slide_out_right);
        animation.setFillAfter(true);
        binding.animatedText.startAnimation(animation);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.action_animate:
                animateView();
                break;
        }
    }
}
