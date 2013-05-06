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

import static com.xtremelabs.robolectric.Robolectric.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.app.AlarmManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.preference.PreferenceScreen;

import com.actionbarsherlock.ActionBarSherlockRobolectric;
import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.view.MenuItem;
import com.commonsware.cwac.wakeful.WakefulIntentService;
import com.skylark95.amazonfreenotify.R;
import com.skylark95.amazonfreenotify.alarm.FreeAppNotificationListener;
import com.skylark95.amazonfreenotify.notification.NotificationIcon;
import com.skylark95.amazonfreenotify.util.TestUtils;
import com.xtremelabs.robolectric.Robolectric;
import com.xtremelabs.robolectric.RobolectricTestRunner;
import com.xtremelabs.robolectric.shadows.ShadowAlarmManager;
import com.xtremelabs.robolectric.shadows.ShadowAlarmManager.ScheduledAlarm;
import com.xtremelabs.robolectric.shadows.ShadowPreferenceActivity;

@RunWith(RobolectricTestRunner.class)
public class PreferencesTest {
	
	private static class PreferencesTester extends Preferences {
		
		private ActionBar actionBar;
		private Map<String, Preference> mockPrefs;
		private PreferenceScreen mockPreferenceScreen;
		private SharedPreferences mockSharedPreferences;
		
		public PreferencesTester() {
			actionBar = mock(ActionBar.class);
			mockPreferenceScreen = mock(PreferenceScreen.class);
			mockSharedPreferences = mock(SharedPreferences.class);
			when(mockPreferenceScreen.getSharedPreferences()).thenReturn(mockSharedPreferences);
			buildMockPrefences();
		}

		@Override
		public ActionBar getSupportActionBar() {
			return actionBar;
		}

		@Override
		@Deprecated
		public Preference findPreference(CharSequence key) {
			return mockPrefs.get(key);
		}
		
		
		@Override
		@Deprecated
		public PreferenceScreen getPreferenceScreen() {
			return mockPreferenceScreen;
		}

		@Override
		@Deprecated
		public void addPreferencesFromResource(int preferencesResId) {
			// nothing
		}

		private void buildMockPrefences() {
			mockPrefs = new HashMap<String, Preference>();
			mockPrefs.put(Preferences.PREF_EXPANDABLE_NOTIFICATION, mock(Preference.class));
			mockPrefs.put(Preferences.PREF_NOTIFICATION_TIME, mock(Preference.class));
			mockPrefs.put(Preferences.PREF_ENABLED, mock(Preference.class));
			mockPrefs.put(Preferences.PREF_NOTIFICATION_DAYS_SCREEN, mock(Preference.class));
			mockPrefs.put(Preferences.PREF_NOTIFICATION_SOUND, mock(Preference.class));
			mockPrefs.put(Preferences.PREF_NOTIFY_ICON_COLOR, mock(ListPreference.class));
		}

		public Map<String, Preference> getMockPrefs() {
			return mockPrefs;
		}		
		
	}

	private PreferencesTester activity;
	private ActionBar mockActionBar;
	private Map<String, Preference> mockPrefs;
	private SharedPreferences pref;
	private PreferenceScreen mockPreferenceScreen;
	private SharedPreferences mockSharedPreferences;

	@Before
	public void setUp() {
		setSdkVersion(0);
		ActionBarSherlockRobolectric.registerImplementation();
		activity = new PreferencesTester();
		mockActionBar = activity.getSupportActionBar();
		mockPrefs = activity.getMockPrefs();
		pref = PreferenceManager.getDefaultSharedPreferences(activity);
		mockPreferenceScreen = activity.getPreferenceScreen();
		mockSharedPreferences = mockPreferenceScreen.getSharedPreferences();
	}

	@Test
	public void expandableNotificationPrefEnabledIfJellybean() {
		setSdkVersion(Build.VERSION_CODES.JELLY_BEAN);
		activity.onCreate(null);
		verify(mockPrefs.get(Preferences.PREF_EXPANDABLE_NOTIFICATION), never()).setEnabled(false);
	}
	
