package com.sid.demoapp.provider;

import android.content.SearchRecentSuggestionsProvider;

/**
 * Created by Okis on 2016.10.01 @ 12:45.
 */

public class SearchSuggestionProvider extends SearchRecentSuggestionsProvider {
    public static final String AUTHORITY = "com.sid.demoapp.provider.SearchSuggestionProvider";
    public static final int MODE = DATABASE_MODE_QUERIES;

    public SearchSuggestionProvider() {
        setupSuggestions(AUTHORITY, DATABASE_MODE_QUERIES);
    }
}
