package com.sid.demoapp;

import android.arch.lifecycle.LifecycleObserver;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.ads.InterstitialAd;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.sid.demoapp.github.GitHubFragment;
import com.sid.demoapp.github.data.RepoData;
import com.sid.demoapp.lifecycleobserver.TestLifecycleObserver;
import com.sid.demoapp.menu.MainMenuFragment;
import com.sid.demoapp.menu.MenuContent;
import com.sid.demoapp.scheduledjobs.AlarmManagerHelper;
import com.sid.demoapp.utils.BatteryStatusListener;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MainActivity extends AppCompatActivity implements
        ImageDragFragment.OnFragmentInteractionListener,
        MainMenuFragment.OnListFragmentInteractionListener,
        GitHubFragment.OnListFragmentInteractionListener {
    private static final String TAG = "MainActivity";
    private BatteryStatusListener batteryStatus;
    private Toolbar mainToolbar;
    private InterstitialAd interstitialAd;
    private FirebaseAnalytics firebaseAnalytics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main_transparent_toolbar);
        setContentView(R.layout.activity_main_new);
        getLifecycle().addObserver(new TestLifecycleObserver());
        // MobileAds.initialize(this, null);
/*
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
*/
        mainToolbar = (Toolbar) findViewById(R.id.main_toolbar);
        mainToolbar.setTitle("");
        setSupportActionBar(mainToolbar);
        final MainMenuFragment fragment = MainMenuFragment.newInstance();
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragment_main_container, fragment)
                .commit();

        //
        batteryStatus = new BatteryStatusListener();
        //
        scheduleAlarmJobAfter(15);
    }

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter filter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        registerReceiver(batteryStatus, filter);
    }

    @Override
    protected void onPause() {
        unregisterReceiver(batteryStatus);
        super.onPause();
    }

    @Override
    public void onImageDragFragmentInteraction() {
        Toast.makeText(MainActivity.this, "Image Drag Item", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onMainMenuListFragmentInteraction(MenuContent.MenuItem item) {
        if (item.itemClass == null) return;
        try {
            final Method method = item.itemClass.getMethod("newInstance");
            final Fragment fragment = (Fragment) method.invoke(null);
            getSupportFragmentManager()
                    .beginTransaction()
                    .setCustomAnimations(
                            R.anim.slide_in_left, R.anim.slide_out_right,
                            R.anim.slide_in_right, R.anim.slide_out_left)
                    .replace(R.id.fragment_main_container, fragment)
                    .addToBackStack(null)
                    .commit();
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onGitHubListFragmentInteraction(RepoData item) {
        Toast.makeText(MainActivity.this, "Git Hub Repo: " + item.name, Toast.LENGTH_SHORT).show();
    }

    private void scheduleAlarmJobAfter(int seconds) {
        Toast.makeText(this, "Job Scheduled to start after " + seconds + " seconds", Toast.LENGTH_SHORT).show();
        new AlarmManagerHelper(this).scheduleAlarmAfter(seconds);
    }

    public Toolbar getToolbar() {
        return mainToolbar;
    }

    private void dummyMethod() {
        final ContentResolver resolver = getContentResolver();
        final String[] columnsToExtract = {ContactsContract.Contacts._ID, ContactsContract.Contacts.DISPLAY_NAME};
        final Cursor cursor = resolver.query(ContactsContract.Contacts.CONTENT_URI,
                columnsToExtract, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                Log.i(TAG, "dummyMethod: Name: " + cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)));
            } while (cursor.moveToNext());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_activity_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_help:
                Toast.makeText(this, "Help", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.action_settings:
                Toast.makeText(this, "Settings", Toast.LENGTH_SHORT).show();
                return true;
            default: return super.onOptionsItemSelected(item);
        }
    }
}