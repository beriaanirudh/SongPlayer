package com.example.beriaa.songplayer;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

/**
 * Created by beriaa on 12/9/14.
 */
public class PlayControl extends AsyncTask <String, Void, String> {
    String control = "";
    @Override
    protected String doInBackground(String... strings) {
        try {
            sendCommand("http://192.168.1.118:8080/?" + this.control + "=True");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    public PlayControl(String control){
        this.control = control;
    }
    public void sendCommand(String command) throws IOException {
        InputStream is = null;
        int len = 500;

        try {
            URL url = new URL(command);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setConnectTimeout(15000 /* milliseconds */);
            conn.setRequestMethod("GET");

            conn.connect();
            is = conn.getInputStream();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                is.close();
                System.out.println("closing the connection");
            }
        }
    }
}
