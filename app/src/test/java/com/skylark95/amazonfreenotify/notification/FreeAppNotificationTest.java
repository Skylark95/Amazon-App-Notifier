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
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat.Builder;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.skylark95.amazonfreenotify.R;
import com.skylark95.amazonfreenotify.settings.Preferences;
import com.xtremelabs.robolectric.RobolectricTestRunner;
import com.xtremelabs.robolectric.shadows.ShadowNotificationManager;

@RunWith(RobolectricTestRunner.class)
public class FreeAppNotificationTest {

	private static final String NOTIFICATION_TAG = "FreeAppNotification";

	private static class FreeAppNotificationTester extends FreeAppNotification {

		private Notification notification;
		private boolean show;
		
		protected FreeAppNotificationTester(Context context, Notification notification) {
			this(context, notification, false);
		}
		
		protected FreeAppNotificationTester(Context context, Notification notification, boolean show) {
			super(context);
			this.notification = notification;	
		}

		@Override
		protected Notification buildNotification() {
			return notification;
		}

		@Override
		protected boolean shouldShowNotification() {
			return show;
		}

		public void setShow(boolean show) {
			this.show = show;
		}
		
	}
	
	private FreeAppNotificationTester freeAppNotification;
	private Context context;
	private PendingIntent mockPendingIntent;
	private SharedPreferences pref;
	private Notification mockNotification;
	
	@Before
	public void setUp() {
		context = new SherlockFragmentActivity();
		mockNotification = mock(Notification.class);
		freeAppNotification = new FreeAppNotificationTester(context, mockNotification);
		mockPendingIntent = mock(PendingIntent.class);
		pref = PreferenceManager.getDefaultSharedPreferences(context);
	}

	@Test
	public void baseBuilderDoesOrangeIcon() {
	    givenNotificationIconIs(NotificationIcon.ORANGE_ICON);
		Builder builder = freeAppNotification.getBaseBuilder(mockPendingIntent);
		assertEquals(NotificationIcon.ORANGE_ICON.getResourceId(), builder.build().icon);
	}
	
	@Test
	public void baseBuilderDoesBlueIcon() {
	    givenNotificationIconIs(NotificationIcon.BLUE_ICON);
	    Builder builder = freeAppNotification.getBaseBuilder(mockPendingIntent);
	    assertEquals(NotificationIcon.BLUE_ICON.getResourceId(), builder.build().icon);
	}
	
	@Test
    public void baseBuilderDoesGreenIcon() {
        givenNotificationIconIs(NotificationIcon.GREEN_ICON);
        Builder builder = freeAppNotification.getBaseBuilder(mockPendingIntent);
        assertEquals(NotificationIcon.GREEN_ICON.getResourceId(), builder.build().icon);
    }
	
	@Test
    public void baseBuilderDoesRedIcon() {
        givenNotificationIconIs(NotificationIcon.RED_ICON);
        Builder builder = freeAppNotification.getBaseBuilder(mockPendingIntent);
        assertEquals(NotificationIcon.RED_ICON.getResourceId(), builder.build().icon);
    }
	
	@Test
	public void baseBuilderDoesGrayscaleIcon() {
	    givenNotificationIconIs(NotificationIcon.GRAYSCALE_ICON);
	    Builder builder = freeAppNotification.getBaseBuilder(mockPendingIntent);
	    assertEquals(NotificationIcon.GRAYSCALE_ICON.getResourceId(), builder.build().icon);
	}
	
	@Test
	public void baseBuilderDoesTransparentIcon() {
	    givenNotificationIconIs(NotificationIcon.TRANSPARENT_ICON);
	    Builder builder = freeAppNotification.getBaseBuilder(mockPendingIntent);
	    assertEquals(NotificationIcon.TRANSPARENT_ICON.getResourceId(), builder.build().icon);
	}	

    @Test
	public void baseBuilderDoesSetContentIntent() {		
		Builder builder = freeAppNotification.getBaseBuilder(mockPendingIntent);
		assertEquals(mockPendingIntent, builder.build().contentIntent);
	}
	
	@Test
	public void baseBuilderDoesSetAutoCancel() {		
		Builder builder = freeAppNotification.getBaseBuilder(mockPendingIntent);
		assertEquals(Notification.FLAG_AUTO_CANCEL, builder.build().flags);
	}
	
	@Test
	public void baseBuilderDoesSetDefaultsIfPreferencesNotSet() {		
		Builder builder = freeAppNotification.getBaseBuilder(mockPendingIntent);
		assertEquals(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE, builder.build().defaults);
	}
	
