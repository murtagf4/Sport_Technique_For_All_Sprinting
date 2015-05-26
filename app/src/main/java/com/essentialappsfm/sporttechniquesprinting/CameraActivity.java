package com.essentialappsfm.sporttechniquesprinting;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.os.Bundle;
import android.provider.Settings;
import android.provider.Settings.Secure;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;


public class CameraActivity extends Activity
{
    private static final int VIDEO_REQUEST_CODE = 200;
    public final int MEDIA_TYPE_VIDEO = 1;
    private Intent intentVideo;
    private Uri fileUri;
    private String stringUri;
    private String userPassword = "default";
    boolean isPlaying = true;
    private static final String TAG = "Tag";

    String userID;

    private boolean isCameraSupported()
    {
        if(getApplicationContext().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA))
        {
            // this device has a camera
            return true;
        }
        else
        {
            // no camera on this device
            return false;
        }
    }

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        // Checking camera availability
        if (!isCameraSupported())
        {
            Toast.makeText(getApplicationContext(),"Sorry! There is no camera present on this device",Toast.LENGTH_LONG).show();

            // will close the app if the device doesn't have camera
            finish();
        }

        super.onCreate(savedInstanceState);

        //create new Intent
        intentVideo = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);

        //These lines are moved to move to next view
        intentVideo.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1); // set the video image quality to high
        intentVideo.putExtra(MediaStore.EXTRA_DURATION_LIMIT, 2);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        fileUri = saveMediaFileUri(MEDIA_TYPE_VIDEO);  // create a file to save the video
        intentVideo.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);  // set the image file name

        // start the Video Capture Intent
        startActivityForResult(intentVideo, VIDEO_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == VIDEO_REQUEST_CODE)
        {
            if (resultCode == RESULT_OK)
            {
                // Video captured and saved to fileUri Intent
                stringUri = fileUri.toString();

                Intent iget = getIntent();
                userID = iget.getExtras().getString("user");
                String passID = iget.getExtras().getString("pass");

                Video myVideo = new Video();
                if(userID != null) {
                    myVideo.id = userID;
                    myVideo.password = passID;
                    myVideo.vidPath = stringUri;
                }
                else{
                    myVideo.id = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);
                    myVideo.password = passID;
                    myVideo.vidPath = stringUri;
                }

                SaveMongoInstance task = new SaveMongoInstance();
                task.execute(myVideo);

                Toast.makeText(this, "Video Saved to:" + stringUri, Toast.LENGTH_LONG).show();

                Intent intent = new Intent(this, AnalyseActivity.class);
                intent.putExtra("userData", userID);
                intent.setData(fileUri);
                startActivity(intent);

            }
            else if (resultCode == RESULT_CANCELED)
            {
                // password cancelled recording
                Toast.makeText(getApplicationContext(),"Video Recording Cancelled", Toast.LENGTH_SHORT).show();
            }
            else
            {
                // failed to record video
                Toast.makeText(getApplicationContext(), "Failed to Record Video", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
        Intent i = new Intent(this, PreviewActivity.class);
        i.putExtra("userAccount", userID);
        startActivity(i);
    }
    /** Create a file Uri for saving an image or video to specific folder
     * https://developer.android.com/guide/topics/media/camera.html#saving-media
     * */
    public Uri saveMediaFileUri(int type)
    {
        return Uri.fromFile(saveMediaFile(type));
    }

    /** Create a File for saving an image or video */
    public File saveMediaFile(int type)
    {
        // To be safe, you should check that the SDCard is mounted

        if(Environment.getExternalStorageState() != null)
        {
            // this works for Android 2.2 and above
            File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "SprintVideos");

            // This location works best if you want the created images to be shared
            // between applications and persist after your app has been uninstalled.

            // Create the storage directory if it does not exist
            if (! mediaStorageDir.exists())
            {
                if (! mediaStorageDir.mkdirs())
                {
                    Log.d(TAG, "failed to create directory");
                    return null;
                }
            }

            // Create a media file name
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            File mediaFile;
            if(type == MEDIA_TYPE_VIDEO)
            {
                mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                        "VID_"+ timeStamp + ".mp4");
            } else
            {
                return null;
            }
            return mediaFile;
        }
        return null;
    }
}