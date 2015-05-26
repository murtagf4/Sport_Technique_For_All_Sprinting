package com.essentialappsfm.sporttechniquesprinting;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Parcel;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class VideoDisplay extends ActionBarActivity
{
    private ArrayList<Video> mongoValues;
    private List<String> videos;
    private ArrayAdapter<String> adapter;
    private ListView list;

    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_display);

        mongoValues = new ArrayList<>();
        videos = new ArrayList<>();
        list = (ListView) findViewById(R.id.listView);
        fillList();
        populateListView();
        registerPress();
    }

    private void fillList() {
        //Get your cloud contacts
        Intent iget = getIntent();
        userID = iget.getExtras().getString("userAccount");

        Video vid1 = new Video();
        if(userID != null)
            vid1.id = userID;
        else
            vid1.id = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);

        GetMongoInstance task = new GetMongoInstance();
        try {
            mongoValues = task.execute(vid1).get();
        } catch (Exception e) {
            e.printStackTrace();
        }

        for(Video x: mongoValues){
            String path = x.getVidPath();
            videos.add(path);
        }
    }

    private void populateListView() {

        // Build Adapter
        adapter = new VideoAdapter();

        // Configure ListView
        list.setAdapter(adapter);

        if(list.getCount() == 0){
            Toast.makeText(getApplicationContext(), "There are no Videos to show", Toast.LENGTH_SHORT).show();
        }
    }

    private class VideoAdapter extends ArrayAdapter<String> {
        public VideoAdapter() {
            // (context of activity, views on screen, content for views)
            super(VideoDisplay.this, R.layout.list_item, videos);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // Make sure we have a view to work with (may have been given null)
            View itemView = convertView;

            if (itemView == null) {
                itemView = getLayoutInflater().inflate(R.layout.list_item, parent, false);
            }

            // Find the option to work with
            String currentVideo = videos.get(position);

            int indexStart = currentVideo.lastIndexOf("/");
            int indexEnd = currentVideo.lastIndexOf(".");
            String fileName = currentVideo.substring(indexStart + 1, indexEnd);

            // ItemText
            TextView vidText = (TextView) itemView.findViewById(R.id.vidText);
            vidText.setText(fileName);

            return itemView;
        }
    }

    private void registerPress() {
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View viewClicked, int position, long id) {
                String pressed = videos.get(position);
                String message = pressed + " pressed";
                Uri userFile = Uri.parse(pressed);
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();

                String uriCheck = getIntent().getExtras().getString("vidUri");

                Intent setVideo;
                if(uriCheck != null) {
                    setVideo = new Intent();
                    setVideo.setData(userFile);
                    setResult(RESULT_OK, setVideo);
                    finish();
                } else{
                    setVideo = new Intent(getApplicationContext(), AnalyseActivity.class);
                    setVideo.setData(userFile);
                    setVideo.putExtra("userData", userID);
                    startActivity(setVideo);
                }
            }
        });
    }
}