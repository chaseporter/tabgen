package com.chaseporter.tabgen.models;


import android.util.Log;

import androidx.databinding.BaseObservable;

import java.io.File;
import java.util.ArrayList;

import javax.inject.Inject;
import javax.inject.Singleton;

import com.chaseporter.tabgen.BR;
/**
 * Model to hold previously recorded files. Will be accessed to play, pause, stop, and rename, and delete audio files.
 */
@Singleton
public class RecordingFiles extends BaseObservable {
    private static final String TAG = "RecordingFiles";
    public ArrayList<String> recordingList = new ArrayList<>();
    private String sourceDirectory;
    private AppState appState;
    private int selectedFileIndex = -1;

    @Inject
    public RecordingFiles(String sourceDirectory, AppState appState) {
        this.sourceDirectory = sourceDirectory;
        this.appState = appState;
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

    /** Function to play a recording from recordingList
     * @param position - position of file in recordingList to be played
     */
    public void playStop(int position) {
        if (appState.isReady()) {
            Log.d(TAG, "playStop: playing " + recordingList.get(position));
            appState.setPlaying();
        } else {
            Log.d(TAG, "playStop: stopping " + recordingList.get(position));
            appState.setReady();
        }
    }

    /** Method to add a recording to recordingList
     *
     * @param recordingName - recording to add to recordingList
     */
    public void addRecording(String recordingName) {
        Log.d(TAG, "addRecording: " + recordingName);
        recordingList.add(0, recordingName);
        if (selectedFileIndex > -1) selectedFileIndex += 1;
        notifyPropertyChanged(BR.recordingFiles);
    }

    /** Method to delete a recording and remove it from the recordingList
     * @param position - position of file in recordingList to be removed
     */
    public void deleteRecording(int position) {
        Log.d(TAG, "deleteFile: " + recordingList.get(position));
        String filePath = sourceDirectory + File.separator + recordingList.get(position);
        File fileToDelete = new File(filePath);
        boolean deleted = fileToDelete.delete();
        if (deleted) {
            recordingList.remove(position);
            selectedFileIndex = -1;
            notifyPropertyChanged(BR.recordingFiles);
        }
    }


    /** Function to set file currently being edited.
     * @param position - position of file being edited in recordingList
     */
    public void selectRecording(int position) {
        if (selectedFileIndex == position) {
            selectedFileIndex = -1;
        } else {
            selectedFileIndex = position;
        }
        notifyPropertyChanged(BR.recordingFiles);
    }

    public int getSelectedRecordingIndex() {
        return selectedFileIndex;
    }
    public String getSelectedRecordingName() {return recordingList.get(selectedFileIndex);}
}
