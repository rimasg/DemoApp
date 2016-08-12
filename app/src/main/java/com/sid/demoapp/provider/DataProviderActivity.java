package com.sid.demoapp.provider;

import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.sid.demoapp.R;
import com.sid.demoapp.databinding.ActivityDataProviderBinding;

public class DataProviderActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final String columnsToDisplay[] = new String[] { DataProviderContract.DATA };
    private static final int[] resourceIds = new int[] { R.id.data_item };
    private SimpleCursorAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityDataProviderBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_data_provider);
        binding.setClickHandler(new ClickHandler(this));

        final EditText vDataEntry = (EditText) findViewById(R.id.data_entry);
        final Button vDataSubmit = (Button) findViewById(R.id.action_data_submit);
        vDataSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enterData(vDataEntry.getText().toString());
                vDataEntry.setText("");
            }
        });
        final Button vDataDelete = (Button) findViewById(R.id.action_delete);
        vDataDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteData();
            }
        });
        final ListView vDataList = (ListView) findViewById(R.id.data_list);
        adapter = new SimpleCursorAdapter(this, R.layout.data_item, null, columnsToDisplay, resourceIds, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        vDataList.setAdapter(adapter);

        getLoaderManager().initLoader(0, null, this);
    }

    public void enterData(String s) {
        if (TextUtils.isEmpty(s)) return;
        ContentValues content = new ContentValues();
        content.put(DataProviderContract.DATA, s);
        getContentResolver().insert(DataProviderContract.CONTENT_URI, content);
    }

    public void deleteData() {
        getContentResolver().delete(DataProviderContract.CONTENT_URI, null, null);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(this, DataProviderContract.CONTENT_URI, DataProviderContract.PROJECTION, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        adapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        adapter.swapCursor(null);
    }
}
