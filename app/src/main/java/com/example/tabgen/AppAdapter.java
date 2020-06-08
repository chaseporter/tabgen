package com.example.tabgen;

import android.util.Log;
import android.view.View;

import androidx.core.content.ContextCompat;
import androidx.databinding.BindingAdapter;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class AppAdapter {
    private static final String TAG = "AppAdapter";
    
    @BindingAdapter(value = {"onClickState", "onClickRecorder"}, requireAll = true)
    public static void startAndStopRecording(FloatingActionButton view, final AppState appState, final Recorder recorder) {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (appState.isReady())
                    recorder.startRecording();
                else
                    recorder.stopRecording();
            }
        });
    }
}
