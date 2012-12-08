package com.skylark95.amazonfreenotify.ui.settings;

import android.os.Bundle;

import com.actionbarsherlock.app.SherlockPreferenceActivity;
import com.skylark95.amazonfreenotify.R;

public class PrefNotificationDays extends SherlockPreferenceActivity {
	
	public static final String PREF_NOTIFICATION_DAYS_SUNDAY = "pref_notification_days_sunday";
	public static final String PREF_NOTIFICATION_DAYS_MONDAY = "pref_notification_days_monday";
	public static final String PREF_NOTIFICATION_DAYS_TUESDAY = "pref_notification_days_tuesday";
	public static final String PREF_NOTIFICATION_DAYS_WEDNESDAY = "pref_notification_days_wednesday";
	public static final String PREF_NOTIFICATION_DAYS_THURSDAY = "pref_notification_days_thursday";
	public static final String PREF_NOTIFICATION_DAYS_FRIDAY = "pref_notification_days_friday";
	public static final String PREF_NOTIFICATION_DAYS_SATURDAY = "pref_notification_days_saturday";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.pref_notification_days);
	}
	
}
