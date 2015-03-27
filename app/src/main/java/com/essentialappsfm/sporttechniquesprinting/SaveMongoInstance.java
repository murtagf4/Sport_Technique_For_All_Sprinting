package com.essentialappsfm.sporttechniquesprinting;

import android.os.AsyncTask;
import android.provider.Settings;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import android.provider.Settings.Secure;

/**
 * Created by Fergus on 04/03/2015.
 */
public class SaveMongoInstance extends AsyncTask<Video, Void, Boolean>
{

    @Override
    protected Boolean doInBackground(Video... arg0)
    {
        try
        {
            Video vid = arg0[0];

            DatabaseEntry db = new DatabaseEntry();

            HttpClient httpClient = new DefaultHttpClient();
            HttpPost request = new HttpPost(db.buildMongoUrl(vid.getId()));

            StringEntity params =new StringEntity(db.createVideoDetails(vid));
            request.addHeader("content-type", "application/json");
            request.setEntity(params);
            HttpResponse response = httpClient.execute(request);

            if(response.getStatusLine().getStatusCode()<205)
            {
                return true;
            }
            else
            {
                return false;
            }
        } catch (Exception e) {
            //e.getCause();
            String val = e.getMessage();
            String val2 = val;
            return false;
        }
    }
}
