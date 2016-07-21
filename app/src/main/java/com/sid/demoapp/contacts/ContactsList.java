package com.sid.demoapp.contacts;

import android.content.ContentResolver;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

/**
 * Created by SID on 2016-07-21 @ 17:17 @ 18:00.
 */
public class ContactsList extends AppCompatActivity{
    private static final String TAG = "ContactsList";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getContactsList();
    }

    public void getContactsList() {
        String[] FROM_COLUMNS = {ContactsContract.Contacts.DISPLAY_NAME};
        final ContentResolver cr = getContentResolver();
        final Cursor cursor = cr.query(
                ContactsContract.Contacts.CONTENT_URI,
                null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                final String contactName = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                Log.i(TAG, "getContactsList: " + contactName);
            } while (cursor.moveToNext());
        }
    }
}
