package com.sid.demoapp.menu;

import com.sid.demoapp.ScrollViewFragment;
import com.sid.demoapp.cardstream.CardStreamFragment;
import com.sid.demoapp.ImageDragFragment;
import com.sid.demoapp.OtherFragment;
import com.sid.demoapp.github.GitHubFragment;
import com.sid.demoapp.web.WebLoaderFragment;

import java.util.ArrayList;
import java.util.List;

public class MenuContent {

    public static final List<MenuItem> ITEMS = new ArrayList<>();

    static {
        addItem(createMenuItem("Image Drag", ImageDragFragment.class));
        addItem(createMenuItem("GitHub", GitHubFragment.class));
        addItem(createMenuItem("Web Loader", WebLoaderFragment.class));
        addItem(createMenuItem("Other Fragment", OtherFragment.class));
        addItem(createMenuItem("Card Stream", CardStreamFragment.class));
        addItem(createMenuItem("Scroll View", ScrollViewFragment.class));
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
