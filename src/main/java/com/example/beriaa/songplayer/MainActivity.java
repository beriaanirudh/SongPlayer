package com.example.beriaa.songplayer;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.beriaa.songplayer.config.Configurations;

public class MainActivity extends Activity{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = getIntent();
        String sharedText = intent.getStringExtra(Intent.EXTRA_TEXT);
        TextView songURL = (TextView) findViewById(R.id.songURL);
        String logMessage;
        Configurations configs = new Configurations();
        SharedPreferences prefs = this.getSharedPreferences(
                configs.getAppKey(), Context.MODE_PRIVATE);
        Editor edit = prefs.edit();
        if (!prefs.contains(configs.getIPAddressKey())) {
            //edit.putString(configs.getIPAddressKey(),
            //        "192.168.1.118");
            //edit.commit();
            takeInputIP();
            Log.e("settingPrefs", "didn't contain, putting");
        }
        else {
            Log.e("settingsPrefs", "contains already!" +
                    prefs.getString(configs.getIPAddressKey(), ""));
        }
        configs.setIPAddress(prefs.getString(configs.getIPAddressKey(),"") +
            configs.getPortNo());
//        if ( == null) {
//
//        }
        /*
        String fileName = Configurations.getFileName();
        File file = new File(Configurations.getDirectoryName());
        if (!file.exists()) {
            file.mkdir();

            Log.e("file", file.toString());
            try {
                FileOutputStream fout = new FileOutputStream(new File(Configurations.getFileName()));
                ObjectOutput oout = new ObjectOutputStream(fout);
                oout.writeObject(new Configurations());
                oout.close();
                fout.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        */
        //GetSongList gsList = new GetSongList(songURL);
        //gsList.execute();


        songURL.setText("welcome");
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void nextSong(View view){
        PlayControl playControl = new PlayControl("playNext", getApplicationContext());
        playControl.execute();
    }
    public void playStop(View view) throws InterruptedException {
        PlayControl playControl = new PlayControl("halt", getApplicationContext());
        playControl.execute();
    }
    public void refreshSongList(View view){
        System.out.println("Refreshing song list");
        TextView songURL = (TextView) findViewById(R.id.songURL);
        GetSongList gsList = new GetSongList(songURL, getApplicationContext()   );
        gsList.execute();
    }
//
//    public void volumeUp(View view){
//        ChangeVolume changeVolume = new ChangeVolume("1");
//        changeVolume.execute();
//    }
//    public void volumeDown(View view){
//        ChangeVolume changeVolume = new ChangeVolume("0");
//        changeVolume.execute();
//    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_VOLUME_DOWN)){
            ChangeVolume changeVolume = new ChangeVolume("0");
            changeVolume.execute();
            //Do something
        }
        return true;
    }
    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_VOLUME_UP)){
            ChangeVolume changeVolume = new ChangeVolume("1");
            changeVolume.execute();
            //Do something
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.IPAddress:
                takeInputIP();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    public void takeInputIP() {
//        getFragmentManager().beginTransaction()
//                .replace(android.R.id.content, new SettingsFragment())
//                .commit();
       // SettingsActivity setAct = new SettingsActivity();
        startActivity(new Intent(this, SettingsActivity.class));

    }
}
