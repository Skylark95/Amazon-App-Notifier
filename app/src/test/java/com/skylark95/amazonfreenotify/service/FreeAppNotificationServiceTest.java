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

package com.skylark95.amazonfreenotify.service;

import static com.xtremelabs.robolectric.Robolectric.*;
import static org.junit.Assert.*;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.skylark95.amazonfreenotify.util.TestUtils;
import com.xtremelabs.robolectric.RobolectricTestRunner;
import com.xtremelabs.robolectric.shadows.ShadowNotificationManager;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

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
