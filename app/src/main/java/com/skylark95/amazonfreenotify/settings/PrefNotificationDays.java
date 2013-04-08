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
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.NavUtils;
import android.util.Log;

import com.actionbarsherlock.app.SherlockPreferenceActivity;
import com.actionbarsherlock.view.MenuItem;
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
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
	
}