	@Test
	public void expandableNotificationPrefDisabledIfICS() {
		setSdkVersion(Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1);
		activity.onCreate(null);
		verify(mockPrefs.get(Preferences.PREF_EXPANDABLE_NOTIFICATION)).setEnabled(false);
	}
	
	@Test
	public void notificationTimePrefSummaryUpdatedWhenOnCreateCalled() {
		pref.edit().putString(Preferences.PREF_NOTIFICATION_TIME, "10:0").commit();
		activity.onCreate(null);
		String summary = activity.getString(R.string.pref_notification_time_summ);
		verify(mockPrefs.get(Preferences.PREF_NOTIFICATION_TIME)).setSummary(String.format(summary, "10:00 AM"));		
	}
	
	@Test
	public void notifyIconColorSummaryUpdatedWhenOnCreateCalled() {
	    ListPreference mockListPref = (ListPreference) activity.findPreference(Preferences.PREF_NOTIFY_ICON_COLOR);
	    when(mockListPref.getEntry()).thenReturn("Orange");
	    activity.onCreate(null);
	    String summary = activity.getString(R.string.pref_notify_icon_color_summ);
	    verify(mockPrefs.get(Preferences.PREF_NOTIFY_ICON_COLOR)).setSummary(String.format(summary, "Orange"));		
	}
	
	@Test
	public void notificationTimePrefSummaryUpdatedWhenOnSharedPreferenceChangeCalled() {
		pref.edit().putString(Preferences.PREF_NOTIFICATION_TIME, "21:0").commit();
		activity.onSharedPreferenceChanged(pref, Preferences.PREF_NOTIFICATION_TIME);
		String summary = activity.getString(R.string.pref_notification_time_summ);
		verify(mockPrefs.get(Preferences.PREF_NOTIFICATION_TIME)).setSummary(String.format(summary, "9:00 PM"));		
	}
	
	@Test
    public void notifyIconColorSummaryUpdatedWhenOnSharedPreferenceChangeCalled() {
	    ListPreference mockListPref = (ListPreference) activity.findPreference(Preferences.PREF_NOTIFY_ICON_COLOR);
        when(mockListPref.getEntry()).thenReturn("Blue");
        activity.onSharedPreferenceChanged(pref, Preferences.PREF_NOTIFY_ICON_COLOR);
        String summary = activity.getString(R.string.pref_notify_icon_color_summ);
        verify(mockPrefs.get(Preferences.PREF_NOTIFY_ICON_COLOR)).setSummary(String.format(summary, "Blue"));        
    }
	
	@Test
	public void notificationSoundPrefSummaryUpdatedWhenOnResumeCalled() {
		activity.onResume();
		String summary = activity.getString(R.string.pref_notification_sound_summ);
		verify(mockPrefs.get(Preferences.PREF_NOTIFICATION_SOUND)).setSummary(String.format(summary, "Not Selected"));		
	}
	
	@Test
	public void notificationDayPrefSummaryUpdatedWhenOnResumeCalled() {
		TestUtils.allDaysChecked(activity, false);
		activity.onResume();
		String summary = activity.getString(R.string.pref_notification_days_screen_summ);
		verify(mockPrefs.get(Preferences.PREF_NOTIFICATION_DAYS_SCREEN)).setSummary(String.format(summary, "None Selected"));		
	}
	
	@Test
	public void onResumeRegisterOnSharedPreferenceChangeListener() {
		activity.onResume();
		verify(mockSharedPreferences).registerOnSharedPreferenceChangeListener(activity);
	}
	
	@Test
	public void onPauseUnregisterOnSharedPreferenceChangeListener() {
		activity.onPause();
		verify(mockSharedPreferences).unregisterOnSharedPreferenceChangeListener(activity);
	}
	
	@Test
	public void onOptionsItemSelectedHandlesHome() {
		MenuItem mockMenuItem = mock(MenuItem.class);
		when(mockMenuItem.getItemId()).thenReturn(android.R.id.home);
		assertTrue(activity.onOptionsItemSelected(mockMenuItem));
	}
	
