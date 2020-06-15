package com.example.tabgen;

import android.media.MediaRecorder;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.BlockingDeque;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class Recorder {
    private static final String TAG = "Recorder";
    private AppState appState;
    private String storageDirectory;
    private MediaRecorder mediaRecorder;

    @Inject
    Recorder(AppState appState, String storageDirectory ) {
        this.appState = appState;
        this.storageDirectory = storageDirectory;
    }

    public void startRecording() {
        appState.setStarting();
        String filePath = storageDirectory + File.separator + "audiorecordTest.3gp";
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

    public void stopRecording() {
        appState.setStopping();
        if (mediaRecorder != null) {
            mediaRecorder.stop();
            mediaRecorder.release();
            mediaRecorder = null;
        }
        appState.setReady();
    }
}
