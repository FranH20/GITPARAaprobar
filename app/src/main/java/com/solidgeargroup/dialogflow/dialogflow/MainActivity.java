package com.solidgeargroup.dialogflow.dialogflow;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import ai.api.AIListener;
import ai.api.android.AIConfiguration;
import ai.api.android.AIService;
import ai.api.model.AIError;
import ai.api.model.AIResponse;
import ai.api.model.Result;

public class MainActivity extends AppCompatActivity implements AIListener {

    RecyclerView recyclerView;
    ArrayList<Documento> documentos;
    DatabaseReference databaseReference;
    AdaptadorFirebase miAdaptador;
    private TextToSpeech mTextToSpeech;
    private AIService mAIService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.my_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        documentos = new ArrayList<Documento>();

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Documento");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds: dataSnapshot.getChildren()) {
                    String titulo = ds.child("titulo").getValue().toString();
                    String url = ds.child("imagen").getValue().toString();
                    String descripcion = ds.child("descripcion").getValue().toString();
                    Documento d = new Documento(titulo,descripcion,url);
                    documentos.add(d);
                }
                miAdaptador = new AdaptadorFirebase(MainActivity.this, documentos);
                recyclerView.setAdapter(miAdaptador);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(MainActivity.this, "Opss ... un error ocurrio", Toast.LENGTH_SHORT).show();;
            }
        });


        final AIConfiguration config = new AIConfiguration("501c31bce1704e4d9f61998b74442d0f",
                AIConfiguration.SupportedLanguages.Spanish,
                AIConfiguration.RecognitionEngine.System);
        mAIService = AIService.getService(this, config);
        mAIService.setListener(this);
        mTextToSpeech = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {

            }
        });

        findViewById(R.id.micButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //INICIA EL SERVICIO
                mAIService.startListening();
                //android.os.Process.killProcess(android.os.Process.myPid());
                //android.os.Process.killProcess(Process.THREAD_PRIORITY_URGENT_AUDIO);
            }
        });



    }


    @Override
    public void onResult(AIResponse response) {
        Result result = response.getResult();
        mTextToSpeech.speak(result.getFulfillment().getSpeech(), TextToSpeech.QUEUE_FLUSH, null, null);

    }

    @Override
    public void onError(AIError error) {

    }

    @Override
    public void onAudioLevel(float level) {

    }

    @Override
    public void onListeningStarted() {

    }

    @Override
    public void onListeningCanceled() {

    }

    @Override
    public void onListeningFinished() {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        android.os.Process.killProcess(android.os.Process.myPid());
    }

    //INICIO VIDEO FLOTANTE
    ActionBar actionBar;
    @Override
    public void onPictureInPictureModeChanged(boolean isInPictureInPictureMode, Configuration newConfig) {
        if (isInPictureInPictureMode){
            actionBar.hide();
        }else {
            actionBar.show();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    public void VerVideo(View v) {
        Intent i = new Intent(this, VideoLayout.class);
        startActivity(i);
    }
}
