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

import static org.junit.Assert.*;

import java.util.Calendar;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.xtremelabs.robolectric.RobolectricTestRunner;

@RunWith(RobolectricTestRunner.class)
public class SettingsUtilsTest {

	private Context context;
	private SharedPreferences pref;

	@Before
	public void setUp() {
		context = new SherlockFragmentActivity();
		pref = PreferenceManager.getDefaultSharedPreferences(context);
	}

	@Test
	public void canGetTimeDisplayValueBeforeMidnight() {
		pref.edit().putString(Preferences.PREF_NOTIFICATION_TIME, "23:59").commit();
		String expected = "11:59 PM";
		String actual = SettingsUtils.getTimeDisplayValue(context);
		assertEquals(expected, actual);
	}
	
	@Test
	public void canGetTimeDisplayValueMidnight() {
		pref.edit().putString(Preferences.PREF_NOTIFICATION_TIME, "0:0").commit();
		String expected = "12:00 AM";
		String actual = SettingsUtils.getTimeDisplayValue(context);
		assertEquals(expected, actual);
	}
	
	@Test
	public void canGetTimeDisplayValueAfterMidnight() {
		pref.edit().putString(Preferences.PREF_NOTIFICATION_TIME, "0:1").commit();
		String expected = "12:01 AM";
		String actual = SettingsUtils.getTimeDisplayValue(context);
		assertEquals(expected, actual);
	}
	
	@Test
	public void canGetTimeDisplayValueNoon() {
		pref.edit().putString(Preferences.PREF_NOTIFICATION_TIME, "12:0").commit();
		String expected = "12:00 PM";
		String actual = SettingsUtils.getTimeDisplayValue(context);
		assertEquals(expected, actual);
	}
	
	@Test
	public void canGetTimeDisplayValueBeforeNoon() {
		pref.edit().putString(Preferences.PREF_NOTIFICATION_TIME, "11:59").commit();
		String expected = "11:59 AM";
		String actual = SettingsUtils.getTimeDisplayValue(context);
		assertEquals(expected, actual);
	}
	
	@Test
	public void canGetTimeDisplayValueAfterNoon() {
		pref.edit().putString(Preferences.PREF_NOTIFICATION_TIME, "12:1").commit();
		String expected = "12:01 PM";
		String actual = SettingsUtils.getTimeDisplayValue(context);
		assertEquals(expected, actual);
	}
	
	@Test
	public void canGetTimeDisplayValueBeforeOne() {
		pref.edit().putString(Preferences.PREF_NOTIFICATION_TIME, "12:59").commit();
		String expected = "12:59 PM";
		String actual = SettingsUtils.getTimeDisplayValue(context);
		assertEquals(expected, actual);
	}
	
	@Test
	public void canGetTimeDisplayValueOne() {
		pref.edit().putString(Preferences.PREF_NOTIFICATION_TIME, "13:0").commit();
		String expected = "1:00 PM";
		String actual = SettingsUtils.getTimeDisplayValue(context);
		assertEquals(expected, actual);
	}
	
	@Test
	public void canGetTimeDisplayValueAfterOne() {
		pref.edit().putString(Preferences.PREF_NOTIFICATION_TIME, "13:1").commit();
		String expected = "1:01 PM";
		String actual = SettingsUtils.getTimeDisplayValue(context);
		assertEquals(expected, actual);
	}
	
	@Test
	public void canGetDaysDisplayValueAll() {
		allDaysChecked(true);
		String expected = "All";
		String actual = SettingsUtils.getDaysDisplayValue(context);
		assertEquals(expected, actual);
	}
	
	@Test
	public void canGetDaysDisplayValueWeekdays() {
		weekDaysChecked(true);
		weekendsChecked(false);
		String expected = "Weekdays";
		String actual = SettingsUtils.getDaysDisplayValue(context);
		assertEquals(expected, actual);
	}
	
	@Test
	public void canGetDaysDisplayValueWeekends() {
		weekDaysChecked(false);
		weekendsChecked(true);
		String expected = "Weekend";
		String actual = SettingsUtils.getDaysDisplayValue(context);
		assertEquals(expected, actual);
	}
	
