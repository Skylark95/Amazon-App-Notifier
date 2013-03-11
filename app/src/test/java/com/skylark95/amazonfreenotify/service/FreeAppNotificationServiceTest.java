package com.skylark95.amazonfreenotify.service;

import static com.xtremelabs.robolectric.Robolectric.*;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.skylark95.amazonfreenotify.settings.PrefNotificationDays;
import com.skylark95.amazonfreenotify.util.TestUtils;
import com.xtremelabs.robolectric.RobolectricTestRunner;
import com.xtremelabs.robolectric.shadows.ShadowNotificationManager;

@RunWith(RobolectricTestRunner.class)
public class FreeAppNotificationServiceTest {
	
	private static final String NOTIFICATION_TAG = "FreeAppNotification";

	private FreeAppNotificationService service;
	private Context context;

	private Intent intent;
	

	@Before
	public void setUp() {
		service = new FreeAppNotificationService();
		context = new SherlockFragmentActivity();
		intent = new Intent();
	}

	@Test
	public void doesShowNotificationIfTodayIsChecked() {
		TestUtils.allDaysChecked(context, true);
		service.doWakefulWork(intent);
		NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
		ShadowNotificationManager shadowManager = shadowOf(manager);
		Notification notification = shadowManager.getNotification(NOTIFICATION_TAG);
		assertNotNull(notification);
	}
	
	@Test
	public void doesNotShowNotificationIfTodayIsNotChecked() {
		TestUtils.allDaysChecked(context, false);
		service.doWakefulWork(intent);
		NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
		ShadowNotificationManager shadowManager = shadowOf(manager);
		Notification notification = shadowManager.getNotification(NOTIFICATION_TAG);
		assertNull(notification);
	}

}
