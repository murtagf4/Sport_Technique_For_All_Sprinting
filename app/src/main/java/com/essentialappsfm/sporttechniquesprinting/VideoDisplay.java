package com.essentialappsfm.sporttechniquesprinting;

import android.app.ListActivity;
import android.content.Intent;
import android.provider.Settings;
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

public class VideoDisplay extends ListActivity
{
    /*ArrayList<Video> mongoValues;
    private List<String> videos;
    ArrayAdapter<String> adapter;
    ListView list;

    DatabaseEntry db;
    String mongo_url;
    String user;*/

    ArrayList<Video> returnValues = new ArrayList<Video>();
    ArrayList<String> listItems = new ArrayList<String>();
    String valueTOUpdate_id;
    String valueTOUpdate_fname;
    String valueTOUpdate_lname;
    String valueTOUpdate_phone;
    String valueTOUpdate_email;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_display);

        //Get your cloud contacts
        Video vid1 = new Video();
        vid1.id = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);
        GetMongoInstance task = new GetMongoInstance();
        try {
            returnValues = task.execute(vid1).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        for(Video x: returnValues){

            listItems.add(x.getVidPath());
        }

        setListAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listItems));


    }
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id)
    {
        super.onListItemClick(l, v, position, id);

        String selectedValue = (String) getListAdapter().getItem(position);
        Toast.makeText(this, selectedValue, Toast.LENGTH_SHORT).show();
    }

    /*private void fillList() {
//        videos.add("Burger");
//        videos.add("Pizza");
//        videos.add("Chips");
//        videos.add("Beer");
//        videos.add("Water");
//        videos.add("Burger");
//        videos.add("Pizza");
//        videos.add("Chips");
//        videos.add("Beer");
//        videos.add("Water");


    }

    /*private void populateListView() {

        // Build Adapter
        adapter = new myAdapter();

        // Configure ListView
        list.setAdapter(adapter);
    }

    private class myAdapter extends ArrayAdapter<String> {
        public myAdapter() {
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

            // ItemText
            TextView vidText = (TextView) itemView.findViewById(R.id.vidText);
            vidText.setText(currentVideo);

            return itemView;
        }
    }

    private void registerPress() {
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View viewClicked, int position, long id) {
                String pressed = videos.get(position);
                String message = pressed + " pressed";
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
            }

        });
    }*/
}
