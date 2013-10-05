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

package com.skylark95.amazonfreenotify.notification;

import static com.xtremelabs.robolectric.Robolectric.shadowOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.preference.PreferenceManager;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.skylark95.amazonfreenotify.R;
import com.skylark95.amazonfreenotify.net.AppDataReader;
import com.skylark95.amazonfreenotify.settings.Preferences;
import com.skylark95.amazonfreenotify.util.TestUtils;
import com.skylark95.robolectric.AmazonAppNotifierTestRunner;
import com.xtremelabs.robolectric.Robolectric;
import com.xtremelabs.robolectric.shadows.ShadowApplication;
import com.xtremelabs.robolectric.shadows.ShadowNetworkInfo;
import com.xtremelabs.robolectric.shadows.ShadowNotification;
import com.xtremelabs.robolectric.shadows.ShadowPendingIntent;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;


/*
 *  TEST CASES MATRIX: 
 * 	connected	show name/price	found appstore	should pass
 *  ---------	---------------	--------------	-----------
 * 	TRUE		TRUE			TRUE			TRUE
 *	TRUE		TRUE			TRUE			FALSE
 * 	TRUE		TRUE			FALSE			TRUE
 * 	TRUE		TRUE			FALSE			FALSE
 * 	TRUE		FALSE			TRUE			---
 * 	TRUE		FALSE			FALSE			---
 * 	FALSE		---				TRUE			---
 *  FALSE		---				FALSE			---
 *
 */
@RunWith(AmazonAppNotifierTestRunner.class)
public class FreeAppNotificationFactoryTest {

	private Context context;
	private SharedPreferences pref;
	private Intent mockAppstoreIntent;
	private AppDataReader mockAppDataReader;
	private Intent noAppstoreIntent;
	
	@Before
	public void setUp() {
		context = new SherlockFragmentActivity();
		pref = PreferenceManager.getDefaultSharedPreferences(context);
		mockAppstoreIntent = null;
		mockAppDataReader = null;
		noAppstoreIntent = null;
	}

	@Test 
	public void buildWhenConnectedAndAppstoreFoundAndDownloadAppDataNoFail() {
		setConnectionStatus(true);
		setFoundAppstore(true);
		setMockAppDataReader(true);
		pref.edit().putBoolean(Preferences.PREF_SHOW_NAME_PRICE, true).commit();
		
		Notification notification = buildTheNotification();
		
		ShadowPendingIntent shadowPendingIntent = shadowOf(notification.contentIntent);
		ShadowNotification shadowNotification = shadowOf(notification);
		String appTitle = TestUtils.readTestAppData().getFreeAppData().getAppTitle();
		assertEquals(mockAppstoreIntent, shadowPendingIntent.getSavedIntent());		
		assertEquals(appTitle, shadowNotification.getLatestEventInfo().getContentTitle());
	}
	
	@Test 
	public void buildWhenConnectedAndAppstoreFoundAndDownloadAppDataFail() {
		setConnectionStatus(true);
		setFoundAppstore(true);
		setMockAppDataReader(false);
		pref.edit().putBoolean(Preferences.PREF_SHOW_NAME_PRICE, true).commit();
		
		Notification notification = buildTheNotification();
		
		ShadowPendingIntent shadowPendingIntent = shadowOf(notification.contentIntent);
		ShadowNotification shadowNotification = shadowOf(notification);
		assertEquals(mockAppstoreIntent, shadowPendingIntent.getSavedIntent());		
		assertEquals(context.getString(R.string.notification_simple_title), shadowNotification.getLatestEventInfo().getContentTitle());
		assertEquals(context.getString(R.string.notification_error_text), shadowNotification.getLatestEventInfo().getContentText());
	}
	
	@Test 
	public void buildWhenConnectedAndAppstoreNotFoundAndDownloadAppDataNoFail() {
		setConnectionStatus(true);
		setFoundAppstore(false);
		setMockAppDataReader(true);
		pref.edit().putBoolean(Preferences.PREF_SHOW_NAME_PRICE, true).commit();
		
		Notification notification = buildTheNotification();
		
		ShadowPendingIntent shadowPendingIntent = shadowOf(notification.contentIntent);
		ShadowNotification shadowNotification = shadowOf(notification);
		String appTitle = TestUtils.readTestAppData().getFreeAppData().getAppTitle();
		assertEquals(noAppstoreIntent.getData(), shadowPendingIntent.getSavedIntent().getData());		
		assertEquals(appTitle, shadowNotification.getLatestEventInfo().getContentTitle());
		assertEquals(context.getString(R.string.notification_simple_no_app_store_text), shadowNotification.getLatestEventInfo().getContentText());
	}

	@Test 
	public void buildWhenConnectedAndAppstoreNotFoundAndDownloadAppDataFail() {
		setConnectionStatus(true);
		setFoundAppstore(false);
		setMockAppDataReader(false);
		pref.edit().putBoolean(Preferences.PREF_SHOW_NAME_PRICE, true).commit();
		
		Notification notification = buildTheNotification();
		
		ShadowPendingIntent shadowPendingIntent = shadowOf(notification.contentIntent);
		ShadowNotification shadowNotification = shadowOf(notification);
		assertEquals(noAppstoreIntent.getData(), shadowPendingIntent.getSavedIntent().getData());		
		assertEquals(context.getString(R.string.notification_simple_title), shadowNotification.getLatestEventInfo().getContentTitle());
		assertEquals(context.getString(R.string.notification_error_text), shadowNotification.getLatestEventInfo().getContentText());
	}

