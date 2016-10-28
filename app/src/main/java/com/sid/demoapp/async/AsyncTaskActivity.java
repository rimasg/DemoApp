package com.sid.demoapp.async;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.sid.demoapp.R;

public class AsyncTaskActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView tvResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_async_task);

        tvResult = (TextView) findViewById(R.id.result_text);
        final Button btnAsyncTask = (Button) findViewById(R.id.action_async_task);
        btnAsyncTask.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.action_async_task:
                new GenericAsyncTask<>(new GenericAsyncTaskOpsImpl(tvResult)).execute();
                break;
        }
    }
}
