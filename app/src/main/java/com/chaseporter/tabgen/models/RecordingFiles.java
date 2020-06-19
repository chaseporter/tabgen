package com.chaseporter.tabgen.models;


import android.util.Log;

import androidx.databinding.ObservableArrayList;

import java.io.File;
import java.util.ArrayList;

import javax.inject.Inject;

/**
 * Model to hold previously recorded files. Will be accessed to play, pause, stop, and rename, and delete audio files.
 */
public class RecordingFiles {
    private static final String TAG = "RecordingFiles";
    public ArrayList<String> recordingList = new ArrayList<>();
    private String sourceDirectory;

    @Inject
    public RecordingFiles(String sourceDirectory) {
        this.sourceDirectory = sourceDirectory;
        Log.d(TAG, "RecordingFiles: populating from " + sourceDirectory);
        File sourceDirectoryFile = new File(sourceDirectory);
        File[] recordingFiles = sourceDirectoryFile.listFiles();
        assert recordingFiles != null;
        for (File recordingFile : recordingFiles) {
            recordingList.add(recordingFile.getName());
            Log.d(TAG, "RecordingFiles: " + recordingFile.getName());
        }
    }
    public ArrayList<String> getRecordingList() {
        return this.recordingList;
    }
}
