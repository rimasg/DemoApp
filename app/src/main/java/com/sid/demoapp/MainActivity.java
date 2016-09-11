package com.sid.demoapp;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Messenger;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.sid.demoapp.dummy.DummyContent;
import com.sid.demoapp.github.GitHubFragment;
import com.sid.demoapp.github.data.RepoData;
import com.sid.demoapp.jobscheduler.ScheduledJobService;
import com.sid.demoapp.scheduledjobs.ScheduleAlarmManager;
import com.sid.demoapp.utils.BatteryStatusListener;
import com.sid.demoapp.web.WebLoaderFragment;

import java.util.List;
import java.util.concurrent.Callable;

import bolts.Continuation;
import bolts.Task;

public class MainActivity extends AppCompatActivity implements
        ImageDragFragment.OnFragmentInteractionListener,
        MainMenuFragment.OnListFragmentInteractionListener,
        GitHubFragment.OnListFragmentInteractionListener {
    private static final String TAG = "MainActivity";

    public static final int MSG_START = 1;
    public static final int MSG_END = 2;

    private BatteryStatusListener batteryStatus;
    private ComponentName serviceComponent;
    private ScheduledJobService jobService;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_START:
                    Toast.makeText(MainActivity.this, "Message Object received", Toast.LENGTH_SHORT).show();
                    jobService = (ScheduledJobService) msg.obj;
                    jobService.setUiCallback(MainActivity.this);
                    break;
                case MSG_END:
                    break;
            }
        }
    };
    private Button btnSchedule;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button btnBoltsTask = (Button) findViewById(R.id.btnBoltsTask);
        assert btnBoltsTask != null;
        btnBoltsTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                runBoltsTask();
            }
        });

        final Button btnListPackages = (Button) findViewById(R.id.action_list_packages);
        btnListPackages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listPackages();
            }
        });

        final Button btnLaunchCalc = (Button) findViewById(R.id.action_launch_calc);
        btnLaunchCalc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchCalc();
            }
        });

        final Button btnImageDrag = (Button) findViewById(R.id.btnImageDrag);
        assert btnImageDrag != null;
        btnImageDrag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setImageDragFragment();
            }
        });

        final Button btnListView = (Button) findViewById(R.id.btnListView);
        assert btnListView != null;
        btnListView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setMainMenuFragment();
            }
        });

        final Button btnGitHubRepos = (Button) findViewById(R.id.btnGitHubRepos);
        assert btnGitHubRepos != null;
        btnGitHubRepos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setGitHubFragment();
            }
        });
        btnSchedule = (Button) findViewById(R.id.action_job_scheduler);
        btnSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scheduleJob();
            }
        });

        final Button btnLoadDataFromWeb = (Button) findViewById(R.id.action_load_data_from_web);
        btnLoadDataFromWeb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setWebLoaderFragment();
            }
        });

        final Button bntAnimate = (Button) findViewById(R.id.action_animate);
        bntAnimate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, DummyActivity.class));
            }
        });
        //
        batteryStatus = new BatteryStatusListener();
        //
        serviceComponent = new ComponentName(this, ScheduledJobService.class);
        final Intent intent = new Intent(this, ScheduledJobService.class);
        intent.putExtra(ScheduledJobService.MESSENGER, new Messenger(handler));
        startService(intent);

//        dummyMethod();
        scheduleAlarmJobAfter(15);
    }

    private void scheduleJob() {
        final JobInfo.Builder builder = new JobInfo.Builder(1, serviceComponent)
                .setOverrideDeadline(0)
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY);

        final JobScheduler jobScheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);
        jobScheduler.schedule(builder.build());
    }

    public void jobStarted() {
        Toast.makeText(MainActivity.this, "Job started", Toast.LENGTH_SHORT).show();
        btnSchedule.setText("Job started");
        btnSchedule.setBackgroundColor(Color.RED);
    }

    public void jobStopped() {
        Toast.makeText(MainActivity.this, "Job stopped", Toast.LENGTH_SHORT).show();
        btnSchedule.setText("Job stopped");
        btnSchedule.setBackgroundColor(Color.GREEN);
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

    private void launchCalc() {
        final Intent intent = Intent.makeMainSelectorActivity(Intent.ACTION_MAIN, Intent.CATEGORY_APP_GALLERY);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(Intent.createChooser(intent, "Select App to Run"));
    }

    private void setImageDragFragment() {
        if (findViewById(R.id.fragment_container) != null) {
            Fragment imageDragFragment = getSupportFragmentManager().findFragmentByTag(ImageDragFragment.TAG);
            if (imageDragFragment == null) {
                imageDragFragment = ImageDragFragment.newInstance();
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, imageDragFragment, ImageDragFragment.TAG)
                        .commit();
            } else {
                getSupportFragmentManager()
                        .beginTransaction()
                        .show(imageDragFragment)
                        .commit();
            }
        }
    }

    private void setMainMenuFragment() {
        if (findViewById(R.id.fragment_container) != null) {
            Fragment listViewFragment = getSupportFragmentManager().findFragmentByTag(MainMenuFragment.TAG);
            if (listViewFragment == null) {
                listViewFragment = MainMenuFragment.newInstance(0);
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, listViewFragment, MainMenuFragment.TAG)
                        .commit();
            } else {
                getSupportFragmentManager()
                        .beginTransaction()
                        .show(listViewFragment)
                        .commit();
            }
        }
    }

    private void setGitHubFragment() {
        if (findViewById(R.id.fragment_container) != null) {
            Fragment listViewFragment = getSupportFragmentManager().findFragmentByTag(GitHubFragment.TAG);
            if (listViewFragment == null) {
                listViewFragment = GitHubFragment.newInstance(0);
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, listViewFragment, GitHubFragment.TAG)
                        .commit();
            } else {
                getSupportFragmentManager()
                        .beginTransaction()
                        .show(listViewFragment)
                        .commit();
            }
        }
    }

    private void setWebLoaderFragment() {
        if (findViewById(R.id.fragment_container) != null) {
            Fragment listViewFragment = getSupportFragmentManager().findFragmentByTag(WebLoaderFragment.TAG);
            if (listViewFragment == null) {
                listViewFragment = WebLoaderFragment.newInstance();
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, listViewFragment, WebLoaderFragment.TAG)
                        .commit();
            } else {
                getSupportFragmentManager()
                        .beginTransaction()
                        .show(listViewFragment)
                        .commit();
            }
        }
    }

    public void runBoltsTask() {
        Task.callInBackground(new Callable<String>() {
            @Override
            public String call() throws Exception {
                final String strToReturn = "I was called by BoltsTask";
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
    public void onImageDragFragmentInteraction() {
        Toast.makeText(MainActivity.this, "Image Drag Item", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onMainMenuListFragmentInteraction(DummyContent.DummyItem item) {
        Toast.makeText(MainActivity.this, "Main Menu Item", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onGitHubListFragmentInteraction(RepoData item) {
        Toast.makeText(MainActivity.this, "Git Hub Repo: " + item.name, Toast.LENGTH_SHORT).show();
    }

    private void listPackages(){
        int counter = 0;
        PackageManager packageManager = getPackageManager();
        List<ApplicationInfo> installedApplications = packageManager.getInstalledApplications(PackageManager.GET_META_DATA);
        for (ApplicationInfo applicationInfo : installedApplications) {
            Log.d(TAG, counter + " Package Name: " + applicationInfo.packageName);
            Log.d(TAG, counter + " Source Dir: " + applicationInfo.sourceDir);
            counter++;
        }
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
