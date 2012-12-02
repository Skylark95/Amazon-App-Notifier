package com.skylark95.amazonfreenotify.settings;

import android.os.Bundle;

import com.actionbarsherlock.app.SherlockPreferenceActivity;
import com.skylark95.amazonfreenotify.R;

public class Preferences extends SherlockPreferenceActivity {
	
	public static final String PREF_ENABLED = "pref_enabled";
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
	}

}
