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
