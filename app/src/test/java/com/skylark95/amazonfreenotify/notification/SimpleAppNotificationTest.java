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
import android.content.SharedPreferences;

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

	private void buildWithSimpleTitle() {
		freeAppNotification = new SimpleAppNotification(context, mockPendingIntent, contentText);
	}

	private void buildWithCustomTitle() {
		freeAppNotification = new SimpleAppNotification(context, mockPendingIntent, contentTitle, contentText);
	}

}
