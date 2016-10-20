package com.sid.demoapp.loaders;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import com.sid.demoapp.dummy.MenuContent;

import java.util.List;

/**
 * Created by S86335 on 2016-10-19.
 */

public class CustomLoader extends AsyncTaskLoader<List<MenuContent.MenuItem>> {

    public CustomLoader(Context context) {
        super(context);
    }

    @Override
    public List<MenuContent.MenuItem> loadInBackground() {
        // FIXME: 2016-10-20 this method is never gets called
        return MenuContent.ITEMS;
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        if (MenuContent.ITEMS != null) {
            deliverResult(MenuContent.ITEMS);
        }
    }
}
