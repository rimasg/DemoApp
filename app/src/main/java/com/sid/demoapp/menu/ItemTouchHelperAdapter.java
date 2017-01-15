package com.sid.demoapp.menu;

/**
 * Created by Okis on 2017.01.15.
 */

public interface ItemTouchHelperAdapter {
    boolean onItemMove(int fromPosition, int toPosition);

    void onItemDismiss(int position);
}
