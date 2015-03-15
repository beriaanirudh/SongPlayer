package com.example.beriaa.songplayer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = getIntent();
        String sharedText = intent.getStringExtra(Intent.EXTRA_TEXT);
        TextView songURL = (TextView) findViewById(R.id.songURL);
        //getSongList gsList = new getSongList(songURL);
        //gsList.execute();
        songURL.setText("welcome");
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public void nextSong(View view){
        PlayControl playControl = new PlayControl("playNext");
        playControl.execute();
    }
    public void playStop(View view) throws InterruptedException {
        PlayControl playControl = new PlayControl("halt");
        playControl.execute();
    }
    public void refreshSongList(View view){
        System.out.println("Refreshing song list");
        TextView songURL = (TextView) findViewById(R.id.songURL);
        getSongList gsList = new getSongList(songURL);
        gsList.execute();
    }
    public void volumeUp(View view){
        ChangeVolume changeVolume = new ChangeVolume("1");
        changeVolume.execute();
    }
    public void volumeDown(View view){
        ChangeVolume changeVolume = new ChangeVolume("0");
        changeVolume.execute();
    }
}
