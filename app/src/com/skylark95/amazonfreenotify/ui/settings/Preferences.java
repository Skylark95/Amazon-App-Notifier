package com.skylark95.amazonfreenotify.ui.settings;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.content.res.Resources;
import android.os.Bundle;
import android.preference.MultiSelectListPreference;
import android.preference.Preference;

import com.actionbarsherlock.app.SherlockPreferenceActivity;
import com.skylark95.amazonfreenotify.R;
import com.skylark95.amazonfreenotify.util.Utils;

public class Preferences extends SherlockPreferenceActivity implements OnSharedPreferenceChangeListener {
	
	public static final String PREF_ENABLED = "pref_enabled";
	public static final String PREF_NOTIFICATION_TIME = "pref_notification_time";
	public static final String PREF_NOTIFICATION_DAYS = "pref_notification_days";
	public static final String PREF_NOTIFICATION_SOUND = "pref_notification_sound";
	public static final String PREF_SHOW_NAME_PRICE = "pref_show_name_price";
	public static final String PREF_SHOW_ON_BOOT = "pref_show_on_boot";
	public static final String PREF_PLAY_NOTIFICATION_SOUND = "pref_play_notification_sound";
	public static final String PREF_VIBRATE = "pref_vibrate";
	public static final String PREF_EXPANDABLE_NOTIFICATION = "pref_expandable_notification";
	public static final String PREF_NOTIFY_FOR_GAMES = "pref_notifiy_for_games";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.preferences);
		setNotificationTimePrefSummary();		
	}
	
	@Override
	protected void onResume() {
	    super.onResume();
	    getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
	}

	@Override
	protected void onPause() {
	    super.onPause();
	    getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
	}

	@Override
	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
		if (PREF_NOTIFICATION_TIME.equals(key)) {
			setNotificationTimePrefSummary();
		}				
	}
	

	private void setNotificationTimePrefSummary() {
		Preference notificationTimePref = findPreference(PREF_NOTIFICATION_TIME);
		String time = notificationTimePref.getSharedPreferences().getString(PREF_NOTIFICATION_TIME, "12:00");
		String notificationTimeSumm = getString(R.string.pref_notification_time_summ);
		notificationTimePref.setSummary(String.format(notificationTimeSumm, Utils.formatTime(time)));
	}
	
	private void setNotificationDayPrefSummary() {
		Preference notificationDayPref = findPreference(PREF_NOTIFICATION_DAYS);		
	}

}
