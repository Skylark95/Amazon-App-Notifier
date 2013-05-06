/*
 * This file is part of Amazon App Notifier
 *
 * Copyright 2013 Derek <derek@skylark95.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Amazon App Notifier is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Amazon App Notifier. if not, If not, see <http://www.gnu.org/licenses/>.
 *
 *
 */

package com.skylark95.amazonfreenotify.settings;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Build;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.support.v4.app.NavUtils;
import android.util.Log;

import com.actionbarsherlock.app.SherlockPreferenceActivity;
import com.actionbarsherlock.view.MenuItem;
import com.commonsware.cwac.wakeful.WakefulIntentService;
import com.skylark95.amazonfreenotify.R;
import com.skylark95.amazonfreenotify.alarm.FreeAppNotificationListener;
import com.skylark95.amazonfreenotify.util.Logger;

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
	public static final String PREF_NOTIFY_ICON_COLOR = "pref_notify_icon_color";
	
	private static final String TAG = Logger.getTag(Preferences.class);
	
	public static void setDefaultValues(Context context) {
		PreferenceManager.setDefaultValues(context, R.xml.preferences, false);
		PrefNotificationDays.setDefaultValues(context);
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.v(TAG, "ENTER - onCreate()");
		addPreferencesFromResource(R.xml.preferences);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		
		updateNotificationTimeSummary();
		updateNotifyIconColorSummary();
		
		if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
			findPreference(PREF_EXPANDABLE_NOTIFICATION).setEnabled(false);
		}
		
		Log.v(TAG, "EXIT - onCreate()");
	}

	@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case android.R.id.home:
            NavUtils.navigateUpFromSameTask(this);
            return true;
        default:
        	return super.onOptionsItemSelected(item);
        }
        
    }

	@Override
	protected void onResume() {
	    super.onResume();
	    Log.v(TAG, "ENTER - onResume()");
	    updateNotificationDaySummary();
	    updateNotificationSoundSummary();
	    getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
	    Log.v(TAG, "EXIT - onResume()");
	}

	@Override
	protected void onPause() {
	    super.onPause();
	    Log.v(TAG, "ENTER - onPause()");
	    getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
	    Log.v(TAG, "EXIT - onPause()");
	}

	@Override
	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
		if (PREF_NOTIFICATION_TIME.equals(key)) {
			handleNotificationTimeChange();
		} else if (PREF_NOTIFY_ICON_COLOR.equals(key)) {
		    updateNotifyIconColorSummary();
		} else if (PREF_ENABLED.equals(key)) {
			handleEnabledChange(sharedPreferences);			
		}
	}

    private void handleEnabledChange(SharedPreferences sharedPreferences) {
        if (sharedPreferences.getBoolean(PREF_ENABLED, true)) {
        	Log.d(TAG, "ENABLED - Schedule Alarms");
        	updateAlarms();
        } else {
        	Log.d(TAG, "DISABLED - Cancel Alarms");
        	WakefulIntentService.cancelAlarms(this);
        }
    }

    private void handleNotificationTimeChange() {
        updateNotificationTimeSummary();
        Log.d(TAG, "UPDATED - Reschedule Alarms");
        updateAlarms();
    }

	private void updateAlarms() {
		Log.v(TAG, "Updating Alarms");
		WakefulIntentService.cancelAlarms(this);
		WakefulIntentService.scheduleAlarms(new FreeAppNotificationListener(), this);
		Log.v(TAG, "DONE Updating Alarms");
	}

	private void updateNotificationTimeSummary() {
		Log.v(TAG, "Updating Time Preference Summary");
		Preference notificationTimePref = findPreference(PREF_NOTIFICATION_TIME);
		String notificationTimeSumm = getString(R.string.pref_notification_time_summ);
		notificationTimePref.setSummary(String.format(notificationTimeSumm, SettingsUtils.getTimeDisplayValue(this)));
		Log.v(TAG, "DONE Updating Time Preference Summary");
	}
	
	private void updateNotificationDaySummary() {
		Log.v(TAG, "Updating Days Preference Summary");
		Preference notificationDayPrefScreen = findPreference(Preferences.PREF_NOTIFICATION_DAYS_SCREEN);		
		String notificationDaySumm = getString(R.string.pref_notification_days_screen_summ);
		notificationDayPrefScreen.setSummary(String.format(notificationDaySumm, SettingsUtils.getDaysDisplayValue(this)));
		Log.v(TAG, "DONE Updating Days Preference Summary");
	}

	private void updateNotificationSoundSummary() {
		Log.v(TAG, "Updating Notification Sound Preference Summary");
		Preference notificationSoundPref = findPreference(PREF_NOTIFICATION_SOUND);
		String notificationSoundSumm = getString(R.string.pref_notification_sound_summ);
		notificationSoundPref.setSummary(String.format(notificationSoundSumm, SettingsUtils.getRingtoneDisplayValue(this)));
		Log.v(TAG, "DONE Updating Notification Sound Preference Summary");
	}
	
	private void updateNotifyIconColorSummary() {
	    Log.v(TAG, "Updating Notify Icon Color Preference Summary");
	    ListPreference prefNotifyIconColor = (ListPreference) findPreference(PREF_NOTIFY_ICON_COLOR);
        String notifyIconColorSumm = getString(R.string.pref_notify_icon_color_summ);
        prefNotifyIconColor.setSummary(String.format(notifyIconColorSumm, prefNotifyIconColor.getEntry()));
        Log.v(TAG, "DONE Updating Notify Icon Color Preference Summary");
    }
	
}
