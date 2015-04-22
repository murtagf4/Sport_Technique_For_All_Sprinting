package com.essentialappsfm.sporttechniquesprinting;

import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.MediaController;
import android.widget.VideoView;


public class WatchActivity extends ActionBarActivity {

    VideoView video;
    Uri videoUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watch);

        video = (VideoView) findViewById(R.id.videoWatch);
        MediaController mediaController = new MediaController(this);
        //mediaController.setAnchorView(video);
        videoUri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.tutorial_vid);
        video.setMediaController(mediaController);
        video.setVideoURI(videoUri);
        video.start();

    }
}
