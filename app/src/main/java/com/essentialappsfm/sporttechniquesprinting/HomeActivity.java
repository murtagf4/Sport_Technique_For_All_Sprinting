package com.essentialappsfm.sporttechniquesprinting;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class HomeActivity extends ActionBarActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_view);
    }

    public void startSession(View view)
    {
        Intent intent1 = new Intent(this, PreviewActivity.class);
        startActivity(intent1);
    }

    public void showVideos(View view)
    {
        Intent intent2 = new Intent(this, VideoDisplay.class);
        startActivity(intent2);
    }

    public void showHelp(View view)
    {
        Intent intent3 = new Intent(this, HelpScreen.class);
        startActivity(intent3);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

}
