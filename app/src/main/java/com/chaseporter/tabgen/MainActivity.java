package com.chaseporter.tabgen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.chaseporter.tabgen.models.AppState;
import com.chaseporter.tabgen.models.Recorder;
import com.chaseporter.tabgen.databinding.ActivityMainBinding;
import com.chaseporter.tabgen.models.RecordingFiles;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import javax.inject.Inject;

/**
 * For now this is the only ActivityFragment that I will have. For the basics of the app just need a
 * record/stop button and a listView to show recordings. Later on, as features expand out, will need
 * to add more Fragments for tasks that involve signal processing.
 */
public class MainActivity extends AppCompatActivity implements RecordingAdapter.OnEditRecordingListener {
    private static final String TAG = "MainActivity";
    @Inject
    AppState appState;
    @Inject
    Recorder recorder;
    @Inject
    RecordingFiles recordingFiles;

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
        initRecyclerView();
    }

    private void initRecyclerView() {
        Log.d(TAG, "initRecyclerView: init recyclerviewer.");
        RecyclerView recyclerView = findViewById(R.id.recordingList);
        RecordingAdapter recordingAdapter = new RecordingAdapter(recordingFiles, appState, this);
        recyclerView.setAdapter(recordingAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
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

    /* Main Activity implements the interface OnEditRecordingListener so that this method can be provided to the adapter.
    * When the EditRecording button is clicked, this method will be called which will open a new Activity. */
    @Override
    public void onEditRecordingClick(int position) {
        recordingFiles.setEditing(position);
        Intent intent = new Intent(this, EditRecordingActivity.class);
        startActivity(intent);
    }
}
