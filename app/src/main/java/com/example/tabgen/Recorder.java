package com.example.tabgen;

import android.util.Log;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class Recorder {
    private static final String TAG = "Recorder";
    private AppState mAppState;

    @Inject
    Recorder(AppState appState) {
        mAppState = appState;
    }

    public void toggle() {
        if (mAppState.isReady()) {
            startRecording();
            return;
        }
        stopRecording();
    }

    private void startRecording() {
        mAppState.setRecording();
    }

    private void stopRecording() {
        mAppState.setReady();
    }

}
