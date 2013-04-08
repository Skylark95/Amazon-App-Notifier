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

package com.skylark95.amazonfreenotify.alarm;

import java.util.Date;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.util.Log;

import com.commonsware.cwac.wakeful.WakefulIntentService;
import com.skylark95.amazonfreenotify.service.FreeAppNotificationService;
import com.skylark95.amazonfreenotify.settings.SettingsUtils;
import com.skylark95.amazonfreenotify.util.Logger;

public class FreeAppNotificationListener implements WakefulIntentService.AlarmListener {

	private static final String TAG = Logger.getTag(FreeAppNotificationListener.class);
	
	@Override
	public long getMaxAge() {
		return AlarmManager.INTERVAL_FIFTEEN_MINUTES;
	}

	@Override
	public void scheduleAlarms(AlarmManager manager, PendingIntent pendingIntent, Context context) {
		long time = SettingsUtils.getTimeInMillis(context);
		manager.setRepeating(AlarmManager.RTC_WAKEUP, time, AlarmManager.INTERVAL_DAY, pendingIntent);
		
		Date alarmDate = new Date(time);
		Log.i(TAG, "Scheduled Repeating Alarm for " + alarmDate.toString());
	}

	@Override
	public void sendWakefulWork(Context context) {
		Log.v(TAG, "ENTER - sendWakefulWork()");
		WakefulIntentService.sendWakefulWork(context, FreeAppNotificationService.class);
		Log.v(TAG, "EXIT - sendWakefulWork()");
	}

}
