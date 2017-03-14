package com.sid.demoapp.dagger;

/**
 * Created by Okis on 2017.01.22.
 */

public class DaggerInjector {
    private static CustomComponent component = DaggerCustomComponent.builder()
            .dataModule(new DataModule())
            .build();

    public static CustomComponent get() {
        return component;
    }
}
