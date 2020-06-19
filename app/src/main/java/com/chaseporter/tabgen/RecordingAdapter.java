package com.chaseporter.tabgen;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.chaseporter.tabgen.models.RecordingFiles;

import java.util.ArrayList;

/**
 * This is the Adapter for the RecyclerView that shows the Recordings made so far.
 */
public class RecordingAdapter extends RecyclerView.Adapter<RecordingAdapter.RecordingViewHolder> {
    private static final String TAG = "RecordingAdapter";
    private ArrayList<String> recordingList;
    private RecordingFiles recordingFiles;
    private OnEditRecordingListener onEditRecordingListener;

    public RecordingAdapter(RecordingFiles recordingFiles, OnEditRecordingListener onEditRecordingListener) {
        this.recordingFiles = recordingFiles;
        this.recordingList = recordingFiles.getRecordingList();
        this.onEditRecordingListener = onEditRecordingListener;
    }

    @NonNull
    @Override
    public RecordingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recording_listitem, parent, false);
        RecordingViewHolder holder = new RecordingViewHolder(view, onEditRecordingListener);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecordingViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: called");
        holder.recordingName.setText(recordingList.get(position));

    }

    @Override
    public int getItemCount() {
        return recordingList.size();
    }

    public class RecordingViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
         TextView recordingName;
         Button editButton;
         Button deleteButton;
         ConstraintLayout listItemLayout;
         OnEditRecordingListener onEditRecordingListener;

         public RecordingViewHolder(@NonNull View itemView, OnEditRecordingListener onEditRecordingListener) {
             super(itemView);
             recordingName = itemView.findViewById(R.id.recordingTitle);
             editButton = itemView.findViewById(R.id.editButton);
             deleteButton = itemView.findViewById(R.id.deleteButton);
             listItemLayout = itemView.findViewById(R.id.list_itemLayout);
             this.onEditRecordingListener = onEditRecordingListener;
             editButton.setOnClickListener(this);
             deleteButton.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {
                     if (recordingFiles.deleteFile(getAdapterPosition())) notifyDataSetChanged();
                 }
             });
         }

         /* This method will call the onEditRecordingListener passed from the MainActivity to open a new Activity
         * to signal process recordings. */
         @Override
         public void onClick(View v) {
             onEditRecordingListener.onEditRecordingClick(getAdapterPosition());
         }
    }

    public interface OnEditRecordingListener {
        void onEditRecordingClick(int position);
    }
}
