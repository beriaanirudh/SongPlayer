package com.example.beriaa.songplayer;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.example.beriaa.songplayer.config.Configurations;

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
    public String toastMessage = "Can't reach server...";
    Context context;
    Configurations configs = new Configurations();
    @Override
    protected String doInBackground(String... strings) {
        try {
            sendCommand("http://"+ configs.getIPAddress() +
                    "/?" + this.control + "=True");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    public PlayControl(String control, Context context){

        this.control = control;
        this.context = context;
    }
    public void sendCommand(String command) throws IOException {
        InputStream is = null;
        int responseCode = 0;
        int len = 500;

        try {
            URL url = new URL(command);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setConnectTimeout(15000 /* milliseconds */);
            conn.setRequestMethod("GET");

            conn.connect();
            is = conn.getInputStream();
            responseCode = conn.getResponseCode();
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
                toastMessage = control + " Sent";
            }
        }
    }
    @Override
    protected void onPostExecute(String s) {
        Log.i("POST EXECUTE", "COMPLETE");
        super.onPostExecute(s);
        Toast.makeText(context, toastMessage,
                Toast.LENGTH_LONG).show();
    }
}
