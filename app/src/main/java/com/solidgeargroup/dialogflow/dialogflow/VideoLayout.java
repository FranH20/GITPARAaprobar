package com.solidgeargroup.dialogflow.dialogflow;

import android.app.PictureInPictureParams;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Rational;
import android.widget.MediaController;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;


public class VideoLayout extends AppCompatActivity {
    VideoView videoView;
    CardView card ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_layout);
        //video
        videoView = (VideoView) findViewById(R.id.video);
        Uri path = Uri.parse("android.resource://com.solidgeargroup.dialogflow.dialogflow/"+ R.raw.yo);
        videoView.setMediaController(new MediaController(this));
        videoView.setVideoURI(path);
        videoView.requestFocus();
        videoView.start();
    }

    private void pictureInPictureMode(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){

            Rational aspectRatio = new Rational(192,108);
            PictureInPictureParams.Builder mPicture = new PictureInPictureParams.Builder();
            mPicture.setAspectRatio(aspectRatio).build();
            enterPictureInPictureMode(mPicture.build());
        }
    }

    void minimizar()
    {
        videoView.setMediaController(null);
        pictureInPictureMode();
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        minimizar();

    }

}
