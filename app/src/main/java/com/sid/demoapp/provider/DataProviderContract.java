package com.sid.demoapp.provider;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Okis on 2016.08.11 @ 19:59.
 */

public final class DataProviderContract implements BaseColumns {
    private DataProviderContract() { }

    public static final String TABLE_NAME = "data_table";
    public static final String COLUMN_NAME_DATA = "data";

    public static final String[] PROJECTION = new String[]{
            _ID, // Projection position 0
            COLUMN_NAME_DATA // Projection position 1
    };
    private static final int PROJECTION_DATA_INDEX = 1;

    public static final String SCHEME = "content";
    public static final String AUTHORITY = "com.sid.demoapp.provider";

    public static final Uri BASE_URI = Uri.parse(SCHEME + "://" + AUTHORITY);
    public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_URI, TABLE_NAME);
}
