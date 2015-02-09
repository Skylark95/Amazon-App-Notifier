package com.skylark95.amazonfreenotify;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import com.skylark95.amazonfreenotify.preference.SettingsFragment;

public class SettingsActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*
         * Workaround for android bug
         * See: https://code.google.com/p/android/issues/detail?id=78701
         */
        getSupportActionBar();

        // Display the fragment as the main content.
        getFragmentManager().beginTransaction()
            .replace(android.R.id.content, new SettingsFragment())
            .commit();
    }

}
