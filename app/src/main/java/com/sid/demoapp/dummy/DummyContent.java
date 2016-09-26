package com.sid.demoapp.dummy;

import com.sid.demoapp.ImageDragFragment;
import com.sid.demoapp.github.GitHubFragment;
import com.sid.demoapp.web.WebLoaderFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p/>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class DummyContent {

    public static final List<MenuItem> ITEMS = new ArrayList<>();

    static {
        addItem(createMenuItem("Image Drag", ImageDragFragment.class));
        addItem(createMenuItem("GitHub", GitHubFragment.class));
        addItem(createMenuItem("WebLoader", WebLoaderFragment.class));
    }

    private static void addItem(MenuItem item) {
        ITEMS.add(item);
    }

    private static MenuItem createMenuItem(String itemName, Class<?> className) {
        return new MenuItem(itemName, className);
    }

    /**
     * A dummy item representing a piece of content.
     */
    public static class MenuItem {
        public final String content;
        public final Class<?> itemClass;

        public MenuItem(String content, Class<?> itemClass) {
            this.content = content;
            this.itemClass = itemClass;
        }

        @Override
        public String toString() {
            return content;
        }
    }
}
