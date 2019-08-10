package com.sid.demoapp.databinding;

import android.os.Bundle;

import com.sid.demoapp.R;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

public class DataBindingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final com.sid.demoapp.databinding.ActivityDataBindingBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_data_binding);

        final User user = new User("Jonas", "Batonas");
        binding.setUser(user);
        final ClickHandlers clickHandlers = new ClickHandlers(binding);
        binding.setHandlers(clickHandlers);
    }
}
