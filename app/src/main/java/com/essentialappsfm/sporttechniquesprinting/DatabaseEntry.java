package com.essentialappsfm.sporttechniquesprinting;

/**
 * Created by Fergus on 04/03/2015.
 */
public class DatabaseEntry
{

    public String getDatabaseName()
    {
        return "sprinting_data";
    }

    // base of the URL for connecting to mongo
    public String getMainUrl()
    {
        return "https://api.mongolab.com/api/1/databases/"+getDatabaseName()+"/collections/";
    }

    // generates api section of connection URL with own unique API key
    public String apiUrl()
    {
        return "?apiKey=KX3rU3XgsGmJ0KqMoLjl20UOUo1-gp0y";
    }

    // generate full URL for connecting to specific mongo instance for each unique device
    public String buildMongoUrl(String deviceId)
    {
        return getMainUrl() + deviceId + apiUrl();
    }

    // foramts the details of each video to be shown
    public String createVideoDetails(Video video)
    {
        return String.format("{\"password\": \"%s\", "
                        + "\"vidPath\": \"%s\"}",
                video.password, video.vidPath);
    }
}