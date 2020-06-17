package com.chaseporter.tabgen;

import dagger.Module;
import dagger.Provides;

/**
 * Dagger Module needed so that storageDirectory can be defined from a Fragment and provided to the
 * models via dependency Injection and that string won't need to be passed around as an argument to
 * functions.
 **/
@Module
public class AppModule {
    private String storageDirectory;

    public AppModule(String storageDirectory) {
        this.storageDirectory = storageDirectory;
    }

    @Provides
    String provideStorageDirectory() {
        return this.storageDirectory;
    }
}
