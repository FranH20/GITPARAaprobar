package com.solidgeargroup.dialogflow.dialogflow;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
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
    ActionBar actionBar;

    boolean isMenuOpen = false;

    FloatingActionButton multiple_actions;
    FloatingActionButton mic;
    FloatingActionButton mic2;
    FloatingActionButton share;
    FloatingActionButton phone;
    FloatingActionButton whatsapp;
    FloatingActionButton video;

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

        multiple_actions = findViewById(R.id.multiple_actions);
        mic = findViewById(R.id.micButton);
        mic2 = findViewById(R.id.micButton2);
        share = findViewById(R.id.share);
        phone = findViewById(R.id.phone);
        whatsapp = findViewById(R.id.whastapp);
        video = findViewById(R.id.verVideo);

        multiple_actions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menuOpen();
            }
        });

    }

    public void menuOpen(){
        if (!isMenuOpen){
            mic.animate().translationY(-getResources().getDimension(R.dimen.mic));
            mic2.animate().translationY(-getResources().getDimension(R.dimen.mic2));
            share.animate().translationY(-getResources().getDimension(R.dimen.share));
            phone.animate().translationY(-getResources().getDimension(R.dimen.phone));
            whatsapp.animate().translationY(-getResources().getDimension(R.dimen.whatsapp));
            video.animate().translationY(-getResources().getDimension(R.dimen.video));

            isMenuOpen = true;
        }else{
            mic.animate().translationY(0);
            mic2.animate().translationY(0);
            share.animate().translationY(0);
            phone.animate().translationY(0);
            whatsapp.animate().translationY(0);
            video.animate().translationY(0);

            isMenuOpen = false;
        }
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

    public void onclic_Share (View v)
    {
        Intent mi_share = new Intent(Intent.ACTION_SEND);
        mi_share.setType("text/plain");
        String shareBody = "Descarga Aqui --> https://mega.nz/#F!YvZFASpZ!5IC_loxK9EG5x0H2cHgRTA";
        String shareSub = "Your Subject here";
        mi_share.putExtra(Intent.EXTRA_SUBJECT,shareBody);
        mi_share.putExtra(Intent.EXTRA_TEXT,shareBody);
        startActivity(Intent.createChooser(mi_share, "Compartir Aplicacion"));
    }

    public void onclicPhone (View v )
    {
        String dialString = "960859674";
        Intent i = new Intent (Intent.ACTION_DIAL, Uri.parse ("tel:" + Uri.encode (dialString)));
        startActivity (i);
    }

    public void onclicWhastapp (View v )
    {
        final String[] nombre = new String[99];

        //INICIO ALERT DIALOG NRO 2 //-----------------------------------------------------------------------------
        Resources res = getResources();
        final String[] facultad = res.getStringArray(R.array.facultad);
        AlertDialog.Builder midialog = new AlertDialog.Builder(this);
        midialog.setTitle("FACULTAD");
        midialog.setIcon(R.drawable.logo);

        midialog.setSingleChoiceItems(R.array.facultad, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                nombre[4] = facultad[i];
            }
        });

        //if positivo
        midialog.setPositiveButton("Finalizar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //String txt = weigtInput.getText().toString();
                nombre[1] =  nombre[4];
                //Toast.makeText(getApplicationContext(),txt,Toast.LENGTH_SHORT).show();
                if (!(nombre[0]).equals("") && !(nombre[1]).equals("")&& !(nombre[3]).equals(""))
                {
                    Uri uri = Uri.parse("https://api.whatsapp.com/send?phone=51952392311&text=Hola , Mi nombre es : " +
                            nombre[0] + " mi codigo : " + nombre[3]  + " , pertenezco a la facultad de : " + nombre[4] + "%20");
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent);
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Lo siento , dejaste en vacio un formulario .. ='(",Toast.LENGTH_SHORT).show();
                }
            }
        });
        //negativo
        midialog.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        midialog.show();
        //FIN ALERT DIALOG NRO 2 //-----------------------------------------------------------------------------

        //INICIO ALERT DIALOG NRO 1 //-----------------------------------------------------------------------------
        AlertDialog.Builder midialog3 = new AlertDialog.Builder(this);
        midialog3.setTitle("TU CODIGO");
        midialog3.setIcon(R.drawable.logo);
        midialog3.setMessage("Escribe tu codigo universitario");

        final EditText weigtInput3 = new EditText (this);
        weigtInput3.setInputType(InputType.TYPE_CLASS_NUMBER);
        midialog3.setView(weigtInput3);
        //if positivo
        midialog3.setPositiveButton("Siguiente", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //String txt = weigtInput.getText().toString();
                nombre[3] = weigtInput3.getText().toString();
                //Toast.makeText(getApplicationContext(),txt,Toast.LENGTH_SHORT).show();
            }
        });

        //negativo
        midialog.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        midialog3.show();
        //FIN ALERT DIALOG NRO 1 //-----------------------------------------------------------------------------

        //INICIO ALERT DIALOG NRO 1 //-----------------------------------------------------------------------------
        AlertDialog.Builder midialog2 = new AlertDialog.Builder(this);
        midialog2.setTitle("TU NOMBRE");
        midialog2.setIcon(R.drawable.logo);
        midialog2.setMessage("Escribe tu nombre completo");

        final EditText weigtInput2 = new EditText (this);

        weigtInput2.setInputType(InputType.TYPE_CLASS_TEXT);
        midialog2.setView(weigtInput2);

        //if positivo
        midialog2.setPositiveButton("Siguiente", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //String txt = weigtInput.getText().toString();
                nombre[0] = weigtInput2.getText().toString();
                //Toast.makeText(getApplicationContext(),txt,Toast.LENGTH_SHORT).show();
            }
        });

        //negativo
        midialog.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        midialog2.show();
        //FIN ALERT DIALOG NRO 1 //-----------------------------------------------------------------------------
    }
}
