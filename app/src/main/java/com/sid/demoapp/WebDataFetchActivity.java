package com.sid.demoapp;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.view.View;
import android.widget.Toast;

import com.sid.demoapp.databinding.ActivityDummyBinding;
import com.sid.demoapp.services.WebDataFetchService;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

public class WebDataFetchActivity extends AppCompatActivity implements View.OnClickListener, BaseView<BasePresenter> {

    private ActivityDummyBinding binding;
    private WebDataFetch dataFetch;
    private boolean connected = false;
    private ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            dataFetch = WebDataFetch.Stub.asInterface(service);
            connected = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            dataFetch = null;
            connected = false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_dummy);
        binding.actionFetchDataFromWeb.setOnClickListener(this);
    }

    public void fetchDataFromWeb() {
        if (connected) {
            try {
                Toast.makeText(this, "Data from web: " + dataFetch.getWebData(), Toast.LENGTH_SHORT).show();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(this, "Not bound to Service", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        bindService(new Intent(WebDataFetchActivity.this, WebDataFetchService.class), conn, BIND_AUTO_CREATE);
    }

    @Override
    protected void onPause() {
        unbindService(conn);
        super.onPause();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.action_fetch_data_from_web:
                fetchDataFromWeb();
                break;
        }
    }

    @Override
    public void setPresenter(BasePresenter presenter) {

    }
}
