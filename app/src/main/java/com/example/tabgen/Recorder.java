package com.example.tabgen;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class Recorder {
    private static final String TAG = "Recorder";
    private AppState appState;

    @Inject
    Recorder(AppState mAppState) {
        appState = mAppState;
    }

    public void toggle() {
        if (appState.isReady()) startRecording();
        else stopRecording();
    }

    private void startRecording() {
        appState.setRecording();
    }

    private void stopRecording() {
        appState.setReady();
    }

}
