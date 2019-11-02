package com.solidgeargroup.dialogflow.dialogflow;

import androidx.appcompat.app.AppCompatActivity;

import android.app.PictureInPictureParams;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Rational;
import android.widget.MediaController;
import android.widget.VideoView;

public class VideoLayout extends AppCompatActivity {

    VideoView videoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_layout);

        //video
        videoView = (VideoView) findViewById(R.id.video);
        Uri path = Uri.parse("android.resource://com.solidgeargroup.dialogflow.dialogflow/"+R.raw.yo);
        videoView.setMediaController(new MediaController(this));
        videoView.setVideoURI(path);
        videoView.requestFocus();
        videoView.start();
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        videoView.setMediaController(null);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            Rational aspectRatio = new Rational(192,108);
            PictureInPictureParams.Builder mPicture = new PictureInPictureParams.Builder();
            mPicture.setAspectRatio(aspectRatio).build();
            enterPictureInPictureMode(mPicture.build());
        }

    }

    @Override
    public void onStop() {
        super.onStop();
        finish();
    }

}
