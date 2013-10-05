/*
 * This file is part of Amazon App Notifier (Free App Notifier for Amazon)
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

package com.skylark95.amazonfreenotify.util;

import static org.junit.Assert.fail;

import android.content.Context;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.skylark95.amazonfreenotify.beans.AppDataResponse;
import com.skylark95.amazonfreenotify.net.AppDataReader;
import com.skylark95.amazonfreenotify.net.AppDataReaderImpl;
import com.skylark95.amazonfreenotify.settings.PrefNotificationDays;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public final class TestUtils {
		
	public static final String TEST_APP_DATA_PATH = "src/test/resources/testAppData.html";

	private TestUtils() {
	}

	/**
	 * Test Fragments with Robolectric
	 * 
	 * @see http://stackoverflow.com/questions/11333354/how-can-i-test-fragments-with-robolectric
	 */
	public static void startFragment(Fragment fragment, FragmentActivity activity) {
	    FragmentManager fragmentManager = activity.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(fragment, null);
        fragmentTransaction.commit();
	}
	
	public static void startFragment(Fragment fragment) {
		startFragment(fragment, new SherlockFragmentActivity());
	}
	
	public static AppDataResponse readTestAppData() {
		AppDataResponse retVal = null;
		try {
			File file = new File(TEST_APP_DATA_PATH);
			URL url = file.toURI().toURL();
			AppDataReader appDataReader = new AppDataReaderImpl();
			retVal = appDataReader.downloadAppData(url.toString());		
		} catch (IOException e) {
			fail(e.getMessage());			
		} 
		return retVal;
	}
	
	public static void allDaysChecked(Context context, boolean checked) {
		PreferenceManager.getDefaultSharedPreferences(context).edit()
			.putBoolean(PrefNotificationDays.PREF_NOTIFICATION_DAYS_SUNDAY, checked)
			.putBoolean(PrefNotificationDays.PREF_NOTIFICATION_DAYS_MONDAY, checked)
			.putBoolean(PrefNotificationDays.PREF_NOTIFICATION_DAYS_TUESDAY, checked)
			.putBoolean(PrefNotificationDays.PREF_NOTIFICATION_DAYS_WEDNESDAY, checked)
			.putBoolean(PrefNotificationDays.PREF_NOTIFICATION_DAYS_THURSDAY, checked)
			.putBoolean(PrefNotificationDays.PREF_NOTIFICATION_DAYS_FRIDAY, checked)
			.putBoolean(PrefNotificationDays.PREF_NOTIFICATION_DAYS_SATURDAY, checked)
			.commit();		
	}

}
