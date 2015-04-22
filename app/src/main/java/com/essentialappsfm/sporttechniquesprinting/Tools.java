package com.essentialappsfm.sporttechniquesprinting;

import android.widget.ImageButton;
import android.widget.VideoView;

/**
 * Created by Fergus on 22/04/2015.
 */
public class Tools
{
    boolean isPlaying = true;

    public void videoControl(VideoView vid, ImageButton button)
    {
        if(vid.isPlaying())
        {
            button.setImageResource(R.drawable.ic_action_play);
            vid.pause();
        }
        else
        {
            button.setImageResource(R.drawable.ic_action_pause);
            vid.start();
        }
        isPlaying = !isPlaying;
    }
}
