package com.sid.demoapp.dagger;

import dagger.Component;

/**
 * Created by Okis on 2017.01.22.
 */

@Component
public interface CustomComponent {
    void inject(AppClass appClass);
}
