package com.sid.demoapp;

import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Messenger;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.sid.demoapp.async.AsyncTaskActivity;
import com.sid.demoapp.jobscheduler.ScheduledJobService;
import com.sid.demoapp.services.PlayMusicService;
import com.sid.demoapp.tabbed.TabbedActionBarActivity;
import com.sid.demoapp.translations.TransitionActivityOne;

import java.util.List;
import java.util.concurrent.Callable;

import bolts.Continuation;
import bolts.Task;

import static android.content.Context.JOB_SCHEDULER_SERVICE;

public class OtherFragment extends Fragment {
    public static final String TAG = "MainMenuFragment";
    public static final int MSG_START = 1;
    public static final int MSG_END = 2;
    private Button btnSchedule;
    private ComponentName serviceComponent;
    private ScheduledJobService jobService;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_START:
                    Toast.makeText(getActivity(), "Message Object received", Toast.LENGTH_SHORT).show();
                    jobService = (ScheduledJobService) msg.obj;
                    jobService.setUiCallback(OtherFragment.this);
                    break;
                case MSG_END:
                    break;
            }
        }
    };

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public OtherFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static OtherFragment newInstance() {
        OtherFragment fragment = new OtherFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            serviceComponent = new ComponentName(getActivity(), ScheduledJobService.class);
            final Intent intent = new Intent(getActivity(), ScheduledJobService.class);
            intent.putExtra(ScheduledJobService.MESSENGER, new Messenger(handler));
            getActivity().startService(intent);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_other, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final LinearLayout slideMenu = (LinearLayout) view.findViewById(R.id.slide_menu);
        final ImageView btnOpenClose = (ImageView) view.findViewById(R.id.action_open_close);
        btnOpenClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCloseSlideMenu(slideMenu, btnOpenClose);
            }
        });

        final Button btnBoltsTask = (Button) view.findViewById(R.id.btnBoltsTask);
        assert btnBoltsTask != null;
        btnBoltsTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                runBoltsTask();
            }
        });

        final Button btnListPackages = (Button) view.findViewById(R.id.action_list_packages);
        assert btnListPackages != null;
        btnListPackages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listPackages();
            }
        });

        final Button btnLaunchCalc = (Button) view.findViewById(R.id.action_launch_calc);
        assert btnLaunchCalc != null;
        btnLaunchCalc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchCalc();
            }
        });

        final Button bntAnimate = (Button) view.findViewById(R.id.action_fetch_data_from_web);
        assert bntAnimate != null;
        bntAnimate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), DummyActivity.class));
            }
        });

        final Button btnActionBar = (Button) view.findViewById(R.id.action_bar_activity);
        assert btnActionBar != null;
        btnActionBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchActinBarActivity();
            }
        });

        final Button btnPlayMusicService = (Button) view.findViewById(R.id.action_play_music);
        btnPlayMusicService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startMusicService();
            }
        });

        final Button btnStopMusicService = (Button) view.findViewById(R.id.action_stop_music);
        btnStopMusicService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopMusicService();
            }
        });

        final Button btnTranslation = (Button) view.findViewById(R.id.anction_translation);
        btnTranslation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchTranslationActivity();
            }
        });

        final Button btnAsyncTask = (Button) view.findViewById(R.id.action_async_task);
        btnAsyncTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startAsynctask();
            }
        });

        final Button btnTabbedActivity = (Button) view.findViewById(R.id.action_tabbed_activity);
        btnTabbedActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTabbedActivity();
            }
        });

        btnSchedule = (Button) view.findViewById(R.id.action_job_scheduler);
        btnSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scheduleJob();
            }
        });
    }

    private void openCloseSlideMenu(View view, ImageView btnOpenClose) {
        ObjectAnimator animator;
        if (view.getTranslationY() > 0.001f) {
            animator = ObjectAnimator.ofFloat(view, "translationY", -view.getHeight() +
                    btnOpenClose.getHeight());
        } else {
            animator = ObjectAnimator.ofFloat(view, "translationY", view.getHeight() -
                    btnOpenClose.getHeight());
        }
        animator.setTarget(view);
        animator.start();
    }

    private void startTabbedActivity() {
        startActivity(new Intent(getActivity(), TabbedActionBarActivity.class));
    }

    private void startAsynctask() {
        startActivity(new Intent(getActivity(), AsyncTaskActivity.class));
    }

    private void launchTranslationActivity() {
        final ActivityOptionsCompat options = ActivityOptionsCompat.makeCustomAnimation(
                getActivity(), R.anim.slide_in_left, R.anim.slide_out_right);
        startActivity(new Intent(getActivity(), TransitionActivityOne.class), options.toBundle());
    }

    private void startMusicService() {
        getActivity().startService(new Intent(getActivity(), PlayMusicService.class));
    }

    private void stopMusicService() {
        getActivity().stopService(new Intent(getActivity(), PlayMusicService.class));
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    private void launchActinBarActivity() {
        startActivity(new Intent(getActivity(), ActionBarActivity.class));
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void scheduleJob() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            final JobInfo.Builder builder = new JobInfo.Builder(1, serviceComponent)
                    .setOverrideDeadline(0)
                    .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY);

            final JobScheduler jobScheduler = (JobScheduler) getActivity().getSystemService(JOB_SCHEDULER_SERVICE);
            jobScheduler.schedule(builder.build());
        }
    }

    public void jobStarted() {
        Toast.makeText(getActivity(), "Job started", Toast.LENGTH_SHORT).show();
        btnSchedule.setText("Job started");
        btnSchedule.setBackgroundColor(Color.RED);
    }

    public void jobStopped() {
        Toast.makeText(getActivity(), "Job stopped", Toast.LENGTH_SHORT).show();
        btnSchedule.setText("Job stopped");
        btnSchedule.setBackgroundColor(Color.GREEN);
    }

    private void launchCalc() {
        final Intent intent = Intent.makeMainSelectorActivity(Intent.ACTION_MAIN, Intent.CATEGORY_APP_GALLERY);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(Intent.createChooser(intent, "Select App to Run"));
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

    private void listPackages(){
        int counter = 0;
        PackageManager packageManager = getActivity().getPackageManager();
        List<ApplicationInfo> installedApplications = packageManager.getInstalledApplications(PackageManager.GET_META_DATA);
        for (ApplicationInfo applicationInfo : installedApplications) {
            Log.d(TAG, counter + " Package Name: " + applicationInfo.packageName);
            Log.d(TAG, counter + " Source Dir: " + applicationInfo.sourceDir);
            counter++;
        }
    }
}
