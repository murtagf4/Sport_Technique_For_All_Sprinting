package com.essentialappsfm.sporttechniquesprinting;

import android.app.Activity;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SurfaceHolder;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;


public class AnalyseActivity extends Activity {

    Uri userVid;
    private VideoView vidDisplay;
    private TextView textTime;
    private SeekBar seek;
    private ImageButton control;
    private FrameLayout layoutFrame;
    private DrawingSurface drawSurface;
    SurfaceHolder holder;
    int buttonClick = 0;
    private static final String TAG = "Tag";

    CameraActivity cam;
    boolean isPlaying = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.camera_view);

        /*userVid = getIntent().getData();
        String vidText = userVid.toString();
        textView = (TextView) findViewById(R.id.testView);
        textView.setText(vidText);*/

        vidDisplay = (VideoView) findViewById(R.id.videoView);
        vidDisplay.setOnPreparedListener(prepare);
        vidDisplay.setOnCompletionListener(complete);
        userVid = getIntent().getData();
        vidDisplay.setVideoURI(userVid);
        control = (ImageButton) findViewById(R.id.play_button);
        textTime = (TextView) findViewById(R.id.textviewTime);
        seek = (SeekBar) findViewById(R.id.seekbarMain);
        seek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                vidDisplay.seekTo(progress);
                textTime.setText(String.valueOf(vidDisplay.getCurrentPosition() / 1000 + "." + vidDisplay.getCurrentPosition() % 1000));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

    }

    MediaPlayer.OnPreparedListener prepare = new MediaPlayer.OnPreparedListener()
    {
        @Override
        public void onPrepared(MediaPlayer mp)
        {
            mp.setVolume(0f, 0f);
            seek.postDelayed(increment, 1000);
        }
    };

    MediaPlayer.OnCompletionListener complete = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mp)
        {
            control.setImageResource(R.drawable.ic_action_play);
            seek.setProgress(0);
        }
    };

    private Runnable increment = new Runnable()
    {
        @Override
        public void run()
        {
            seek.setProgress(vidDisplay.getCurrentPosition());
            seek.setMax(vidDisplay.getDuration());
            seek.postDelayed(increment, 1000);
        }
    };

    public void playPause(View view)
    {
        cam = new CameraActivity();
        cam.videoControl(vidDisplay, control);
    }

    public void startDrawing(View v)    {
        buttonClick++;
        layoutFrame = (FrameLayout) findViewById(R.id.videoFrame);

        if(vidDisplay.isPlaying())     {
            Toast.makeText(getApplicationContext(), "Video must be paused to use Drawing tools", Toast.LENGTH_SHORT).show();
        }
        {
            if(buttonClick%2 == 0){
                layoutFrame.removeView(drawSurface);
            }
            else{
                drawSurface = new DrawingSurface(this);
                holder = drawSurface.getHolder();
                holder.setFormat(PixelFormat.TRANSPARENT);
                drawSurface.setZOrderOnTop(true);
                layoutFrame.addView(drawSurface);
            }
        }
        Log.d(TAG, "Buttonclick = " + buttonClick);
    }

    public void advanceSide(View view)
    {
        Intent i = new Intent(this, ComparisonActivity.class);
        i.setData(userVid);
        startActivity(i);
    }
}

