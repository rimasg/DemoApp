package com.sid.demoapp.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.util.Log;

public class DataProvider extends ContentProvider {
    private static final String TAG = "DataProvider";

    private static final String CREATE_SQL = "CREATE TABLE "
            + DataProviderContract.TABLE_NAME + " ("
            + DataProviderContract._ID + " INTEGER PRIMARY KEY,"
            + DataProviderContract.COLUMN_NAME_DATA + " TEXT"
            + ")";
    private static final String DELETE_SQL = "DROP TABLE IF EXISTS " + DataProviderContract.TABLE_NAME;
    private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    public DataProvider() {
    }

    static {
        uriMatcher.addURI(DataProviderContract.AUTHORITY, DataProviderContract.TABLE_NAME, 1);
        uriMatcher.addURI(DataProviderContract.AUTHORITY, DataProviderContract.TABLE_NAME + "/#", 2);
    }

    private SQLiteOpenHelper dbHelper;

    @Override
    public boolean onCreate() {
        dbHelper = new DatabaseHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        // TODO: 2016.09.24 Tesing UriMatcher, remove later
        switch (uriMatcher.match(uri)) {
            case 1:
                Log.d(TAG, "query: Table selected");
                break;
            case 2:
                Log.d(TAG, "query: Item selected");
                break;
        }
        final SQLiteDatabase db = dbHelper.getReadableDatabase();
        final Cursor cursor = db.query(DataProviderContract.TABLE_NAME, projection, selection, selectionArgs, null, null, null);
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        final SQLiteDatabase db = dbHelper.getWritableDatabase();
        final long id = db.insert(DataProviderContract.TABLE_NAME, null, values);
        if (id != -1) {
            getContext().getContentResolver().notifyChange(uri, null);
        } else {
            throw new SQLException("Insert error: " + uri);
        }
        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = dbHelper.getWritableDatabase();
        final int count = db.delete(DataProviderContract.TABLE_NAME, selection, selectionArgs);
        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        final SQLiteDatabase db = dbHelper.getWritableDatabase();
        final int rows = db.update(DataProviderContract.TABLE_NAME, values, selection, selectionArgs);
        if (rows != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        } else {
            throw new SQLException("Update error: " + uri);
        }
        return -1;
    }

    static class DatabaseHelper extends SQLiteOpenHelper{
        static final String DATABASE_NAME = "data.db";
        static final int DATABASE_VERSION = 1;

        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_SQL);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL(DELETE_SQL);
            onCreate(db);
        }
    }
}