	@Test
	public void canGetDaysDisplayValueNoDays() {
		allDaysChecked(false);
		String expected = "None Selected";
		String actual = SettingsUtils.getDaysDisplayValue(context);
		assertEquals(expected, actual);
	}
	
	@Test
	public void canGetDaysDisplayValueAllButMonday() {
		allDaysChecked(true);
		pref.edit().putBoolean(PrefNotificationDays.PREF_NOTIFICATION_DAYS_MONDAY, false).commit();
		String expected = new StringBuilder()
			.append(DayOfWeek.SUNDAY.getAbbrName()).append(", ")
			.append(DayOfWeek.TUESDAY.getAbbrName()).append(", ")
			.append(DayOfWeek.WEDNESDAY.getAbbrName()).append(", ")
			.append(DayOfWeek.THURSDAY.getAbbrName()).append(", ")
			.append(DayOfWeek.FRIDAY.getAbbrName()).append(", ")
			.append(DayOfWeek.SATURDAY.getAbbrName())
			.toString();
		String actual = SettingsUtils.getDaysDisplayValue(context);
		assertEquals(expected, actual);
	}
	@Test
	public void canGetDaysDisplayValueOnlyMonday() {
		allDaysChecked(false);
		pref.edit().putBoolean(PrefNotificationDays.PREF_NOTIFICATION_DAYS_MONDAY, true).commit();
		String expected = DayOfWeek.MONDAY.getAbbrName();
		String actual = SettingsUtils.getDaysDisplayValue(context);
		assertEquals(expected, actual);
	}
	
	/*
	 * No idea how to test if ringtone is not null as RingtoneManager is static
	 */
	
	@Test
	public void ringtoneDisplayValueNotSelectedIfNull() {
		pref.edit().putString(Preferences.PREF_NOTIFICATION_SOUND, null).commit();
		String expected = "Not Selected";
		String actual = SettingsUtils.getRingtoneDisplayValue(context);
		assertEquals(expected, actual);
	}	
	
	/**
	 * Note: Test will fail if current time is 11:59 PM
	 */
	@Test
	public void canGetTimeInMillisSameDay() {
		pref.edit().putString(Preferences.PREF_NOTIFICATION_TIME, "23:59").commit();
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 0);
		
		long expected = cal.getTimeInMillis();
		long actual = SettingsUtils.getTimeInMillis(context);
		
		// One second buffer
		assertTrue(expected - 1000L < actual && actual < expected + 1000L);
	}
	
	@Test
	public void canGetTimeInMillisNextDay() {
		pref.edit().putString(Preferences.PREF_NOTIFICATION_TIME, "0:0").commit();
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, 1);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		
		long expected = cal.getTimeInMillis();
		long actual = SettingsUtils.getTimeInMillis(context);
		
		// One second buffer
		assertTrue(expected - 1000L < actual && actual < expected + 1000L);
	}
	
	@Test
	public void isTodayCheckedTrue() {
		allDaysChecked(true);
		assertTrue(SettingsUtils.isTodayChecked(context));
	}
	
	@Test
	public void isTodayCheckedFalse() {
		allDaysChecked(false);
		assertFalse(SettingsUtils.isTodayChecked(context));
	}
	
	private void allDaysChecked(boolean checked) {
		weekDaysChecked(checked);
		weekendsChecked(checked);	
	}

	private void weekendsChecked(boolean checked) {
		pref.edit()
			.putBoolean(PrefNotificationDays.PREF_NOTIFICATION_DAYS_SUNDAY, checked)
			.putBoolean(PrefNotificationDays.PREF_NOTIFICATION_DAYS_SATURDAY, checked)
			.commit();		
	}

	private void weekDaysChecked(boolean checked) {
		pref.edit()
			.putBoolean(PrefNotificationDays.PREF_NOTIFICATION_DAYS_MONDAY, checked)
			.putBoolean(PrefNotificationDays.PREF_NOTIFICATION_DAYS_TUESDAY, checked)
			.putBoolean(PrefNotificationDays.PREF_NOTIFICATION_DAYS_WEDNESDAY, checked)
			.putBoolean(PrefNotificationDays.PREF_NOTIFICATION_DAYS_THURSDAY, checked)
			.putBoolean(PrefNotificationDays.PREF_NOTIFICATION_DAYS_FRIDAY, checked)
			.commit();			
	}

}
