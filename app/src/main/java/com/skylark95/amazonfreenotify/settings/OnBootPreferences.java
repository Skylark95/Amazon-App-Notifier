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

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.skylark95.amazonfreenotify.util.Logger;

/**
 * Class is a workaround for the CONNECTIVITY_ACTION intent being sent twice for some devices
 * 
 * @author Derek
 *
 */
public final class OnBootPreferences {

    public static final String PREF_IS_ON_BOOT = "_is_on_boot";
    public static final String TAG = Logger.getTag(OnBootPreferences.class);

    private OnBootPreferences() {
    }
    
    public static boolean isOnBoot(Context context) {
        SharedPreferences pref = getOnBootPreferences(context);
        boolean onBoot = pref.getBoolean(PREF_IS_ON_BOOT, false);
        Log.v(TAG, PREF_IS_ON_BOOT + " = " + onBoot);
        return onBoot;
    }

    public static void setOnBoot(Context context, boolean onBoot) {
        SharedPreferences pref = getOnBootPreferences(context);
        pref.edit().putBoolean(PREF_IS_ON_BOOT, onBoot).commit();
    }

    private static SharedPreferences getOnBootPreferences(Context context) {
        return context.getSharedPreferences(PREF_IS_ON_BOOT, Context.MODE_PRIVATE);
    }

}
