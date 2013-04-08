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

package com.skylark95.amazonfreenotify.service;

import android.content.Intent;
import android.util.Log;

import com.commonsware.cwac.wakeful.WakefulIntentService;
import com.skylark95.amazonfreenotify.net.AppDataReaderImpl;
import com.skylark95.amazonfreenotify.notification.FreeAppNotification;
import com.skylark95.amazonfreenotify.notification.FreeAppNotificationFactory;
import com.skylark95.amazonfreenotify.settings.SettingsUtils;
import com.skylark95.amazonfreenotify.util.Logger;

public class FreeAppNotificationService extends WakefulIntentService {
	
	private static final String TAG = Logger.getTag(FreeAppNotificationService.class);

	public FreeAppNotificationService() {
		super("FreeAppNotificationService");
	}

	@Override
	protected void doWakefulWork(Intent intent) {
		Log.v(TAG, "ENTER - doWakefulWork()");
		if (SettingsUtils.isTodayChecked(this)) {
			Log.d(TAG, "Today is checked");
			FreeAppNotification notification = FreeAppNotificationFactory.buildNotification(this, new AppDataReaderImpl());
			notification.showNotificationIfNecessary();
		}
		Log.v(TAG, "EXIT - doWakefulWork()");
	}	

}
