package com.essentialappsfm.sporttechniquesprinting;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class HomeActivity extends ActionBarActivity
{
    TextView userMessage;
    String dataCreate;

    private static final int LOGIN_REQUEST_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_view);
        userMessage = (TextView) findViewById(R.id.welcomeText);
        userMessage.setText("Welcome User");
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

    public void handleLogin(View view)
    {
        Intent intent4 = new Intent(this, LoginActivity.class);
        startActivityForResult(intent4, LOGIN_REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        dataCreate = data.getExtras().getString("createUser");
        userMessage.setText("Welcome " + dataCreate);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

}