	@Test
	public void baseBuilderDoesNotSetDefaultSoundIfPrefNoSoundAndDefaultVibrate() {
		pref.edit().putBoolean(Preferences.PREF_PLAY_NOTIFICATION_SOUND, false).commit();
		Builder builder = freeAppNotification.getBaseBuilder(mockPendingIntent);
		assertEquals(Notification.DEFAULT_VIBRATE, builder.build().defaults);
	}
	
	@Test
	public void baseBuilderDoesNotSetAnyDefaultSoundIfPrefNoSoundAndPrefVibrateFalse() {
		pref.edit().putBoolean(Preferences.PREF_PLAY_NOTIFICATION_SOUND, false)
		.putBoolean(Preferences.PREF_VIBRATE, false).commit();
		Builder builder = freeAppNotification.getBaseBuilder(mockPendingIntent);
		assertEquals(0, builder.build().defaults);
	}
	
	@Test
	public void baseBuilderDoesSetSound() {
		String notificationUri = "notificationUri";
		pref.edit().putString(Preferences.PREF_NOTIFICATION_SOUND, notificationUri).commit();
		Builder builder = freeAppNotification.getBaseBuilder(mockPendingIntent);
		assertEquals(Uri.parse(notificationUri), builder.build().sound);
	}
	
	@Test
	public void baseBuilderDoesSetSoundNotSetSoundIfNoSoundPref() {
		String notificationUri = "notificationUri";
		pref.edit().putString(Preferences.PREF_NOTIFICATION_SOUND, notificationUri)
		.putBoolean(Preferences.PREF_PLAY_NOTIFICATION_SOUND, false).commit();
		Builder builder = freeAppNotification.getBaseBuilder(mockPendingIntent);
		assertNull(builder.build().sound);
	}
	
	@Test
	public void baseBuilderDoesSetDefaultVibrateIfSoundSetAndVibrateDefaultTrue() {
		String notificationUri = "notificationUri";
		pref.edit().putString(Preferences.PREF_NOTIFICATION_SOUND, notificationUri).commit();
		Builder builder = freeAppNotification.getBaseBuilder(mockPendingIntent);
		assertEquals(Notification.DEFAULT_VIBRATE, builder.build().defaults);
	}
	
	@Test
	public void baseBuilderDoesSetNoDefaultsIfSoundSetAndVibrateFalse() {
		String notificationUri = "notificationUri";
		pref.edit().putString(Preferences.PREF_NOTIFICATION_SOUND, notificationUri)
		.putBoolean(Preferences.PREF_VIBRATE, false).commit();
		Builder builder = freeAppNotification.getBaseBuilder(mockPendingIntent);
		assertEquals(0, builder.build().defaults);
	}
	
	@Test
	public void showNotificationReguardlessShowsNotificationWhenShowNotificationFalse() {
		freeAppNotification.setShow(false);
		freeAppNotification.showNotificationReguardless();
		ShadowNotificationManager shadowManager = buildShadowNotificationManager();
		assertEquals(mockNotification, shadowManager.getNotification(NOTIFICATION_TAG));
	}

	@Test
	public void showNotificationReguardlessShowsNotificationWhenShowNotificationTrue() {
		freeAppNotification.setShow(true);
		freeAppNotification.showNotificationReguardless();
		ShadowNotificationManager shadowManager = buildShadowNotificationManager();
		assertEquals(mockNotification, shadowManager.getNotification(NOTIFICATION_TAG));
	}
	
	@Test
	public void showNotificationIfNecessaryShowsNotificationWhenShowNotificationTrue() {
		freeAppNotification.setShow(true);
		freeAppNotification.showNotificationIfNecessary();
		ShadowNotificationManager shadowManager = buildShadowNotificationManager();
		assertEquals(mockNotification, shadowManager.getNotification(NOTIFICATION_TAG));
	}
	
	@Test
	public void showNotificationIfNecessaryDoesNotShowNotificationWhenShowNotificationFalse() {
		freeAppNotification.setShow(false);
		freeAppNotification.showNotificationIfNecessary();
		ShadowNotificationManager shadowManager = buildShadowNotificationManager();
		assertNull(shadowManager.getNotification(NOTIFICATION_TAG));
	}

	private void givenNotificationIconIs(NotificationIcon notificationIcon) {
        pref.edit().putString(Preferences.PREF_NOTIFY_ICON_COLOR, notificationIcon.getPrefValue()).commit();
    }

    private ShadowNotificationManager buildShadowNotificationManager() {
		NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
		return shadowOf(manager);
	}

}
