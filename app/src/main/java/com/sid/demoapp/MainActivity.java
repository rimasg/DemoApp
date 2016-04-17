package com.sid.demoapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import java.util.concurrent.Callable;

import bolts.Continuation;
import bolts.Task;

public class MainActivity extends AppCompatActivity implements ImageDragFragment.OnFragmentInteractionListener{
    private static final String TAG = "MainActivity";
    private RelativeLayout frame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        frame = (RelativeLayout) findViewById(R.id.relative_layout);
        final Button btnBoltsTask = (Button) findViewById(R.id.btnBoltsTask);
        btnBoltsTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                runBoltsTask();
            }
        });
        final Button btnImageDrag = (Button) findViewById(R.id.btnImageDrag);
        btnImageDrag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setImgageDragFragment();
            }
        });
    }

    private void setImgageDragFragment() {
        if (findViewById(R.id.fragment_container) != null) {
            final ImageDragFragment imageDragFragment = ImageDragFragment.newInstance();
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.fragment_container, imageDragFragment)
                    .commit();
        }
    }

    public void runBoltsTask() {
        Task.callInBackground(new Callable<String>() {
            @Override
            public String call() throws Exception {
                final String strToReturn = "I was call by BoltsTask";
                return strToReturn;
            }
        }).onSuccess(new Continuation<String, Void>() {
            @Override
            public Void then(Task<String> task) throws Exception {
                System.out.println(task.getResult());
                return null;
            }
        });
    }

    @Override
    public void onFragmentInteraction() {

    }
}
