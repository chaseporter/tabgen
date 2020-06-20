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

    /* Would like to databind here so i can bind the appstate to the views and make sure that the buttons are disabled
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
        holder.binding.setRecordingFiles(recordingFiles);
    }

    @Override
    public int getItemCount() {
        return recordingList.size();
    }

    public class RecordingViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
         RecordingListitemBinding binding;
         TextView recordingName;
         Button editButton;
         Button deleteButton;
         Button playButton;
         OnEditRecordingListener onEditRecordingListener;

         public RecordingViewHolder(@NonNull RecordingListitemBinding binding, OnEditRecordingListener onEditRecordingListener) {
             super(binding.getRoot());
             this.binding = binding;
             recordingName = itemView.findViewById(R.id.recordingTitle);
             playButton = itemView.findViewById(R.id.playButton);
             editButton = itemView.findViewById(R.id.editButton);
             deleteButton = itemView.findViewById(R.id.deleteButton);
             this.onEditRecordingListener = onEditRecordingListener;
             editButton.setOnClickListener(this);
         }

         /* This method will call the onEditRecordingListener passed from the MainActivity to open a new Activity
         * to signal process recordings. Eventually would like to remove the buttons from the default list item view
         * and use this method to inflate a new view when an element is clicked that has the three buttons for playing,
         * editing, and deleting recordings. */
         @Override
         public void onClick(View v) {
             onEditRecordingListener.onEditRecordingClick(getAdapterPosition());
         }
    }

    public interface OnEditRecordingListener {
        void onEditRecordingClick(int position);
    }
}
