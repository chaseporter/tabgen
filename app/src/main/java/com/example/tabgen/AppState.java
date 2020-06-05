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
    @IntDef({READY, RECORDING})
    @interface State {}
    static final int READY = 0;
    static final int RECORDING = 1;

    @State
    private int appState = READY;

    @Inject
    AppState() {

    }

    private void setState(@State int mode) {
        appState = mode;
        notifyPropertyChanged(BR.appState);
    }

    boolean setRecording() {
        if (appState != READY) {
            Log.e(TAG, "App must be READY before starting to record");
            return false;
        }
        setState(RECORDING);
        Log.i(TAG, "Set AppState to Recording");
        return true;
    }

    boolean setReady() {
        if (appState != RECORDING) {
            Log.e(TAG, "App must be RECORDING to set its state to READY");
            return false;
        }
        setState(READY);
        Log.i(TAG, "Set Appstate to Ready");
        return true;
    }

    @Bindable
    public @State int getAppState() {
        return appState;
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

