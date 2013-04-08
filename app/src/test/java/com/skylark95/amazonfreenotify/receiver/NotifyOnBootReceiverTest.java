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

package com.skylark95.amazonfreenotify.receiver;

import static com.xtremelabs.robolectric.Robolectric.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.skylark95.amazonfreenotify.service.FreeAppNotificationService;
import com.skylark95.amazonfreenotify.settings.Preferences;
import com.xtremelabs.robolectric.RobolectricTestRunner;
import com.xtremelabs.robolectric.shadows.ShadowFragmentActivity;
import com.xtremelabs.robolectric.shadows.ShadowIntent;

@RunWith(RobolectricTestRunner.class)
public class NotifyOnBootReceiverTest {

	private SherlockFragmentActivity activity;
	private Intent mockIntent;
	private BroadcastReceiver receiver;
	private SharedPreferences pref;

	@Before
	public void setUp() {
		activity = new SherlockFragmentActivity();
		mockIntent = mock(Intent.class);
		receiver = new NotifyOnBootReceiver();
		pref = PreferenceManager.getDefaultSharedPreferences(activity);
	}

	@Test
	public void onReceiveStartsNotificationServiceIfOnBootCompletedAndShowOnBootEnabled() {
		when(mockIntent.getAction()).thenReturn(Intent.ACTION_BOOT_COMPLETED);
		pref.edit().putBoolean(Preferences.PREF_SHOW_ON_BOOT, true).commit();
		
		receiver.onReceive(activity, mockIntent);
		ShadowFragmentActivity shadowFragmentActivity = shadowOf(activity);
	    Intent startedService = shadowFragmentActivity.getNextStartedService();
	    ShadowIntent shadowIntent = shadowOf(startedService);
	        
	    assertEquals(shadowIntent.getComponent().getClassName(), FreeAppNotificationService.class.getName());
	}
	
	@Test
	public void onReceiveDoesNotStartNotificationServiceIfOnBootCompletedAndShowOnBootDisabled() {
		when(mockIntent.getAction()).thenReturn(Intent.ACTION_BOOT_COMPLETED);
		pref.edit().putBoolean(Preferences.PREF_SHOW_ON_BOOT, false).commit();
		
		receiver.onReceive(activity, mockIntent);
		ShadowFragmentActivity shadowFragmentActivity = shadowOf(activity);
		Intent startedService = shadowFragmentActivity.getNextStartedService();
		
		assertNull(startedService);
	}
	
	@Test
	public void onReceiveDoesNotStartNotificationServiceIfNotOnBootCompleted() {		
		receiver.onReceive(activity, mockIntent);
		ShadowFragmentActivity shadowFragmentActivity = shadowOf(activity);
		Intent startedService = shadowFragmentActivity.getNextStartedService();
		
		assertNull(startedService);
	}

}
