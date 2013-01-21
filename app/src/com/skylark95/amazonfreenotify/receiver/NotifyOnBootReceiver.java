package com.skylark95.amazonfreenotify.receiver;

import com.commonsware.cwac.wakeful.WakefulIntentService;
import com.skylark95.amazonfreenotify.service.FreeAppNotificationService;
import com.skylark95.amazonfreenotify.settings.Preferences;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class NotifyOnBootReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		if (Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())) {
			showNotificationIfEnabled(context);
		}
	}

	private void showNotificationIfEnabled(Context context) {
		SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
		if (pref.getBoolean(Preferences.PREF_SHOW_ON_BOOT, true)) {
			WakefulIntentService.sendWakefulWork(context, FreeAppNotificationService.class);
		}
	}

}
