package com.example.beriaa.songplayer;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by beriaa on 12/1/14.
 */
public class LoadSong extends AsyncTask<String, Void, String>{

    public Activity activity;
    public TextView songURL;
    Context context;
    public String toastMessage = "Song Added!";
    public LoadSong(TextView songURL, Context context){

        this.songURL = songURL;
        this.context = context;
    }
    public LoadSong()
    {}
    @Override
    protected String doInBackground(String... strings) {
        try {
            System.out.println(strings[0]);
            //System.out.println(strings[1]);
            //downloadUrl("http://192.168.1.142:8080/?link="+strings[0]+"&name=myname");
            downloadUrl("http://192.168.1.118:8080/?link="+strings[0] + "&name=" + URLEncoder.encode(strings[1]));

        } catch (IOException e) {
            e.printStackTrace();
        }
        /*getSongList gsList = new getSongList(songURL);
        gsList.execute();
        */
        return null;
    }
    private void downloadUrl(String myurl) throws IOException {

        InputStream is = null;
        // Only display the first 500 characters of the retrieved
        // web page content.
        int len = 500;

        try {
            URL url = new URL(myurl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            //conn.setReadTimeout(10000 /* milliseconds */);
            conn.setConnectTimeout(2000 /* milliseconds */);
            conn.setRequestMethod("GET");
            //conn.setDoInput(true);
            // Starts the query
            conn.connect();
            //int response = conn.getResponseCode();
           // Log.d(DEBUG_TAG, "The response is: " + response);
            is = conn.getInputStream();

            // Convert the InputStream into a string
            //String contentAsString = readIt(is, len);
            //return contentAsString;


            // Makes sure that the InputStream is closed after the app is
            // finished using it.
        }
        catch (Exception e) {
            toastMessage = "Error! Sorry, couldn't add...";
            Log.e("LoadSong", e.toString());
        } finally {
            if (is != null) {
                is.close();
                System.out.println("cloding the connection");
            }

        }
    }


    @Override
    protected void onPostExecute(String s) {
        Log.i("POST EXECUTE","COMPLETE");
        super.onPostExecute(s);
        Toast.makeText(context, toastMessage,
                Toast.LENGTH_LONG).show();
    }
}
