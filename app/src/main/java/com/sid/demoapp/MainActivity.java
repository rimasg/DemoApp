package com.sid.demoapp;

import android.content.ContentResolver;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
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

import java.util.concurrent.Callable;

import bolts.Continuation;
import bolts.Task;

public class MainActivity extends AppCompatActivity implements
        ImageDragFragment.OnFragmentInteractionListener,
        MainMenuFragment.OnListFragmentInteractionListener,
        GitHubFragment.OnListFragmentInteractionListener {
    private static final String TAG = "MainActivity";

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
        //
        dummyActivity();
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

    public void dummyActivity() {
        final ContentResolver resolver = getContentResolver();
        final String[] columnsToExtract = {ContactsContract.Contacts._ID, ContactsContract.Contacts.DISPLAY_NAME};
        final Cursor cursor = resolver.query(ContactsContract.Contacts.CONTENT_URI,
                columnsToExtract, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                Log.i(TAG, "dummyActivity: Name: " + cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)));
            } while (cursor.moveToNext());
        }
        final SQLiteDatabase db = openOrCreateDatabase("MyDB", MODE_PRIVATE, null);
//        db.execSQL("CREATE TABLE IF NOT EXISTS MyTable (FirstName VARCHAR, LastName VARCHAR, Age INT(3))");
//        db.execSQL("INSERT INTO MyTable VALUES ('Jonas', 'Batonas', '66')");
/*
        final Cursor c = db.rawQuery("SELECT * FROM MyTable", null);

        if (c.moveToFirst()) {
            do {
                Log.i(TAG, "dummyActivity: " + c.getString(c.getColumnIndex("FirstName")));
            } while (c.moveToNext());
        }
*/
        db.close();
    }
}
