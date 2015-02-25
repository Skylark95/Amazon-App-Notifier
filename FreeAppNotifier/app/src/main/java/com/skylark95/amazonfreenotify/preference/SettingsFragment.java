package com.skylark95.amazonfreenotify.preference;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;

import com.skylark95.amazonfreenotify.R;

public class SettingsFragment extends PreferenceFragment {

    private Preference.OnPreferenceChangeListener updateNotificationSoundSummaryListener;
    private SharedPreferences.OnSharedPreferenceChangeListener updateNotificationIconSummaryListener;
    private Preference notificationSound;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);

        updateNotificationSoundSummaryListener = getUpdateNotificationSoundSummaryListener();
        updateNotificationIconSummaryListener = getUpdateNotificationIconSummaryListener();
        notificationSound = findPreference(Settings.KEY_PREF_NOTIFICATION_SOUND);

        setNotificationSoundSummary();
        setNotificationIconSummary();
    }

    @Override
    public void onPause() {
        super.onPause();
        getSharedPreferences().unregisterOnSharedPreferenceChangeListener(updateNotificationIconSummaryListener);
    }

    @Override
    public void onResume() {
        super.onResume();

        /*
         * See: https://stackoverflow.com/questions/6725105/ringtonepreference-not-firing-onsharedpreferencechanged
         */
        notificationSound.setOnPreferenceChangeListener(updateNotificationSoundSummaryListener);

        getSharedPreferences().registerOnSharedPreferenceChangeListener(updateNotificationIconSummaryListener);
    }

    Preference.OnPreferenceChangeListener getUpdateNotificationSoundSummaryListener() {
        if (updateNotificationSoundSummaryListener == null) {
            updateNotificationSoundSummaryListener = new UpdateNotificationSoundSummaryListener(getActivity());
        }
        return updateNotificationSoundSummaryListener;
    }

    SharedPreferences.OnSharedPreferenceChangeListener getUpdateNotificationIconSummaryListener() {
        if (updateNotificationIconSummaryListener == null) {
            updateNotificationIconSummaryListener = new UpdateNotificationIconSummaryListener(getActivity(), getPreferenceManager());
        }
        return updateNotificationIconSummaryListener;
    }

    SharedPreferences getSharedPreferences() {
        return getPreferenceManager().getSharedPreferences();
    }

    private void setNotificationSoundSummary() {
        String notificationUri = getSharedPreferences().getString(Settings.KEY_PREF_NOTIFICATION_SOUND, null);
        updateNotificationSoundSummaryListener.onPreferenceChange(notificationSound, notificationUri);
    }

    private void setNotificationIconSummary() {
        updateNotificationIconSummaryListener.onSharedPreferenceChanged(getSharedPreferences(), Settings.KEY_PREF_NOTIFY_ICON_COLOR);
    }

}
