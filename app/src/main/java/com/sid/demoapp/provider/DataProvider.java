package com.sid.demoapp.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

public class DataProvider extends ContentProvider {
    public DataProvider() {
    }

    private static final String DATABASE_NAME = "data.db";
    private static final int DATABASE_VERSION = 1;
    private static final String CREATE_TABLE = "CREATE TABLE "
            + DataProviderContract.TABLE_NAME + "("
            + DataProviderContract._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + DataProviderContract.DATA + " TEXT"
            + ")";

    private SQLiteOpenHelper dbHelper;

    @Override
    public boolean onCreate() {
        dbHelper = new DatabaseHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        final SQLiteDatabase db = dbHelper.getReadableDatabase();
        final Cursor cursor = db.query(DataProviderContract.TABLE_NAME, projection, null, null, null, null, null);
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
        final int count = db.delete(DataProviderContract.TABLE_NAME, null, null);
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
        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_TABLE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + DataProviderContract.TABLE_NAME);
            onCreate(db);
        }
    }
}
