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

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.xtremelabs.robolectric.RobolectricTestRunner;
import com.xtremelabs.robolectric.shadows.ShadowNotificationManager;

@RunWith(RobolectricTestRunner.class)
public class TestAppNotificationServiceTest {

	private static final String NOTIFICATION_TAG = "FreeAppNotification";
	
	private TestAppNotificationService service;
	private Context context;
	private Intent intent;	

	@Before
	public void setUp() {
		service = new TestAppNotificationService();
		context = new SherlockFragmentActivity();
		intent = new Intent();
	}

	@Test
	public void alwaysShowsNotification() {
		service.doWakefulWork(intent);
		NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
		ShadowNotificationManager shadowManager = shadowOf(manager);
		Notification notification = shadowManager.getNotification(NOTIFICATION_TAG);
		assertNotNull(notification);
	}

}
