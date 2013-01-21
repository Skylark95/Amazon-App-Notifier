package com.skylark95.amazonfreenotify.service;

import android.content.Intent;
import android.util.Log;

import com.commonsware.cwac.wakeful.WakefulIntentService;
import com.skylark95.amazonfreenotify.notification.FreeAppNotification;
import com.skylark95.amazonfreenotify.notification.FreeAppNotificationFactory;
import com.skylark95.amazonfreenotify.util.Logger;
import com.skylark95.amazonfreenotify.util.SettingsUtils;

public class FreeAppNotificationService extends WakefulIntentService {
	
	private static final String TAG = Logger.getTag(FreeAppNotificationService.class);

	public FreeAppNotificationService() {
		super("FreeAppNotificationService");
	}

	@Override
	protected void doWakefulWork(Intent intent) {
		Log.v(TAG, "ENTER - doWakefulWork()");
		if (SettingsUtils.isTodayChecked(this)) {
			FreeAppNotification notification = FreeAppNotificationFactory.buildNotification(this);
			notification.showNotificationIfNecessary();
		}
		Log.v(TAG, "EXIT - doWakefulWork()");
	}	

}
