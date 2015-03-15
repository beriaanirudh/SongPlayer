package com.example.beriaa.songplayer;

import android.os.AsyncTask;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by beriaa on 12/7/14.
 */
public class getSongList extends AsyncTask<String, Void, String> {

    TextView textView;
    String retString = "";
    @Override
    protected String doInBackground(String... strings) {
        try {
            downloadUrl("http://raspberrypi:8080/?getList=True");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public getSongList(TextView tView){
        this.textView = tView;
    }



    private String downloadUrl(String myurl) throws IOException {
        InputStream is = null;



        int len = 500;

        try {
            URL url = new URL(myurl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setConnectTimeout(15000 /* milliseconds */);
            conn.setRequestMethod("GET");

            conn.connect();
            is = conn.getInputStream();
            BufferedReader bfReader = new BufferedReader(new InputStreamReader(is));
            String inputLine;
            System.out.println("PRINTING THE LIST\n\n");
            while((inputLine = bfReader.readLine()) != null) {
                System.out.println(inputLine);
                retString += inputLine;
                retString += "\n";
            }
        } finally {
            if (is != null) {
                is.close();
                System.out.println("cloding the connection");
            }
        }
        return retString;
    }
    @Override
    protected void onPostExecute(String str){
        //textView.setText(retString);
        textView.setText(retString.replace("\\n", "\n"));

    }
}
