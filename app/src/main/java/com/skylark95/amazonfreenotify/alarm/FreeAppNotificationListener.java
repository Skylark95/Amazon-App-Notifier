package com.skylark95.amazonfreenotify.alarm;

import java.util.Date;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.util.Log;

import com.commonsware.cwac.wakeful.WakefulIntentService;
import com.skylark95.amazonfreenotify.service.FreeAppNotificationService;
import com.skylark95.amazonfreenotify.settings.SettingsUtils;
import com.skylark95.amazonfreenotify.util.Logger;

public class FreeAppNotificationListener implements WakefulIntentService.AlarmListener {

	private static final String TAG = Logger.getTag(FreeAppNotificationListener.class);
	
	@Override
	public long getMaxAge() {
		return AlarmManager.INTERVAL_FIFTEEN_MINUTES;
	}

	@Override
	public void scheduleAlarms(AlarmManager manager, PendingIntent pendingIntent, Context context) {
		long time = SettingsUtils.getTimeInMillis(context);
		manager.setRepeating(AlarmManager.RTC_WAKEUP, time, AlarmManager.INTERVAL_DAY, pendingIntent);
		
		Date alarmDate = new Date(time);
		Log.i(TAG, "Scheduled Repeating Alarm for " + alarmDate.toString());
	}

	@Override
	public void sendWakefulWork(Context context) {
		Log.v(TAG, "ENTER - sendWakefulWork()");
		WakefulIntentService.sendWakefulWork(context, FreeAppNotificationService.class);
		Log.v(TAG, "EXIT - sendWakefulWork()");
	}

}
