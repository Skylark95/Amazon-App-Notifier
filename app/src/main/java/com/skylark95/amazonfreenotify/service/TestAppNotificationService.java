package com.skylark95.amazonfreenotify.service;

import android.content.Intent;
import android.util.Log;

import com.commonsware.cwac.wakeful.WakefulIntentService;
import com.skylark95.amazonfreenotify.net.AppDataReaderImpl;
import com.skylark95.amazonfreenotify.notification.FreeAppNotification;
import com.skylark95.amazonfreenotify.notification.FreeAppNotificationFactory;
import com.skylark95.amazonfreenotify.util.Logger;

public class TestAppNotificationService extends WakefulIntentService {
	
	private static final String TAG = Logger.getTag(TestAppNotificationService.class);

	public TestAppNotificationService() {
		super("TestAppNotificationService");
	}

	@Override
	protected void doWakefulWork(Intent intent) {
		Log.v(TAG, "Enter - doWakefulWork()");
		FreeAppNotification notification = FreeAppNotificationFactory.buildNotification(this, new AppDataReaderImpl());
		notification.showNotificationReguardless();
		Log.v(TAG, "EXIT - doWakefulWork()");
	}

	

}
