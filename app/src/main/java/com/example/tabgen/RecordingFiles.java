package com.example.tabgen;


import android.util.Log;

import androidx.databinding.ObservableArrayList;

import javax.inject.Inject;

/**
 * Model to hold previously recorded files. Will be accessed to play, pause, stop, and rename, and delete audio files.
 */
public class RecordingFiles {
    private static final String TAG = "RecordingFiles";
    public ObservableArrayList<String> recordingList;
    private String sourceDirectory;

    @Inject
    public RecordingFiles(String sourceDirectory) {
        this.sourceDirectory = sourceDirectory;
        Log.d(TAG, "RecordingFiles: populating from " + sourceDirectory);
    }
}
