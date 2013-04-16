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

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.commonsware.cwac.wakeful.WakefulIntentService;
import com.skylark95.amazonfreenotify.net.NetworkUtils;
import com.skylark95.amazonfreenotify.service.FreeAppNotificationService;
import com.skylark95.amazonfreenotify.settings.OnBootPreferences;
import com.skylark95.amazonfreenotify.settings.Preferences;
import com.skylark95.amazonfreenotify.util.Logger;

public final class OnBootNotificationHandler {
    
    private static final String TAG = Logger.getTag(OnBootNotificationHandler.class);
    
    private OnBootNotificationHandler() {
    }
    
    static void handleNotification(Context context) {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        if (isEnabled(pref) && isOnBoot(context)) {
            if (networkConditionsMet(context, pref)) {
                Log.i(TAG, "Show on boot notification");
                setPrefOnBoot(context, false);
                WakefulIntentService.sendWakefulWork(context, FreeAppNotificationService.class);
            } else {
                Log.i(TAG, "Not conected, delaying notification");
                ConnectivityReceiver.setEnabled(context, true);
            } 
        } else {
            setPrefOnBoot(context, false);
        }
    }

    private static void setPrefOnBoot(Context context, boolean bootCompleted) {
        OnBootPreferences.setOnBoot(context, bootCompleted);
    }

    private static boolean isEnabled(SharedPreferences pref) {
        return pref.getBoolean(Preferences.PREF_SHOW_ON_BOOT, true);
    }

    private static boolean isOnBoot(Context context) {
        return OnBootPreferences.isOnBoot(context);
    }

    private static boolean networkConditionsMet(Context context, SharedPreferences pref) {
        if (pref.getBoolean(Preferences.PREF_SHOW_NAME_PRICE, true)) {
            return NetworkUtils.isDeviceOnline(context);
        }
        return true; 
    }

}
