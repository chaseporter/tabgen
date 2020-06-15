package com.example.tabgen;

import dagger.Module;
import dagger.Provides;

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
