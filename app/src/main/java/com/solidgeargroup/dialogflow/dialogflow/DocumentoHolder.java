package com.solidgeargroup.dialogflow.dialogflow;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class DocumentoHolder extends RecyclerView.ViewHolder {

    public TextView titleTextView;
    public TextView descTextView;
    public ImageView imageImageView;

    public DocumentoHolder(@NonNull View itemView) {
        super(itemView);
        titleTextView = itemView.findViewById(R.id.title);
        descTextView = itemView.findViewById(R.id.description);
        imageImageView = itemView.findViewById(R.id.image);
    }
}
