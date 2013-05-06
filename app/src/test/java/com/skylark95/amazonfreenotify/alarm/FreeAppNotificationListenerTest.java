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

package com.skylark95.amazonfreenotify.alarm;

import static com.xtremelabs.robolectric.Robolectric.*;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.commonsware.cwac.wakeful.AlarmReceiver;
import com.commonsware.cwac.wakeful.WakefulIntentService;
import com.skylark95.amazonfreenotify.service.FreeAppNotificationService;
import com.skylark95.amazonfreenotify.settings.SettingsUtils;
import com.xtremelabs.robolectric.RobolectricTestRunner;
import com.xtremelabs.robolectric.shadows.ShadowAlarmManager;
import com.xtremelabs.robolectric.shadows.ShadowAlarmManager.ScheduledAlarm;
import com.xtremelabs.robolectric.shadows.ShadowFragmentActivity;
import com.xtremelabs.robolectric.shadows.ShadowIntent;

@RunWith(RobolectricTestRunner.class)
public class FreeAppNotificationListenerTest {

	private WakefulIntentService.AlarmListener alarmListener;
	
	@Before
	public void setUp() {
		alarmListener = new FreeAppNotificationListener();
	}

	@Test
	public void correctMaxAge() {
		assertEquals(AlarmManager.INTERVAL_FIFTEEN_MINUTES, alarmListener.getMaxAge());
	}
	
	@Test
	public void scheduleAlarmsSchedulesCorrectRepeatingAlarm() {
		SherlockFragmentActivity activity = new SherlockFragmentActivity();
		AlarmManager alarmManager = (AlarmManager) activity.getApplicationContext().getSystemService(Context.ALARM_SERVICE);
		Intent i = new Intent(activity, AlarmReceiver.class);
	    PendingIntent pi = PendingIntent.getBroadcast(activity, 0, i, 0);
	    
	    long time = SettingsUtils.getTimeInMillis(activity);
	    alarmListener.scheduleAlarms(alarmManager, pi, activity);
	    
	    ShadowAlarmManager shadowAlarmManager = shadowOf(alarmManager);
	    ScheduledAlarm alarm = shadowAlarmManager.getNextScheduledAlarm();	
	    
	    assertEquals(AlarmManager.INTERVAL_DAY, alarm.interval);
	    assertEquals(AlarmManager.RTC_WAKEUP, alarm.type);	    
	    
	    // Add 1 second buffer time to prevent failing tests...
	    assertTrue(time - 1000 < alarm.triggerAtTime && alarm.triggerAtTime < time + 1000);
	}
	
	@Test
	public void sendWakefulWorkStartsService() {
		SherlockFragmentActivity activity = new SherlockFragmentActivity();
		alarmListener.sendWakefulWork(activity);
		
		ShadowFragmentActivity shadowFragmentActivity = shadowOf(activity);
		Intent nextService = shadowFragmentActivity.getNextStartedService();
		ShadowIntent shadowIntent = shadowOf(nextService);
		
		assertEquals(shadowIntent.getComponent().getClassName(), FreeAppNotificationService.class.getName());		
	}

}
