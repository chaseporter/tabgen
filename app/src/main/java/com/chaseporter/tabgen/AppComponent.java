package com.chaseporter.tabgen;

import com.chaseporter.tabgen.models.AppState;
import com.chaseporter.tabgen.models.Recorder;
import com.chaseporter.tabgen.models.RecordingFiles;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Dagger Component needed to build the models and inject them into the MainActivity
 */
@Singleton
@Component(modules = {AppModule.class})
public interface AppComponent {
    AppState getAppState();
    Recorder getRecorder();
    RecordingFiles getRecordingFiles();

    void inject(MainActivity mainActivity);

}
