package com.skylark95.amazonfreenotify.util;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import android.content.Context;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.skylark95.amazonfreenotify.beans.AppDataResponse;
import com.skylark95.amazonfreenotify.net.AppDataReader;
import com.skylark95.amazonfreenotify.net.AppDataReaderImpl;
import com.skylark95.amazonfreenotify.settings.PrefNotificationDays;

public final class TestUtils {
		
	public static final String TEST_APP_DATA_PATH = "src/test/resources/testAppData.html";

	private TestUtils() {
	}

	/**
	 * Test Fragments with Robolectric
	 * 
	 * @see http://stackoverflow.com/questions/11333354/how-can-i-test-fragments-with-robolectric
	 */
	public static void startFragment(Fragment fragment) {
		FragmentManager fragmentManager = new SherlockFragmentActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(fragment, null);
        fragmentTransaction.commit();
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
