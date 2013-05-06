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

package com.skylark95.amazonfreenotify.receiver;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.commonsware.cwac.wakeful.WakefulIntentService;
import com.skylark95.amazonfreenotify.net.NetworkUtils;
import com.skylark95.amazonfreenotify.service.FreeAppNotificationService;
import com.skylark95.amazonfreenotify.settings.OnBootPreferences;
import com.skylark95.amazonfreenotify.settings.Preferences;
import com.skylark95.amazonfreenotify.util.Logger;

/*
 * TODO Refactor into service
 */
public final class OnBootNotificationHandler {
    
    private static final String TAG = Logger.getTag(OnBootNotificationHandler.class);
    
    private OnBootNotificationHandler() {
    }
    
    static void timeoutNotification(Context context) {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        if (isEnabled(pref) && isOnBoot(context)) {
            showNotification(context);
        }
    }
    
    static void handleOnBootNotification(Context context) {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        if (isEnabled(pref) && isOnBoot(context)) {
            if (networkConditionsMet(context, pref)) {
                showNotification(context);
            } else {                
                delayNotification(context);
            } 
        } else {
            setPrefOnBoot(context, false);
        }
    }
    
    static void cancelTimeout(Context context) {
        Log.v(TAG, "Cancel Timeout alarm");
        Intent intent = new Intent(context, OnBootTimeoutReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(pendingIntent);
    }
    
    static void scheduleTimeout(Context context) {
        Log.v(TAG, "Schedule Timeout alarm");
        Intent intent = new Intent(context, OnBootTimeoutReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, ConnectivityReceiver.TIMEOUT, pendingIntent);
    }

    private static void showNotification(Context context) {
        Log.i(TAG, "Show on boot notification");
        setPrefOnBoot(context, false);
        WakefulIntentService.sendWakefulWork(context, FreeAppNotificationService.class);
    }

    private static void delayNotification(Context context) {
        Log.i(TAG, "Not conected, delaying notification");
        ConnectivityReceiver.setEnabled(context, true);
        scheduleTimeout(context);                
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