	@Test 
	public void buildWhenConnectedAndAppstoreFoundAndDontDownloadAppData() {
		setConnectionStatus(true);
		setFoundAppstore(true);
		pref.edit().putBoolean(Preferences.PREF_SHOW_NAME_PRICE, false).commit();
		
		Notification notification = buildTheNotification();
		
		ShadowPendingIntent shadowPendingIntent = shadowOf(notification.contentIntent);
		ShadowNotification shadowNotification = shadowOf(notification);
		assertEquals(mockAppstoreIntent, shadowPendingIntent.getSavedIntent());		
		assertEquals(context.getString(R.string.notification_simple_title), shadowNotification.getLatestEventInfo().getContentTitle());
		assertEquals(context.getString(R.string.notification_simple_text), shadowNotification.getLatestEventInfo().getContentText());
	}

	@Test 
	public void buildWhenConnectedAndAppstoreNotFoundAndDontDownloadAppData() {
		setConnectionStatus(true);
		setFoundAppstore(false);
		pref.edit().putBoolean(Preferences.PREF_SHOW_NAME_PRICE, false).commit();
		
		Notification notification = buildTheNotification();
		
		ShadowPendingIntent shadowPendingIntent = shadowOf(notification.contentIntent);
		ShadowNotification shadowNotification = shadowOf(notification);
		assertEquals(noAppstoreIntent.getData(), shadowPendingIntent.getSavedIntent().getData());
		assertEquals(context.getString(R.string.notification_simple_title), shadowNotification.getLatestEventInfo().getContentTitle());
		assertEquals(context.getString(R.string.notification_simple_no_app_store_text), shadowNotification.getLatestEventInfo().getContentText());
	}

	@Test 
	public void buildWhenNotConnectedAndAppstoreFound() {
		setConnectionStatus(false);
		setFoundAppstore(true);
		
		Notification notification = buildTheNotification();
		
		ShadowPendingIntent shadowPendingIntent = shadowOf(notification.contentIntent);
		ShadowNotification shadowNotification = shadowOf(notification);
		assertEquals(mockAppstoreIntent, shadowPendingIntent.getSavedIntent());		
		assertEquals(context.getString(R.string.notification_simple_title), shadowNotification.getLatestEventInfo().getContentTitle());
		assertEquals(context.getString(R.string.notification_simple_offline_text), shadowNotification.getLatestEventInfo().getContentText());
	}
	
	@Test 
	public void buildWhenNotConnectedAndAppstoreNotFound() {
		setConnectionStatus(false);
		setFoundAppstore(false);
		
		Notification notification = buildTheNotification();
		
		ShadowPendingIntent shadowPendingIntent = shadowOf(notification.contentIntent);
		ShadowNotification shadowNotification = shadowOf(notification);
		assertEquals(noAppstoreIntent.getData(), shadowPendingIntent.getSavedIntent().getData());
		assertEquals(context.getString(R.string.notification_simple_title), shadowNotification.getLatestEventInfo().getContentTitle());
		assertEquals(context.getString(R.string.notification_simple_offline_text), shadowNotification.getLatestEventInfo().getContentText());
	}

	private Notification buildTheNotification() {
		FreeAppNotification notification = FreeAppNotificationFactory.buildNotification(context, mockAppDataReader);
		return notification.buildNotification();
	}

	private void setFoundAppstore(boolean isAppstoreFound) {
		ShadowApplication shadowApplication = shadowOf(Robolectric.application);
		PackageManager mockPackageManager = mock(PackageManager.class);
		shadowApplication.setPackageManager(mockPackageManager);
		if (isAppstoreFound) {
			mockAppstoreIntent = mock(Intent.class);
			when(mockPackageManager.getLaunchIntentForPackage(context.getString(R.string.app_store_package)))
				.thenReturn(mockAppstoreIntent);
		} else {
			String url = context.getString(R.string.app_store_download_url);
			noAppstoreIntent = new Intent(Intent.ACTION_VIEW);
			noAppstoreIntent.setData(Uri.parse(url));
		}
	}

	private void setConnectionStatus(boolean isConnected) {
		ConnectivityManager connMgr = (ConnectivityManager) Robolectric.application.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
		ShadowNetworkInfo shadowNetworkInfo = shadowOf(networkInfo);
		shadowNetworkInfo.setConnectionStatus(isConnected);
	}

	private void setMockAppDataReader(boolean shouldPass) {
		mockAppDataReader = mock(AppDataReader.class);
		try {
			if (shouldPass) {
				when(mockAppDataReader.downloadAppData(context.getString(R.string.app_data_url)))
					.thenReturn(TestUtils.readTestAppData());
			} else {
				when(mockAppDataReader.downloadAppData(context.getString(R.string.app_data_url)))
					.thenThrow(new IOException());
			}			
		} catch (IOException e) {
			fail(e.getMessage());
		}
	}


}
