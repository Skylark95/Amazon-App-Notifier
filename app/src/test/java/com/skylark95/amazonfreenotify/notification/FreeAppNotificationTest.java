package com.skylark95.amazonfreenotify.notification;

import static com.xtremelabs.robolectric.Robolectric.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.skylark95.amazonfreenotify.R;
import com.skylark95.amazonfreenotify.settings.Preferences;
import com.xtremelabs.robolectric.RobolectricTestRunner;
import com.xtremelabs.robolectric.shadows.ShadowNotificationManager;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat.Builder;

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
	public void baseBuilderDoesSetSmallIcon() {		
		Builder builder = freeAppNotification.getBaseBuilder(mockPendingIntent);
		assertEquals(R.drawable.notify_icon, builder.build().icon);
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

	private ShadowNotificationManager buildShadowNotificationManager() {
		NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
		return shadowOf(manager);
	}

}
