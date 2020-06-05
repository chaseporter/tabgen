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

    void setRecording() throws IllegalStateException {
        if (appState != READY) {
            throw new IllegalStateException("App must be READY to set its state to RECORDING");
        }
        setState(RECORDING);
        Log.d(TAG, "Set AppState to Recording");
    }

    void setReady() throws IllegalStateException {
        if (appState != RECORDING) {
            throw new IllegalStateException("App must be RECORDING to set its state to READY");
        }
        setState(READY);
        Log.d(TAG, "Set Appstate to Ready");
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

