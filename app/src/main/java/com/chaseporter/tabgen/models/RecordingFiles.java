package com.chaseporter.tabgen.models;


import android.util.Log;

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


    /** Method to delete a recording and remove it from the recordingList
     * @param position - position of file in recordingList to be removed
     * @return boolean indicating whether the file was successfully deleted
     */
    public boolean deleteFile(int position) {
        Log.d(TAG, "deleteFile: " + recordingList.get(position));
        String filePath = sourceDirectory + File.separator + recordingList.get(position);
        File fileToDelete = new File(filePath);
        boolean deleted = fileToDelete.delete();
        if (deleted) recordingList.remove(position);
        return deleted;
    }


    /** Function to set file currently being edited.
     * @param position - position of file being edited in recordingList
     */
    public void setEditing(int position) {
    }
}
