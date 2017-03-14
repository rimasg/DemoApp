package com.sid.demoapp.dagger;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Okis on 2017.03.11.
 */

@Module
public class DataModule {
    @Provides
    OtherPrintClass provideOtherClass() { return new OtherPrintClass(); }
}
