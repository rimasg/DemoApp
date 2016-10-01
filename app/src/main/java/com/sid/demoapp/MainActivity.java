package com.sid.demoapp;

import android.content.ContentResolver;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.sid.demoapp.dummy.MenuContent;
import com.sid.demoapp.github.GitHubFragment;
import com.sid.demoapp.github.data.RepoData;
import com.sid.demoapp.scheduledjobs.ScheduleAlarmManager;
import com.sid.demoapp.utils.BatteryStatusListener;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MainActivity extends AppCompatActivity implements
        ImageDragFragment.OnFragmentInteractionListener,
        MainMenuFragment.OnListFragmentInteractionListener,
        GitHubFragment.OnListFragmentInteractionListener {
    private static final String TAG = "MainActivity";

    private BatteryStatusListener batteryStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_new);

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
        try {
            final Method method = item.itemClass.getMethod("newInstance");
            final Fragment fragment = (Fragment) method.invoke(null);
            getSupportFragmentManager()
                    .beginTransaction()
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
        new ScheduleAlarmManager(this).scheduleAlarmAfter(seconds);
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
//        final SQLiteDatabase db = openOrCreateDatabase("MyDB", MODE_PRIVATE, null);
//        db.execSQL("CREATE TABLE IF NOT EXISTS MyTable (FirstName VARCHAR, LastName VARCHAR, Age INT(3))");
//        db.execSQL("INSERT INTO MyTable VALUES ('Jonas', 'Batonas', '66')");
/*
        final Cursor c = db.rawQuery("SELECT * FROM MyTable", null);

        if (c.moveToFirst()) {
            do {
                Log.i(TAG, "dummyMethod: " + c.getString(c.getColumnIndex("FirstName")));
            } while (c.moveToNext());
        }
*/
//        db.close();
    }
}
