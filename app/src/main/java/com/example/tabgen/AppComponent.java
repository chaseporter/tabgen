package com.example.tabgen;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;

@Singleton
@Component(modules = {AppModule.class})
public interface AppComponent {
    AppState getAppState();
    Recorder getRecorder();
    RecordingFiles getRecordingFiles();

    void inject(MainActivity mainActivity);

}
