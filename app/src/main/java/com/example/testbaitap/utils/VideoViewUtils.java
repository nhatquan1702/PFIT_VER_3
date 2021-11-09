package com.example.testbaitap.utils;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;
import android.widget.VideoView;

public class VideoViewUtils {
    // "myvideo.mp4" in directory "raw".
    public static final String RAW_VIDEO_SAMPLE = "myvideo";
    public static final String LOCAL_VIDEO_SAMPLE ="/storage/emulated/0/DCIM/Camera/VID_20180212_195520.mp4";
    public static final String URL_VIDEO_SAMPLE  = "https://ex1.o7planning.com/_testdatas_/mov_bbb.mp4";


    public static final String LOG_TAG= "AndroidVideoView";


    // Play a video in directory RAW.
    // Video name = "myvideo.mp4" ==> resName = "myvideo".
    public static void playRawVideo(Context context, VideoView videoView, String resName)  {
        try {
            // ID of video file.
            int id = VideoViewUtils.getRawResIdByName( context, resName);

            Uri uri = Uri.parse("android.resource://" + context.getPackageName() + "/" + id);
            Toast.makeText(context,"Video URI: "+ uri,Toast.LENGTH_SHORT).show();

            videoView.setVideoURI(uri);
            videoView.requestFocus();

        } catch (Exception e) {
            Log.e(LOG_TAG, "Error Play Raw Video: "+e.getMessage());
            Toast.makeText(context,"Error Play Raw Video: "+ e.getMessage(),Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    // @localPath = "/storage/emulated/0/DCIM/Camera/VID_20180212_195520.mp4"; (For example).
    public static void playLocalVideo(Context context, VideoView videoView, String localPath)  {
        try {

        } catch(Exception e) {
            Log.e(LOG_TAG, "Error Play Local Video: "+ e.getMessage());
            Toast.makeText(context,"Error Play Local Video: "+ e.getMessage(),Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    // String videoURL = "https://ex1.o7planning.com/_testdatas_/mov_bbb.mp4";
    // String videoURL = "https://www.radiantmediaplayer.com/media/bbb-360p.mp4";
    public static void playURLVideo(Context context, VideoView videoView, String videoURL)  {
        try {
            Log.i(LOG_TAG, "Video URL: "+ videoURL);

            Uri uri= Uri.parse( videoURL );

            videoView.setVideoURI(uri);
            videoView.requestFocus();

        } catch(Exception e) {
            Log.e(LOG_TAG, "Error Play URL Video: "+ e.getMessage());
            Toast.makeText(context,"Error Play URL Video: "+ e.getMessage(),Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    // Find ID corresponding to the name of the resource (in the directory RAW).
    public static int getRawResIdByName(Context context, String resName) {
        String pkgName = context.getPackageName();
        // Return 0 if not found.
        int resID = context.getResources().getIdentifier(resName, "raw", pkgName);

        Log.i(LOG_TAG, "Res Name: " + resName + "==> Res ID = " + resID);
        return resID;
    }
}
