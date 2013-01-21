package com.skylark95.amazonfreenotify.alarm;

import java.util.Calendar;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.commonsware.cwac.wakeful.WakefulIntentService;
import com.skylark95.amazonfreenotify.service.FreeAppNotificationService;
import com.skylark95.amazonfreenotify.settings.Preferences;
import com.skylark95.amazonfreenotify.util.Logger;
import com.skylark95.amazonfreenotify.util.SettingsUtils;

public class FreeAppNotificationListener implements WakefulIntentService.AlarmListener {

	private static final String TAG = Logger.getTag(FreeAppNotificationListener.class);
	
	@Override
	public long getMaxAge() {
		return AlarmManager.INTERVAL_FIFTEEN_MINUTES;
	}

	@Override
	public void scheduleAlarms(AlarmManager manager, PendingIntent pendingIntent, Context context) {
		SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
		
		if (pref.getBoolean(Preferences.PREF_ENABLED, true)) {
			long time = SettingsUtils.getTimeInMillis(context);
			manager.setRepeating(AlarmManager.RTC_WAKEUP, time, AlarmManager.INTERVAL_DAY, pendingIntent);
			
			Calendar cal = Calendar.getInstance();
			cal.setTimeInMillis(time);
			Log.i(TAG, "Scheduled Repeating Alarm for " + cal.getTime().toString());
		} else {
			Log.i(TAG, "Not Scheduling Alarms");
		}
	}

	@Override
	public void sendWakefulWork(Context context) {
		Log.v(TAG, "ENTER - sendWakefulWork()");
		WakefulIntentService.sendWakefulWork(context, FreeAppNotificationService.class);
		Log.v(TAG, "EXIT - sendWakefulWork()");
	}

}
