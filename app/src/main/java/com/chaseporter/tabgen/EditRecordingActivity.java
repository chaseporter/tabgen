package com.chaseporter.tabgen;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.chaseporter.tabgen.databinding.ActivityEditRecordingBinding;
import com.chaseporter.tabgen.models.AppState;
import com.chaseporter.tabgen.models.RecordingFiles;

import javax.inject.Inject;

public class EditRecordingActivity extends AppCompatActivity {
    private static final String TAG = "EditRecordingActivity";
    @Inject
    AppState appState;
    @Inject
    RecordingFiles recordingFiles;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: called");
        MainApplication application = (MainApplication) getApplication();
        application.getAppComponent().inject(this);
        ActivityEditRecordingBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_edit_recording);
        binding.setState(appState);
        binding.setRecordingFiles(recordingFiles);
    }


}
