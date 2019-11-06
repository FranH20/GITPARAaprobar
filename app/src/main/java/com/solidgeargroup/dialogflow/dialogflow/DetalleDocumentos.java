package com.solidgeargroup.dialogflow.dialogflow;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import com.squareup.picasso.Picasso;

public class DetalleDocumentos extends AppCompatActivity {

    private Documento documento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_documentos);

        //GET INTENT
        Intent i=this.getIntent();

        //RECEIVE DATA
        String Titulo = i.getExtras().getString("NAME_KEY");
        String Descripcion = i.getExtras().getString("DESC_KEY");
        String UrlImagen = i.getExtras().getString("IMAG_KEY");

        TextView titleTextView = this.findViewById(R.id.title);
        TextView descTextView = this.findViewById(R.id.description);
        ImageView imageImageView = this.findViewById(R.id.image);

        titleTextView.setText(Titulo);
        descTextView.setText(Descripcion);

        Picasso.with(this).load(UrlImagen).fit().centerInside().into(imageImageView);

        //video
        VideoView videoView = (VideoView) findViewById(R.id.videodetalle);
        Uri path = Uri.parse("android.resource://com.solidgeargroup.dialogflow.dialogflow/"+R.raw.videodetalle);
        videoView.setMediaController(new MediaController(this));
        videoView.setVideoURI(path);
        videoView.requestFocus();
        videoView.start();
        //

    }

    public void onclic_universidad(View v)
    {
        Intent i = new Intent(this, PDF_webview.class);
        startActivity(i);
    }
}
