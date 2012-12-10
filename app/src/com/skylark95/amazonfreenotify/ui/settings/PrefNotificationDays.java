package com.skylark95.amazonfreenotify.ui.settings;

import android.content.Context;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;

import com.actionbarsherlock.app.SherlockPreferenceActivity;
import com.skylark95.amazonfreenotify.R;
import com.skylark95.amazonfreenotify.util.Logger;

public class PrefNotificationDays extends SherlockPreferenceActivity {
	
	public static final String PREF_NOTIFICATION_DAYS_SUNDAY = "pref_notification_days_sunday";
	public static final String PREF_NOTIFICATION_DAYS_MONDAY = "pref_notification_days_monday";
	public static final String PREF_NOTIFICATION_DAYS_TUESDAY = "pref_notification_days_tuesday";
	public static final String PREF_NOTIFICATION_DAYS_WEDNESDAY = "pref_notification_days_wednesday";
	public static final String PREF_NOTIFICATION_DAYS_THURSDAY = "pref_notification_days_thursday";
	public static final String PREF_NOTIFICATION_DAYS_FRIDAY = "pref_notification_days_friday";
	public static final String PREF_NOTIFICATION_DAYS_SATURDAY = "pref_notification_days_saturday";
	
	private static final String TAG = Logger.getTag(PrefNotificationDays.class);
	
	protected static void setDefaultValues(Context context) {
		PreferenceManager.setDefaultValues(context, R.xml.pref_notification_days, false);
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.v(TAG, "ENTER - onCreate()");
		addPreferencesFromResource(R.xml.pref_notification_days);
		Log.v(TAG, "EXIT - onCreate()");
	}
	
}
