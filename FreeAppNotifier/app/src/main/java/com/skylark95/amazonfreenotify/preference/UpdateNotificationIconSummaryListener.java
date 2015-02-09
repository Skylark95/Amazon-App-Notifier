package com.skylark95.amazonfreenotify.preference;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.ListPreference;
import android.preference.PreferenceManager;

public class UpdateNotificationIconSummaryListener implements SharedPreferences.OnSharedPreferenceChangeListener {

    private final Context context;
    private final PreferenceManager preferenceManager;

        public UpdateNotificationIconSummaryListener(Context context, PreferenceManager preferenceManager) {
            this.context = context;
            this.preferenceManager = preferenceManager;
        }

        @Override
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
            if (Settings.KEY_PREF_NOTIFY_ICON_COLOR.equals(key)) {
                ListPreference listPreference = (ListPreference) preferenceManager.findPreference(Settings.KEY_PREF_NOTIFY_ICON_COLOR);
                listPreference.setSummary(listPreference.getEntry());
            }
        }

}
