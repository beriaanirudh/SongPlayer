package com.example.beriaa.songplayer;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.TextView;
import android.widget.Toast;

import com.example.beriaa.songplayer.config.Configurations;

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
public class GetSongList extends AsyncTask<String, Void, String> {

    TextView textView;
    String retString = "";
    boolean refreshFlag = false;
    Context context;
    Configurations configs = new Configurations();
    @Override
    protected String doInBackground(String... strings) {
        try {
            downloadUrl("http://" + configs.getIPAddress() + "/?getList=True");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public GetSongList(TextView tView, Context context){
        this.context = context;
        this.textView = tView;
    }



    private String downloadUrl(String myurl) throws IOException {
        InputStream is = null;
        int responseCode = 0;


        int len = 500;


        try {
            URL url = new URL(myurl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setConnectTimeout(15000 /* milliseconds */);
            conn.setRequestMethod("GET");

            conn.connect();
            responseCode = conn.getResponseCode();
            is = conn.getInputStream();

            System.out.println(responseCode + " is response code");
            BufferedReader bfReader = new BufferedReader(new InputStreamReader(is));
            String inputLine;
            System.out.println("PRINTING THE LIST\n\n");
            //This boolean flag is hack to remove response-header
            boolean removeHeader = false;
            while((inputLine = bfReader.readLine()) != null) {
//                if (!removeHeader) {
//                    removeHeader = true;
//                    //continue;
//                }
                System.out.println(inputLine);
                retString += inputLine;
                retString += "\n";
            }
        } finally {
            if (is != null) {
                is.close();
                System.out.println("cloding the connection");
                if (responseCode == 200) {
                    refreshFlag = true;
                }
            }
        }
        return retString;
    }
    @Override
    protected void onPostExecute(String str){
        //textView.setText(retString);
        if (!refreshFlag) {
            Toast.makeText(context, "Couldnt' connect...",
                    Toast.LENGTH_LONG).show();
        }
        textView.setText(retString.replace("\\n", "\n"));

    }
}
