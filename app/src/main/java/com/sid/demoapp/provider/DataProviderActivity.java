package com.sid.demoapp.provider;

import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FilterQueryProvider;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.sid.demoapp.R;
import com.sid.demoapp.databinding.ActivityDataProviderBinding;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

public class DataProviderActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final String columnsToDisplay[] = new String[] { DataProviderContract.COLUMN_NAME_DATA};
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
        final Button vSearch = (Button) findViewById(R.id.action_search);
        vSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search(vDataEntry.getText().toString());
            }
        });
        final ListView vDataList = (ListView) findViewById(R.id.data_list);
        adapter = new SimpleCursorAdapter(this, R.layout.data_item, null, columnsToDisplay, resourceIds, 0);
        vDataList.setAdapter(adapter);
        vDataList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                deleteItem(id);
                return true;
            }
        });
        vDataList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                showItemInfo(id);
            }
        });
        setAdapterFilter();

        getLoaderManager().initLoader(0, null, this);
    }

    public void enterData(String s) {
        if (TextUtils.isEmpty(s)) return;
        ContentValues content = new ContentValues();
        content.put(DataProviderContract.COLUMN_NAME_DATA, s);
        getContentResolver().insert(DataProviderContract.CONTENT_URI, content);
    }

    private void showItemInfo(long rowId) {
        final Uri itemUri = DataProviderContract.CONTENT_URI.buildUpon().appendPath(String.valueOf(rowId)).build();
        getContentResolver().query(
                itemUri,
                DataProviderContract.PROJECTION,
                "_ID = ?", new String[]{String.valueOf(rowId)}, null);
    }

    public int deleteData() {
        return getContentResolver().delete(DataProviderContract.CONTENT_URI, null, null);
    }

    private int deleteItem(long rowId) {
        return getContentResolver().delete(DataProviderContract.CONTENT_URI, "_ID = ?", new String[]{String.valueOf(rowId)});
    }

    private void search(String s) {
        adapter.getFilter().filter(s);
    }

    private void setAdapterFilter() {
        adapter.setFilterQueryProvider(new FilterQueryProvider() {
            @Override
            public Cursor runQuery(CharSequence constraint) {
                final Cursor cursor = getContentResolver().query(
                        DataProviderContract.CONTENT_URI,
                        DataProviderContract.PROJECTION,
                        DataProviderContract.COLUMN_NAME_DATA + " LIKE ?", new String[]{"%" + constraint + "%"}, null);
                return cursor;
            }
        });
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
