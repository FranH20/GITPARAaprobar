package com.solidgeargroup.dialogflow.dialogflow;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class DocumentoHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView titleTextView;
    public TextView descTextView;
    public ImageView imageImageView;
    OnRecyclerViewItemListener recyclerViewItemListener;


    public DocumentoHolder(@NonNull View itemView) {
        super(itemView);
        titleTextView = itemView.findViewById(R.id.title);
        descTextView = itemView.findViewById(R.id.description);
        imageImageView = itemView.findViewById(R.id.image);
        itemView.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        this.recyclerViewItemListener.onItemClick(this.getLayoutPosition());
    }

    public void setItemClickListener(OnRecyclerViewItemListener recyclerViewItemListener)
    {
        this.recyclerViewItemListener=recyclerViewItemListener;
    }
}
