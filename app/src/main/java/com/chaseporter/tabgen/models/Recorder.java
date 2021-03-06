package com.chaseporter.tabgen.models;

import android.media.MediaRecorder;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Class to handle recording. Call this class to start and stop a recording. Will access state and
 * add recordings to RecordingFiles.
 */
@Singleton
public class Recorder {
    private static final String TAG = "Recorder";
    private AppState appState;
    private String storageDirectory;
    private String currentRecordingName;
    private RecordingFiles recordingFiles;
    private MediaRecorder mediaRecorder;

    @Inject
    Recorder(AppState appState, RecordingFiles recordingFiles, String storageDirectory ) {
        this.appState = appState;
        this.storageDirectory = storageDirectory;
        this.recordingFiles = recordingFiles;
    }

    /* Function called to start a recording. Sets AppState to STARTING, initializes MediaRecorder
    * starts recording, and sets AppState to RECORDING. */
    public void startRecording() {
        SimpleDateFormat s = new SimpleDateFormat("ddMMyyyyhhmmss");
        String timeStamp = s.format(new Date());
        currentRecordingName = timeStamp + ".3gp";
        String filePath = storageDirectory + File.separator + currentRecordingName;
        Log.d(TAG, "startRecording: recording to " + filePath);

        mediaRecorder = new MediaRecorder();
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mediaRecorder.setOutputFile(filePath);
        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        try {
            mediaRecorder.prepare();
        } catch (IOException e) {
            Log.e(TAG, "startRecording: " + e.getMessage());
        }
        mediaRecorder.start();
        appState.setRecording();
    }

    /* Function to stop recording. Sets AppState to STOPPING, stops recording, saves audio file and
    * sets AppState to READY */
    public void stopRecording() {
        if (mediaRecorder != null) {
            mediaRecorder.stop();
            mediaRecorder.release();
            mediaRecorder = null;
        }
        recordingFiles.addRecording(currentRecordingName);
        appState.setReady();
    }
}
