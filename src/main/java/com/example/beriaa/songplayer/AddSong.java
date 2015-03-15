package com.example.beriaa.songplayer;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
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
public class AddSong extends Activity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.activity_main);
        Intent intent = getIntent();
        String sharedText = intent.getStringExtra(Intent.EXTRA_TEXT);
        //TextView songURL = (TextView) findViewById(R.id.songURL);

//        if (intent == null) {
//            songURL.setText("All right");
//        }
//        else {
        TextView songURL = (TextView) findViewById(R.id.songURL);
            LoadSong ls = new LoadSong(songURL, getApplicationContext());
            //ls.context = this;
//            if(sharedText != null) {
               //songURL.setText(sharedText);
        System.out.println(sharedText);
                String[] raw = sharedText.split(" ");
                if (raw.length > 0) {
                    String url = raw[raw.length - 1];
                    String songName = sharedText.substring(0, sharedText.lastIndexOf(" "));
                    System.out.println(songName);
                    //ls.execute(new String[]{url, songName});
                    Log.i("request",url);
                    ls.execute(new String[]{url, songName});

                   // System.out.println(URLEncoder.encode(songName));
                }
//            }
        this.finish();
        }
}
