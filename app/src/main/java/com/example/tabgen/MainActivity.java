package com.example.tabgen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;

import com.example.tabgen.databinding.ActivityMainBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import javax.inject.Inject;

/**
 * For now this is the only ActivityFragment that I will have. For the basics of the app just need a
 * record/stop button and a listView to show recordings. Later on, as features expand out, will need
 * to add more Fragments for tasks that involve signal processing.
 */
public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    @Inject
    AppState appState;
    @Inject
    Recorder recorder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppComponent appComponent = DaggerAppComponent.builder().appModule(new AppModule(getExternalFilesDir(null).getAbsolutePath())).build();
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
                        recorder.startRecording();
                    }
                } else if (appState.isRecording()) {
                    recorder.stopRecording();
                }
            }
        });
    }

    /* Function to check permissions and ask for them if needed. Needs RECORD_AUDIO and WRITE_EXTERNAL_STORAGE permission */
    boolean checkPermissions() {
        if (checkSelfPermission(Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_DENIED ||
                checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
            requestPermissions(new String[]{Manifest.permission.RECORD_AUDIO, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 123);
            return false;
        }
        return true;
    }
}
