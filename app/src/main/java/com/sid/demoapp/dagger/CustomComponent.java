package com.sid.demoapp.dagger;

import com.sid.demoapp.github.GitHubFragment;

import dagger.Component;

/**
 * Created by Okis on 2017.01.22.
 */

@Component(modules = {DataModule.class})
public interface CustomComponent {
    void inject(AppClass appClass);
    void inject(GitHubFragment fragment);

    OtherPrintClass getPrintClass();
}
