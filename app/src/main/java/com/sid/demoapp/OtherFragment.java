package com.sid.demoapp;

import android.Manifest;
import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.arch.lifecycle.ViewModelProviders;
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
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.fortislabs.commons.utils.PermissionUtils;
import com.fortislabs.commons.utils.StorageUtils;
import com.sid.demoapp.async.AsyncTaskActivity;
import com.sid.demoapp.jobscheduler.ScheduledJobService;
import com.sid.demoapp.model.LiveDataModel;
import com.sid.demoapp.services.PlayMusicService;
import com.sid.demoapp.tabbed.TabbedActionBarActivity;
import com.sid.demoapp.translations.TransitionActivityOne;
import com.sid.demoapp.ui.PorterDuffActivity;
import com.sid.demoapp.ui.RotateViewActivity;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import bolts.Continuation;
import bolts.Task;

import static android.content.Context.JOB_SCHEDULER_SERVICE;

public class OtherFragment extends Fragment {
    private static final int PERMISSION_REQUEST_WRITE_EXTERNAL_STORAGE = 968;

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
    private LiveDataModel liveDataModel;

    public OtherFragment() {
    }

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
        liveDataModel = ViewModelProviders.of(getActivity()).get(LiveDataModel.class);

        initLiveData();
    }

    private void initLiveData() {
        liveDataModel.msg.observe(this, this::newLiveDateMsg);
    }

    private void newLiveDateMsg(String  msg) {
        Log.i(TAG, "newLiveDateMsg: " + msg);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_other, container, false);
        liveDataModel.msg.setValue("View created");
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final LinearLayout slideMenu = (LinearLayout) view.findViewById(R.id.slide_menu);
        final ImageView btnOpenClose = (ImageView) view.findViewById(R.id.action_open_close);
        btnOpenClose.setOnClickListener(v -> openCloseSlideMenu(slideMenu, btnOpenClose));

        final Button btnBoltsTask = (Button) view.findViewById(R.id.btnBoltsTask);
        assert btnBoltsTask != null;
        btnBoltsTask.setOnClickListener(v -> runBoltsTask());

        final Button btnListPackages = (Button) view.findViewById(R.id.action_list_packages);
        assert btnListPackages != null;
        btnListPackages.setOnClickListener(v -> listPackages());

        final Button btnLaunchCalc = (Button) view.findViewById(R.id.action_launch_calc);
        assert btnLaunchCalc != null;
        btnLaunchCalc.setOnClickListener(v -> launchCalc());

        final Button bntAnimate = (Button) view.findViewById(R.id.action_fetch_data_from_web);
        assert bntAnimate != null;
        bntAnimate.setOnClickListener(v -> startActivity(new Intent(getActivity(), WebDataFetchActivity.class)));

        final Button btnActionBar = (Button) view.findViewById(R.id.action_bar_activity);
        assert btnActionBar != null;
        btnActionBar.setOnClickListener(v -> launchActinBarActivity());

        final Button btnPlayMusicService = (Button) view.findViewById(R.id.action_play_music);
        btnPlayMusicService.setOnClickListener(v -> startMusicService());

        final Button btnStopMusicService = (Button) view.findViewById(R.id.action_stop_music);
        btnStopMusicService.setOnClickListener(v -> stopMusicService());

        final Button btnTranslation = (Button) view.findViewById(R.id.anction_translation);
        btnTranslation.setOnClickListener(v -> launchTranslationActivity());

        final Button btnAsyncTask = (Button) view.findViewById(R.id.action_async_task);
        btnAsyncTask.setOnClickListener(v -> startAsynctask());

        final Button btnTabbedActivity = (Button) view.findViewById(R.id.action_tabbed_activity);
        btnTabbedActivity.setOnClickListener(v -> startTabbedActivity());

        final Button btnRotateView = (Button) view.findViewById(R.id.action_rotate_view);
        btnRotateView.setOnClickListener(v -> startRotateViewActivity());

        final Button btnPorterDuff = (Button) view.findViewById(R.id.action_porter_duff);
        btnPorterDuff.setOnClickListener(v -> startPorterDuffActivity());

        final Button btnActivityChooser = (Button) view.findViewById(R.id.action_activity_chooser);
        btnActivityChooser.setOnClickListener(v -> startActivityChooser());

        final Button btnKotlin = (Button) view.findViewById(R.id.action_kotlin);
        btnKotlin.setOnClickListener(v -> startKotlinActivity());

        final Button btnSimpleModel = (Button) view.findViewById(R.id.action_simple_model);
        btnSimpleModel.setOnClickListener(v -> startSimpleModelActivity());

        btnSchedule = (Button) view.findViewById(R.id.action_job_scheduler);
        btnSchedule.setOnClickListener(v -> scheduleJob());
    }

    private void startTabbedActivity() {
        startActivity(new Intent(getActivity(), TabbedActionBarActivity.class));
    }

    private void startRotateViewActivity() {
        startActivity(new Intent(getActivity(), RotateViewActivity.class));
    }

    private void startPorterDuffActivity() {
        startActivity(new Intent(getActivity(), PorterDuffActivity.class));
    }

    private void startActivityChooser() {
        final Intent intent = new Intent(Intent.ACTION_VIEW);
        final Intent chooser = Intent.createChooser(intent, "Select App to run:");
        startActivity(chooser);
    }

    private void startKotlinActivity() {
        startActivity(new Intent(getActivity(), KotlinActivity.class));
    }

    private void startSimpleModelActivity() {
        startActivity(new Intent(getActivity(), SimpleModelActivity.class));
    }

    private void launchTranslationActivity() {
        final ActivityOptionsCompat options = ActivityOptionsCompat.makeCustomAnimation(
                getActivity(), R.anim.slide_in_left, R.anim.slide_out_right);
        startActivity(new Intent(getActivity(), TransitionActivityOne.class), options.toBundle());
    }

    private void startAsynctask() {
        startActivity(new Intent(getActivity(), AsyncTaskActivity.class));
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
        ArrayList<String> apkList = new ArrayList<>();

        PackageManager packageManager = getActivity().getPackageManager();
        List<ApplicationInfo> installedApplications = packageManager.getInstalledApplications(PackageManager.GET_META_DATA);
        for (ApplicationInfo applicationInfo : installedApplications) {
            final String apk =
                    "Package: " + applicationInfo.packageName +
                    " # SrcDir: " + applicationInfo.sourceDir;
            apkList.add(apk);
        }

        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) !=
                PackageManager.PERMISSION_GRANTED) {

        }
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            PermissionUtils.requestPermission((AppCompatActivity) getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE, PERMISSION_REQUEST_WRITE_EXTERNAL_STORAGE, false);
            return;
        } else {
            final File outputFile = StorageUtils.getOutputMediaFile(getActivity(), StorageUtils.MEDIA_TYPE_TEXT, "apk-list.txt");
            if (null == outputFile) return;
            try {
                final FileOutputStream fos = new FileOutputStream(outputFile);
                final BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(fos));
                for (String apkItem : apkList) {
                    writer.append(apkItem);
                    writer.newLine();
                }
                writer.close();
                fos.close();
                Toast.makeText(getActivity(), "Packages loaded :)", Toast.LENGTH_SHORT).show();
                Log.d("ExternalStorage", outputFile.getAbsolutePath());
            } catch (IOException e) {
                final String errorMsg = "Error writing " + outputFile;
                Log.w("ExternalStorage", errorMsg, e);
                Toast.makeText(getActivity(), errorMsg, Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void launchCalc() {
        final Intent intent = Intent.makeMainSelectorActivity(Intent.ACTION_MAIN, Intent.CATEGORY_APP_GALLERY);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(Intent.createChooser(intent, "Select App to Run"));
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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSION_REQUEST_WRITE_EXTERNAL_STORAGE:
                if (PermissionUtils.isPermissionGranted(permissions, grantResults, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    listPackages();
                }
                break;
        }
    }
}
