package com.example.tabgen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;

import com.example.tabgen.databinding.ActivityMainBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    @Inject
    AppState appState;
    @Inject
    Recorder recorder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppComponent appComponent = DaggerAppComponent.create();
        appComponent.inject(this);
        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setState(appState);
        binding.setRecorder(recorder);
        super.onCreate(savedInstanceState);
        final FloatingActionButton recordButton = findViewById(R.id.recordButton);
        recordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (appState.isReady()) {
                    if (checkPermissions()) {
                        recorder.startRecording(getExternalCacheDir().getAbsolutePath() + File.separator + "audiorecordTest.3gp");
                    }
                } else if (appState.isRecording()) {
                    recorder.stopRecording();
                }
            }
        });
    }

    boolean checkPermissions() {
        if (checkSelfPermission(Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_DENIED ||
                checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
            requestPermissions(new String[]{Manifest.permission.RECORD_AUDIO, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 123);
            return false;
        }
        return true;
    }
}
