package com.chaseporter.tabgen;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.Observable;
import androidx.recyclerview.widget.RecyclerView;

import com.chaseporter.tabgen.databinding.RecordingListitemBinding;
import com.chaseporter.tabgen.models.AppState;
import com.chaseporter.tabgen.models.RecordingFiles;

import java.util.ArrayList;

/**
 * This is the Adapter for the RecyclerView that shows the Recordings made so far.
 */
public class RecordingAdapter extends RecyclerView.Adapter<RecordingAdapter.RecordingViewHolder> {
    private static final String TAG = "RecordingAdapter";
    private ArrayList<String> recordingList;
    private RecordingFiles recordingFiles;
    private AppState appState;
    private OnEditRecordingListener onEditRecordingListener;

    public RecordingAdapter(RecordingFiles recordingFiles, AppState appState, OnEditRecordingListener onEditRecordingListener) {
        this.recordingFiles = recordingFiles;
        this.appState = appState;
        this.recordingList = recordingFiles.getRecordingList();
        this.onEditRecordingListener = onEditRecordingListener;
        Observable.OnPropertyChangedCallback callback = new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                if (propertyId == BR.currentState || propertyId == BR.recordingFiles) {
                    notifyDataSetChanged();
                }
            }
        };
        recordingFiles.addOnPropertyChangedCallback(callback);
        appState.addOnPropertyChangedCallback(callback);
    }

    /* Implented databind here so I can bind the AppState to the views and make sure that the buttons are disabled
     * when the app is not in READY state. Could also bind the recordingFiles and use the xml for click events rather than
     * making onClickListeners.
     */
    @NonNull
    @Override
    public RecordingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        RecordingListitemBinding binding = DataBindingUtil.inflate(inflater, R.layout.recording_listitem, parent, false);
        binding.setState(appState);
        return new RecordingViewHolder(binding, onEditRecordingListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RecordingViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: called");
        holder.recordingName.setText(recordingList.get(position));
        holder.binding.setIndex(position);
        holder.binding.setState(appState);
        holder.bindRecordingFiles(recordingFiles);
        holder.bindListener();
    }

    @Override
    public int getItemCount() {
        return recordingList.size();
    }

    public class RecordingViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
         RecordingListitemBinding binding;
         TextView recordingName;
         View expandedView;
         OnEditRecordingListener onEditRecordingListener;

         public RecordingViewHolder(@NonNull RecordingListitemBinding binding, final OnEditRecordingListener onEditRecordingListener) {
             super(binding.getRoot());
             this.binding = binding;
             binding.getRoot().setOnClickListener(this);
             recordingName = itemView.findViewById(R.id.recordingTitle);
             expandedView = itemView.findViewById(R.id.expandedListItem);
             this.onEditRecordingListener = onEditRecordingListener;
         }

         /* This will select this file to expand its view. */
         @Override
         public void onClick(View v) {
             recordingFiles.selectFile(getAdapterPosition());
         }

         private void bindRecordingFiles(RecordingFiles recordingFiles) {
             binding.setRecordingFiles(recordingFiles);
             boolean expanded = recordingFiles.getSelectedFile() == getAdapterPosition();
             expandedView.setVisibility(expanded ? View.VISIBLE : View.GONE);
         }

         private void bindListener() {
             binding.setListener(this.onEditRecordingListener);
         }
    }

    public interface OnEditRecordingListener {
        void onEditRecordingClick(int position);
    }
}
