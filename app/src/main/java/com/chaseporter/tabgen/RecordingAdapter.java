package com.chaseporter.tabgen;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecordingAdapter extends RecyclerView.Adapter<RecordingAdapter.RecordingViewHolder> {
    private static final String TAG = "RecordingAdapter";
    private ArrayList<String> recordingList;
    private Context context;
    private OnEditRecordingListener onEditRecordingListener;

    public RecordingAdapter(ArrayList<String> recordingList, OnEditRecordingListener onEditRecordingListener) {
        this.recordingList = recordingList;
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
         ConstraintLayout listItemLayout;
         OnEditRecordingListener onEditRecordingListener;

         public RecordingViewHolder(@NonNull View itemView, OnEditRecordingListener onEditRecordingListener) {
             super(itemView);
             recordingName = itemView.findViewById(R.id.recordingTitle);
             listItemLayout = itemView.findViewById(R.id.list_itemLayout);
             this.onEditRecordingListener = onEditRecordingListener;
             itemView.setOnClickListener(this);
         }

        @Override
        public void onClick(View v) {
            onEditRecordingListener.onEditRecordingClick(getAdapterPosition());
        }
    }

    public interface OnEditRecordingListener {
        void onEditRecordingClick(int position);
    }
}
