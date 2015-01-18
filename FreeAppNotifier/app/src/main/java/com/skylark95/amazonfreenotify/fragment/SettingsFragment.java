package com.skylark95.amazonfreenotify.fragment;

import android.os.Bundle;
import android.preference.PreferenceFragment;

import com.skylark95.amazonfreenotify.R;

public class SettingsFragment extends PreferenceFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Load the preferences from an XML resource
        addPreferencesFromResource(R.xml.preferences);
        
    }

}
