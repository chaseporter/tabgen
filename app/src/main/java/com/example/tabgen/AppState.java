package com.example.tabgen;

import android.util.Log;

import androidx.annotation.IntDef;
import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import java.lang.annotation.Retention;

import javax.inject.Inject;
import javax.inject.Singleton;

import static java.lang.annotation.RetentionPolicy.CLASS;

@Singleton
public class AppState extends BaseObservable {
    private static final String TAG = "AppState";

    @Retention(CLASS)
    @IntDef({READY, STARTING, RECORDING, STOPPING, PLAYING})
    @interface State {}
    static final int READY = 0;
    static final int STARTING = 1;
    static final int RECORDING = 2;
    static final int STOPPING = 3;
    static final int PLAYING = 4;

    @State
    private int appState = READY;

    @Inject
    AppState() {
    }

    synchronized private void setState(@State int mode) {
        appState = mode;
        notifyPropertyChanged(BR.currentState);
    }

    synchronized void setRecording() throws IllegalStateException {
        if (appState != STARTING) {
            throw new IllegalStateException("App must be STARTING to set its state to RECORDING");
        }
        setState(RECORDING);
        Log.d(TAG, "Set state to RECORDING");
    }

    synchronized void setStarting() throws IllegalStateException {
        if (appState != READY) {
            throw new IllegalStateException("App must be READY to set its state to STARTING");
        }
        setState(STARTING);
        Log.d(TAG, "Set state to STARTING");

    }

    synchronized void setReady() throws IllegalStateException {
        if (appState != STOPPING) {
            throw new IllegalStateException("App must be RECORDING to set its state to READY");
        }
        setState(READY);
        Log.d(TAG, "Set state to READY");
    }

    synchronized void setStopping() throws IllegalStateException {
        if (appState != RECORDING) {
            throw new IllegalStateException("App must be RECORDING to set its state to READY");
        }
        setState(STOPPING);
        Log.d(TAG, "Set state to STOPPING");
    }
    
    synchronized  void setPlaying() throws IllegalStateException {
        if (appState != READY) {
            throw new IllegalStateException("App must be READY to set its state to PLAYING");
        }
        setState(PLAYING);
        Log.d(TAG, "Set state to PLAYING");
    }

    @Bindable
    public @State int getAppState() {
        return appState;
    }

    @Bindable public String getCurrentState() {
        switch (appState) {
            case READY: return "Ready";
            case STARTING: return "Starting";
            case RECORDING: return "Recording";
            case STOPPING: return "Stopping";
            case PLAYING: return "Playing";
            default: return "";
        }
    }

    @Bindable
    public boolean isRecording() {
        return appState == RECORDING;
    }

    @Bindable
    public boolean isReady() {
        return appState == READY;
    }
}

