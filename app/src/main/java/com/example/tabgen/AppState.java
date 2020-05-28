package com.example.tabgen;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;

import javax.inject.Inject;
import javax.inject.Singleton;

import static java.lang.annotation.RetentionPolicy.CLASS;

@Singleton
public class AppState {
    @Retention(CLASS)
    @IntDef({READY, RECORDING})
    public @interface State {}
    static final int READY = 0;
    static final int RECORDING = 1;

    @State
    private int appState = READY;

    @Inject
    AppState() {

    }

    public void setState(@State int mode) {
        appState = mode;
    }
}
