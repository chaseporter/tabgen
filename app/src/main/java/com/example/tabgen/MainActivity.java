package com.example.tabgen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.util.Log;

import com.example.tabgen.databinding.ActivityMainBinding;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    @Inject
    AppState appState;

    @Inject
    Recorder recorder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        binding.setState(appState);
        binding.setRecorder(recorder);
        Log.d(TAG, "onCreate: " + Boolean.toString(appState.isReady()));
    }
}
