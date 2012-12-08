package com.skylark95.amazonfreenotify.ui.settings;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceManager;

import com.actionbarsherlock.app.SherlockPreferenceActivity;
import com.skylark95.amazonfreenotify.R;

public class Preferences extends SherlockPreferenceActivity implements OnSharedPreferenceChangeListener {
	
	public static final String PREF_ENABLED = "pref_enabled";
	public static final String PREF_NOTIFICATION_TIME = "pref_notification_time";
	public static final String PREF_NOTIFICATION_DAYS_SCREEN = "pref_notification_days_screen";
	public static final String PREF_NOTIFICATION_SOUND = "pref_notification_sound";
	public static final String PREF_SHOW_NAME_PRICE = "pref_show_name_price";
	public static final String PREF_SHOW_ON_BOOT = "pref_show_on_boot";
	public static final String PREF_PLAY_NOTIFICATION_SOUND = "pref_play_notification_sound";
	public static final String PREF_VIBRATE = "pref_vibrate";
	public static final String PREF_EXPANDABLE_NOTIFICATION = "pref_expandable_notification";
	public static final String PREF_NOTIFY_FOR_GAMES = "pref_notifiy_for_games";
	
	public static void setDefaultValues(Context context) {
		PreferenceManager.setDefaultValues(context, R.xml.preferences, false);
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.preferences);
		setNotificationTimePrefSummary();
		setNotificationSoundPrefSummary();
	}

	@Override
	protected void onResume() {
	    super.onResume();
	    setNotificationDayPrefSummary();
	    setNotificationSoundPrefSummary();
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
		String notificationTimeSumm = getString(R.string.pref_notification_time_summ);
		notificationTimePref.setSummary(String.format(notificationTimeSumm, SettingsUtils.getTimeDisplayValue(this)));
	}
	
	private void setNotificationDayPrefSummary() {
		Preference notificationDayPrefScreen = findPreference(Preferences.PREF_NOTIFICATION_DAYS_SCREEN);		
		String notificationDaySumm = getString(R.string.pref_notification_days_screen_summ);
		notificationDayPrefScreen.setSummary(String.format(notificationDaySumm, SettingsUtils.getDaysDisplayValue(this)));
	}

	private void setNotificationSoundPrefSummary() {
		Preference notificationSoundPref = findPreference(PREF_NOTIFICATION_SOUND);
		String notificationSoundSumm = getString(R.string.pref_notification_sound_summ);
		notificationSoundPref.setSummary(String.format(notificationSoundSumm, SettingsUtils.getRingtoneDisplayValue(this)));		
	}
	
}
