package com.essentialappsfm.sporttechniquesprinting;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class SplashScreen extends Activity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Thread splashTimer = new Thread()
        {
            public void run()
            {
                try
                {
                    sleep(4000);
                    Intent mainIntent = new Intent("com.essentialappsfm.sporttechniqueforallsprinting.HomeActivity");
                    startActivity(mainIntent);
                }
                catch(InterruptedException e)
                {
                    e.printStackTrace();
                }
                finally
                {
                    finish();
                }
            }
        };
        splashTimer.start();
    }
}