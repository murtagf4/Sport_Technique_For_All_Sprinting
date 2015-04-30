package com.essentialappsfm.sporttechniquesprinting;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class HelpScreen extends ActionBarActivity
{
    Button watchButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_screen);

        watchButton = (Button) findViewById(R.id.videoButton);
    }

    public void watchVideo(View view){
        Intent i = new Intent(this, WatchActivity.class);
        startActivity(i);
    }
}
