package com.chaseporter.tabgen.models;

import android.util.Log;

import androidx.annotation.IntDef;
import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.chaseporter.tabgen.BR;

import java.lang.annotation.Retention;

import javax.inject.Inject;
import javax.inject.Singleton;

import static java.lang.annotation.RetentionPolicy.CLASS;

/**
 * AppState is a single instance of state that the entire access will reference to determine what
 * actions are allowed and what UI to display. This class is Observable so that it can be databound
 * to and the UI can update on changes to it. It is a Singleton so that only one instance of it
 * exists at any time, guaranteeing that all places in the App see the same state.
 */
@Singleton
public class AppState extends BaseObservable {
    private static final String TAG = "AppState";

    /* Using IntDef rather then enum because of the memory advantages of IntDef over enums */
    @Retention(CLASS)
    @IntDef({READY, RECORDING, PLAYING, EDITING})
    @interface State {}
    static final int READY = 0;
    static final int RECORDING = 1;
    static final int PLAYING = 2;
    static final int EDITING = 3;

    @State
    private int appState = READY;

    @Inject
    AppState() {
        Log.d(TAG, "AppState: Created");
    }

    synchronized private void setState(@State int mode) {
        appState = mode;
        notifyPropertyChanged(BR.currentState);
    }

    /**
     * The following set Functions define the possible state flows as to prevent the App from being
     * able to reach illegal states. For example, should not be able to record and play a recording
     * at the same time.
     * @throws IllegalStateException - Error defines state expectations.
     */
    public synchronized void setRecording() throws IllegalStateException {
        if (appState != READY) {
            throw new IllegalStateException("App must be READY to set its state to RECORDING");
        }
        setState(RECORDING);
        Log.d(TAG, "Set state to RECORDING");
    }

    public synchronized void setReady() throws IllegalStateException {
        if (appState != RECORDING && appState != PLAYING) {
            throw new IllegalStateException("App must be RECORDING or PLAYING to set its state to READY");
        }
        setState(READY);
        Log.d(TAG, "Set state to READY");
    }

    public synchronized void setPlaying() throws IllegalStateException {
        if (appState != READY) {
            throw new IllegalStateException("App must be READY to set its state to PLAYING");
        }
        setState(PLAYING);
        Log.d(TAG, "Set state to PLAYING");
    }
    
    public synchronized void setEditing() throws IllegalStateException {
        if (appState != READY) {
            throw new IllegalStateException("App must be READY to set its state to EDITING");
        }
        setState(EDITING);
        Log.d(TAG, "Set state to EDITING");
    }

    @Bindable
    public @State int getAppState() {
        return appState;
    }

    @Bindable public String getCurrentState() {
        switch (appState) {
            case READY: return "Ready";
            case RECORDING: return "Recording";
            case PLAYING: return "Playing";
            case EDITING: return "Editing";
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

    @Bindable
    public boolean isPlaying() { return appState == PLAYING; }
}

