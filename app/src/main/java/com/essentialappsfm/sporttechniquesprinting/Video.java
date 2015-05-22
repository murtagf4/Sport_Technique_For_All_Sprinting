package com.essentialappsfm.sporttechniquesprinting;

/**
 * Created by Fergus on 25/02/2015.
 */
public class Video
{
    String id;
    public String password;
    public String vidPath;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getVidPath() {
        return vidPath;
    }

    public void setVidPath(String vidPath) {
        this.vidPath = vidPath;
    }

    @Override
    public String toString()
    {
        return "Video [password=" + password + ", vidPath=" + vidPath + "]";
    }
}