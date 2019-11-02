package com.solidgeargroup.dialogflow.dialogflow;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.ArrayList;

public class AdaptadorFirebase extends RecyclerView.Adapter<DocumentoHolder>{

    Context c;
    ArrayList<Documento> documentos;

    public AdaptadorFirebase(Context c, ArrayList<Documento> documentos) {
        this.c = c;
        this.documentos = documentos;
    }

    @NonNull
    @Override
    public DocumentoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, null);
        return new DocumentoHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DocumentoHolder holder, int position) {
        final  Documento doc = documentos.get(position);
        holder.titleTextView.setText(documentos.get(position).getTitulo());
        holder.descTextView.setText(documentos.get(position).getDescripcion());
        String imageUrl = documentos.get(position).getUrlImagen();
        Picasso.with(c).load(imageUrl).fit().centerInside().into(holder.imageImageView);

        holder.setItemClickListener(new OnRecyclerViewItemListener() {
            @Override
            public void onItemClick(int pos) {

                Intent intent = new Intent(c, DetalleDocumentos.class);
                intent.putExtra("NAME_KEY",doc.getTitulo());
                intent.putExtra("DESC_KEY",doc.getDescripcion());
                intent.putExtra("IMAG_KEY",doc.getUrlImagen());
                c.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return documentos.size();
    }
}
