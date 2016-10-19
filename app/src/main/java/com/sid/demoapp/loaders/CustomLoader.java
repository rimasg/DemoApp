package com.sid.demoapp.loaders;

import android.content.Context;

import com.sid.demoapp.dummy.MenuContent;

import java.util.List;

/**
 * Created by S86335 on 2016-10-19.
 */

public class CustomLoader extends android.support.v4.content.AsyncTaskLoader<List<MenuContent.MenuItem>> {

    public CustomLoader(Context context) {
        super(context);
    }

    @Override
    public List<MenuContent.MenuItem> loadInBackground() {
        return MenuContent.ITEMS;
    }
}
