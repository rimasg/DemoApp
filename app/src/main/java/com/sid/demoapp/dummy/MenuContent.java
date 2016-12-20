package com.sid.demoapp.dummy;

import com.sid.demoapp.ImageDragFragment;
import com.sid.demoapp.OtherFragment;
import com.sid.demoapp.github.GitHubFragment;
import com.sid.demoapp.web.WebLoaderFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Helper class for providing sample name for user interfaces created by
 * Android template wizards.
 * <p/>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class MenuContent {

    public static final List<MenuItem> ITEMS = new ArrayList<>();

    static {
        addItem(createMenuItem("Image Drag", ImageDragFragment.class));
        addItem(createMenuItem("GitHub", GitHubFragment.class));
        addItem(createMenuItem("Web Loader", WebLoaderFragment.class));
        addItem(createMenuItem("Other Fragment", OtherFragment.class));
        addItem(createMenuItem("Test 1", OtherFragment.class));
        addItem(createMenuItem("Test 2", OtherFragment.class));
        addItem(createMenuItem("Test 3", OtherFragment.class));
        addItem(createMenuItem("Test 4", OtherFragment.class));
        addItem(createMenuItem("Test 5", OtherFragment.class));
    }

    private static void addItem(MenuItem item) {
        ITEMS.add(item);
    }

    private static MenuItem createMenuItem(String itemName, Class<?> itemClass) {
        return new MenuItem(itemName, itemClass);
    }

    public static class MenuItem {
        public final String name;
        public final Class<?> itemClass;

        public MenuItem(String name, Class<?> itemClass) {
            this.name = name;
            this.itemClass = itemClass;
        }

        @Override
        public String toString() {
            return name;
        }
    }
}
