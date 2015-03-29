package com.example.beriaa.songplayer;

import android.os.AsyncTask;

import com.example.beriaa.songplayer.config.Configurations;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by beriaa on 12/28/14.
 */
public class ChangeVolume extends AsyncTask<String, Void, String> {

    Configurations configs = new Configurations();
    String value;
    @Override
    protected String doInBackground(String... strings) {
        try {
            downloadUrl("http://" + configs.getIPAddress() +
                    configs.getPortNo() + "/?volume="+value);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    public ChangeVolume(String value){
        this.value = value;
    }
    private String downloadUrl(String myurl) throws IOException {
        InputStream is = null;
        // Only display the first 500 characters of the retrieved
        // web page content.
        int len = 500;

        try {
            URL url = new URL(myurl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            //conn.setReadTimeout(10000 /* milliseconds */);
            conn.setConnectTimeout(200 /* milliseconds */);
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
        } finally {
            if (is != null) {
                is.close();
                System.out.println("cloding the connection to " + configs.getIPAddress());
            }
        }
        return "success";
    }
}
