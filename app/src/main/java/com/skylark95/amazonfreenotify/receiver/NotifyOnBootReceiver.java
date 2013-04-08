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

import com.commonsware.cwac.wakeful.WakefulIntentService;
import com.skylark95.amazonfreenotify.service.FreeAppNotificationService;
import com.skylark95.amazonfreenotify.settings.Preferences;
import com.skylark95.amazonfreenotify.util.Logger;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

public class NotifyOnBootReceiver extends BroadcastReceiver {
	
	private static final String TAG = Logger.getTag(NotifyOnBootReceiver.class);

	@Override
	public void onReceive(Context context, Intent intent) {
		if (Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())) {
			Log.d(TAG, "On Boot");
			showNotificationIfEnabled(context);
		}
	}

	private void showNotificationIfEnabled(Context context) {
		SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
		if (pref.getBoolean(Preferences.PREF_SHOW_ON_BOOT, true)) {
			Log.i(TAG, "Show on boot notification");
			WakefulIntentService.sendWakefulWork(context, FreeAppNotificationService.class);
		}
	}

}