	@Test
	public void onOptionsItemSelectedDefaultCallsSuper() {
		MenuItem mockMenuItem = mock(MenuItem.class);
		when(mockMenuItem.getItemId()).thenReturn(0);
		assertFalse(activity.onOptionsItemSelected(mockMenuItem));
	}
	
	@Test
	public void verifyHomeUpIsEnabled() {
		activity.onCreate(null);
		verify(mockActionBar).setDisplayHomeAsUpEnabled(true);
	}
	
	@Test
	public void alarmsAreChagedWhenTimeIsChanged() {
		pref.edit().putString(Preferences.PREF_NOTIFICATION_TIME, "15:0").commit();
		WakefulIntentService.scheduleAlarms(new FreeAppNotificationListener(), activity);
		long before = SettingsUtils.getTimeInMillis(activity);
		
		ShadowPreferenceActivity shadowActivity = shadowOf(activity);
		AlarmManager alarmManager = (AlarmManager) shadowActivity.getApplicationContext().getSystemService(Context.ALARM_SERVICE);
		ShadowAlarmManager shadowAlarmManager = shadowOf(alarmManager);	
		
		ScheduledAlarm beforeScheduledAlarm = shadowAlarmManager.getNextScheduledAlarm();
		assertTrue(before - 1000L < beforeScheduledAlarm.triggerAtTime && beforeScheduledAlarm.triggerAtTime < before + 1000L);			
		
		pref.edit().putString(Preferences.PREF_NOTIFICATION_TIME, "16:0").commit();
		activity.onSharedPreferenceChanged(pref, Preferences.PREF_NOTIFICATION_TIME);
		long after = SettingsUtils.getTimeInMillis(activity);
		
		ScheduledAlarm afterScheduledAlarm = shadowAlarmManager.getNextScheduledAlarm();
		assertFalse(before - 1000L < afterScheduledAlarm.triggerAtTime && afterScheduledAlarm.triggerAtTime < before + 1000L);
		assertTrue(after - 1000L < afterScheduledAlarm.triggerAtTime && afterScheduledAlarm.triggerAtTime < after + 1000L);		
	}
	
	@Test
	public void alarmsAreEnabledIfPreferenceEnabled() {
		ShadowPreferenceActivity shadowActivity = shadowOf(activity);
		AlarmManager alarmManager = (AlarmManager) shadowActivity.getApplicationContext().getSystemService(Context.ALARM_SERVICE);
		ShadowAlarmManager shadowAlarmManager = shadowOf(alarmManager);	
		
		ScheduledAlarm beforeScheduledAlarm = shadowAlarmManager.getNextScheduledAlarm();
		assertNull(beforeScheduledAlarm);
		
		pref.edit().putBoolean(Preferences.PREF_ENABLED, true).commit();
		activity.onSharedPreferenceChanged(pref, Preferences.PREF_ENABLED);
		
		ScheduledAlarm afterScheduledAlarm = shadowAlarmManager.getNextScheduledAlarm();
		assertNotNull(afterScheduledAlarm);
	}
	
	@Test
	public void alarmsAreDisabledIfPreferenceEnabled() {
		WakefulIntentService.scheduleAlarms(new FreeAppNotificationListener(), activity);
		ShadowPreferenceActivity shadowActivity = shadowOf(activity);
		AlarmManager alarmManager = (AlarmManager) shadowActivity.getApplicationContext().getSystemService(Context.ALARM_SERVICE);
		ShadowAlarmManager shadowAlarmManager = shadowOf(alarmManager);	
		
		ScheduledAlarm beforeScheduledAlarm = shadowAlarmManager.getNextScheduledAlarm();
		assertNotNull(beforeScheduledAlarm);
		
		pref.edit().putBoolean(Preferences.PREF_ENABLED, false).commit();
		activity.onSharedPreferenceChanged(pref, Preferences.PREF_ENABLED);
		
		ScheduledAlarm afterScheduledAlarm = shadowAlarmManager.getNextScheduledAlarm();
		assertNull(afterScheduledAlarm);
	}

	private void setSdkVersion(int version) {
		Robolectric.Reflection.setFinalStaticField(Build.VERSION.class, "SDK_INT", version);
	}
	
	

}
