package com.essentialappsfm.sporttechniquesprinting;

/**
 * Created by Fergus on 25/02/2015.
 */
public class Video
{
    String id;
    public String user;
    public String vidPath;

    public String getId() {
        return id;
    }

    public void setId(String user) {
        this.user = id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
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
        return "Video [User=" + user + ", vidPath=" + vidPath + "]";
    }
}
