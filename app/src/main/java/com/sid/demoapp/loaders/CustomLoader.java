package com.sid.demoapp.loaders;

import android.content.Context;

import com.sid.demoapp.menu.MenuContent;

import java.util.List;

import androidx.loader.content.AsyncTaskLoader;

/**
 * Created by SID on 2016-10-19.
 */

public class CustomLoader extends AsyncTaskLoader<List<MenuContent.MenuItem>> {

    public CustomLoader(Context context) {
        super(context);
    }

    @Override
    public List<MenuContent.MenuItem> loadInBackground() {
        return MenuContent.ITEMS;
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        if (MenuContent.ITEMS != null) {
            deliverResult(MenuContent.ITEMS);
        }

        if (MenuContent.ITEMS == null || takeContentChanged()) {
            forceLoad();
        }
    }
}
