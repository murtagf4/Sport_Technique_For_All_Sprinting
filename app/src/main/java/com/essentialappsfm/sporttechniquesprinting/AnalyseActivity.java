package com.essentialappsfm.sporttechniquesprinting;

import android.app.Activity;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
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
    private VideoView videoDisplay;
    private TextView timeText;
    private SeekBar seekbar;
    private ImageButton controlButton;
    private FrameLayout frameLayout;
    private DrawingSurface drawSurface;
    SurfaceHolder holder;
    int buttonClick = 0;
    private static final String TAG = "Tag";

    Tools tool;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.camera_view);

        videoDisplay = (VideoView) findViewById(R.id.videoView);
        videoDisplay.setOnPreparedListener(prepare);
        videoDisplay.setOnCompletionListener(complete);
        userVid = getIntent().getData();
        videoDisplay.setVideoURI(userVid);
        controlButton = (ImageButton) findViewById(R.id.play_button);
        timeText = (TextView) findViewById(R.id.textviewTime);
        seekbar = (SeekBar) findViewById(R.id.seekbarMain);
        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                videoDisplay.seekTo(progress);
                timeText.setText(String.valueOf(videoDisplay.getCurrentPosition() / 1000 + "." + videoDisplay.getCurrentPosition() % 1000));
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
            seekbar.postDelayed(increment, 1000);
        }
    };

    MediaPlayer.OnCompletionListener complete = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mp)
        {
            controlButton.setImageResource(R.drawable.ic_action_play);
            seekbar.setProgress(0);
        }
    };

    private Runnable increment = new Runnable()
    {
        @Override
        public void run()
        {
            seekbar.setProgress(videoDisplay.getCurrentPosition());
            seekbar.setMax(videoDisplay.getDuration());
            seekbar.postDelayed(increment, 1000);
        }
    };

    public void playPause(View view)
    {
        tool = new Tools();
        tool.videoControl(videoDisplay, controlButton);
    }

    public void startDrawing(View v)    {
        buttonClick++;
        frameLayout = (FrameLayout) findViewById(R.id.videoFrame);

        if(videoDisplay.isPlaying())     {
            Toast.makeText(getApplicationContext(), "Video must be paused to use Drawing tools", Toast.LENGTH_SHORT).show();
        }
        {
            if(buttonClick%2 == 0){
                frameLayout.removeView(drawSurface);
            }
            else{
                drawSurface = new DrawingSurface(this);
                holder = drawSurface.getHolder();
                holder.setFormat(PixelFormat.TRANSPARENT);
                drawSurface.setZOrderOnTop(true);
                frameLayout.addView(drawSurface);
            }
        }
        Log.d(TAG, "Buttonclick = " + buttonClick);
    }

    public void advanceSide(View view)
    {
        Intent i = new Intent(this, ComparisonActivity.class);
        String userID = getIntent().getExtras().getString("userData");
        i.setData(userVid);
        i.putExtra("id", userID);
        //Toast.makeText(getApplicationContext(),"User = " + userID, Toast.LENGTH_SHORT).show();
        startActivity(i);
    }

    public void returnHome(View view){
        Intent i = new Intent(this, HomeActivity.class);
        startActivity(i);
    }
}