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

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.os.SystemClock;
import android.util.Log;

import com.skylark95.amazonfreenotify.util.Logger;

public class ConnectivityReceiver extends BroadcastReceiver {
    
    private static final long TIMEOUT = 300000L;
    private static final String TAG = Logger.getTag(ConnectivityReceiver.class);

    @Override
    public void onReceive(Context context, Intent intent) {
        if (ConnectivityManager.CONNECTIVITY_ACTION.equals(intent.getAction())) {
            Log.d(TAG, ConnectivityManager.CONNECTIVITY_ACTION);
            setEnabled(context, false);
            if (SystemClock.elapsedRealtime() < TIMEOUT) {
                OnBootNotificationHandler.handleNotification(context);
            } else {
                Log.w(TAG, "ConnectivityReceiver has timed out");
            }
        }        
    }

    public static void setEnabled(Context context, boolean enabled) {
        ComponentName componentName = new ComponentName(context, ConnectivityReceiver.class);
        PackageManager packageManager = context.getPackageManager();
        if (enabled) {
            packageManager.setComponentEnabledSetting(componentName, PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);
            Log.v(TAG, "enabled");
        } else {
            packageManager.setComponentEnabledSetting(componentName, PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
            Log.v(TAG, "disabled");
        }
    }

}
