/*
 * This file is part of Amazon App Notifier
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

package com.skylark95.amazonfreenotify.notification;

import static com.xtremelabs.robolectric.Robolectric.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.skylark95.amazonfreenotify.R;
import com.xtremelabs.robolectric.RobolectricTestRunner;
import com.xtremelabs.robolectric.shadows.ShadowNotification;

@RunWith(RobolectricTestRunner.class)
public class SimpleAppNotificationTest {

	private FreeAppNotification freeAppNotification;
	private Context context;
	private PendingIntent mockPendingIntent;
	private String contentTitle;
	private String contentText;
	private String defaultTitle;
	
	
	@Before
	public void setUp() {
		context = new SherlockFragmentActivity();
		mockPendingIntent = mock(PendingIntent.class);
		contentText = "contentText";
		contentTitle = "contentTitle";
		freeAppNotification = null;
		defaultTitle = context.getString(R.string.notification_simple_title);
	}
	
	@Test
	public void buidingWithSimpleTitleSetsDefaultTitle() {
		buildWithSimpleTitle();
		Notification notification = freeAppNotification.buildNotification();
		ShadowNotification shadowNotification = shadowOf(notification);
		assertEquals(defaultTitle, shadowNotification.getLatestEventInfo().getContentTitle());
	}
	
	@Test
	public void buidingWithCustomTitleSetsCustomTitle() {
		buildWithCustomTitle();
		Notification notification = freeAppNotification.buildNotification();
		ShadowNotification shadowNotification = shadowOf(notification);
		assertEquals(contentTitle, shadowNotification.getLatestEventInfo().getContentTitle());
	}
	
	@Test
	public void buidingWithSimpleTitleSetsContentText() {
		buildWithSimpleTitle();
		Notification notification = freeAppNotification.buildNotification();
		ShadowNotification shadowNotification = shadowOf(notification);
		assertEquals(contentText, shadowNotification.getLatestEventInfo().getContentText());
	}
	
	@Test
	public void buidingWithSimpleTitleSetTickerWithDefaultTitle() {
		buildWithSimpleTitle();
		Notification notification = freeAppNotification.buildNotification();
		assertEquals(defaultTitle + '\n' + contentText, notification.tickerText);
	}
	
	@Test
	public void buidingWithCustomTitleSetTickerWithCustomTitle() {
		buildWithCustomTitle();
		Notification notification = freeAppNotification.buildNotification();
		assertEquals(contentTitle + '\n' + contentText, notification.tickerText);
	}
	
	@Test
	public void shouldShowNotificationIsTrue() {
		buildWithSimpleTitle();
		assertTrue(freeAppNotification.shouldShowNotification());
	}

	private void buildWithSimpleTitle() {
		freeAppNotification = new SimpleAppNotification(context, mockPendingIntent, contentText);
	}

	private void buildWithCustomTitle() {
		freeAppNotification = new SimpleAppNotification(context, mockPendingIntent, contentTitle, contentText);
	}

}
