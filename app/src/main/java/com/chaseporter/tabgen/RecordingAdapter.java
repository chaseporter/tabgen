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

    public RecordingAdapter(Context context, ArrayList<String> recordingList) {
        this.context = context;
        this.recordingList = recordingList;
    }

    @NonNull
    @Override
    public RecordingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recording_listitem, parent, false);
        RecordingViewHolder holder = new RecordingViewHolder(view);
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

    public class RecordingViewHolder extends RecyclerView.ViewHolder {

         TextView recordingName;
         ConstraintLayout listItemLayout;

         public RecordingViewHolder(@NonNull View itemView) {
             super(itemView);
             recordingName = itemView.findViewById(R.id.recordingTitle);
             listItemLayout = itemView.findViewById(R.id.list_itemLayout);
         }
     }
}
