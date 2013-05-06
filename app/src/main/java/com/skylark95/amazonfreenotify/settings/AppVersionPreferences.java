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

package com.skylark95.amazonfreenotify.settings;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.util.Log;

import com.skylark95.amazonfreenotify.util.Logger;

public final class AppVersionPreferences {
    
    public static final String PREF_APP_VERSION_CODE = "_app_version_code";
    
    private static final String TAG = Logger.getTag(AppVersionPreferences.class);
    
    private AppVersionPreferences() {
    }
    
    public static boolean isAppUpdate(Context context) {
        SharedPreferences pref = getAppVersionPref(context);
        int savedVersion = pref.getInt(PREF_APP_VERSION_CODE, 0);
        int currentVersion = getVersionCode(context);
        if (currentVersion > savedVersion) {
            Log.v(TAG, "isAppUpdate = true");
            updateAppVersion(context);
            return true;  
        } 
        
        Log.v(TAG, "isAppUpdate = false");
        return false;             
    }
    
    private static void updateAppVersion(Context context) {
        SharedPreferences pref = getAppVersionPref(context);
        pref.edit().putInt(PREF_APP_VERSION_CODE, getVersionCode(context)).commit();
    }
    
    private static SharedPreferences getAppVersionPref(Context context) {
        return context.getSharedPreferences(PREF_APP_VERSION_CODE, Context.MODE_PRIVATE);
    }
    
    public static String getVersion(Context context) {
        Log.v(TAG, "Getting application version");
        String result = "N/A";
        try {
            PackageInfo info = getPackageInfo(context);

            result = info.versionName;
        } catch (NameNotFoundException e) {
            Log.w(TAG, "WARNING Unable to get application version: " + e.getMessage());
        }

        return result;
    }

    public static int getVersionCode(Context context) {
        Log.v(TAG, "Getting application versionCode");
        int result = 0;
        try {
            PackageInfo info = getPackageInfo(context);

            result = info.versionCode;
        } catch (NameNotFoundException e) {
            Log.w(TAG, "WARNING Unable to get application versionCode: " + e.getMessage());
        }

        return result;
    }

    private static PackageInfo getPackageInfo(Context context) throws NameNotFoundException {
        PackageManager manager = context.getPackageManager();
        return manager.getPackageInfo(context.getPackageName(), 0);
    }

}
