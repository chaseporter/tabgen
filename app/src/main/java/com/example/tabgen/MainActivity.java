package com.example.tabgen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

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
        AppComponent appComponent = DaggerAppComponent.create();
        appComponent.inject(this);
        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setState(appState);
        binding.setRecorder(recorder);
        super.onCreate(savedInstanceState);
    }
}
