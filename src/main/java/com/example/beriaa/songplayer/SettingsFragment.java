package com.example.beriaa.songplayer;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.util.Log;

import com.example.beriaa.songplayer.config.Configurations;

/**
 * Created by beriaa on 3/29/15.
 */
public class SettingsFragment extends PreferenceFragment
        implements SharedPreferences.OnSharedPreferenceChangeListener{
    Configurations configs = new Configurations();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.preferences);
        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
        Configurations configs = new Configurations();
        configs.setIPAddress(sharedPreferences.getString(s,"") +
            configs.getPortNo());
        //sharedPreferences.edit().putString()
        Log.e("changinPref", configs.getIPAddress());

    }
}
