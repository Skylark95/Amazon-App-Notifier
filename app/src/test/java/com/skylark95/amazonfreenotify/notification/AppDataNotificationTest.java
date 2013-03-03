package com.skylark95.amazonfreenotify.notification;

import static com.xtremelabs.robolectric.Robolectric.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationCompat.Builder;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.skylark95.amazonfreenotify.beans.FreeAppData;
import com.skylark95.amazonfreenotify.net.AppDataReader;
import com.skylark95.amazonfreenotify.settings.Preferences;
import com.xtremelabs.robolectric.Robolectric;
import com.xtremelabs.robolectric.RobolectricTestRunner;
import com.xtremelabs.robolectric.shadows.ShadowNotification;

@RunWith(RobolectricTestRunner.class)
public class AppDataNotificationTest {
	
	private static final String CATEGORY_GAMES = "Games";
	private static final String CATEGORY_OTHER = "Other";

	private static class AppDataNotificationTester extends AppDataNotification {
	
		private NotificationCompat.Builder builder;
	
		protected AppDataNotificationTester(Context context, PendingIntent pendingIntent, FreeAppData freeAppData) {
			super(context, pendingIntent, freeAppData);
		}
	
		@Override
		protected Builder getBaseBuilder(PendingIntent pendingIntent) {
			return builder;
		}
	
		public void setBuilder(NotificationCompat.Builder builder) {
			this.builder = builder;
		}
		
	}

	private FreeAppNotification freeAppNotification;
	private Context context;
	private PendingIntent mockPendingIntent;
	private FreeAppData freeAppData;
	private FreeAppData mockAppData;
	private NotificationCompat.Builder mockBuilder;
	private SharedPreferences pref;
	
	@Before
	public void setUp() {
		setSdkInt(0);
		context = new SherlockFragmentActivity();
		mockPendingIntent = mock(PendingIntent.class);
		pref = PreferenceManager.getDefaultSharedPreferences(context);
		freeAppNotification = null;
		freeAppData = null;
		mockAppData = null;
		mockBuilder = null;
	}

	@Test
	public void contentTitleIsAppTitle() {
		buildFreeAppNotification();
		Notification notification = freeAppNotification.buildNotification();
		ShadowNotification shadowNotification = shadowOf(notification);
		assertEquals(freeAppData.getAppTitle(), shadowNotification.getLatestEventInfo().getContentTitle());
	}
	
	@Test
	public void contentTextIsAppDeveloperIfHoneycomb() {
		buildFreeAppNotification();
		setSdkInt(Build.VERSION_CODES.HONEYCOMB);
		Notification notification = freeAppNotification.buildNotification();
		ShadowNotification shadowNotification = shadowOf(notification);
		String expected = "By: " + freeAppData.getAppDeveloper();
		assertEquals(expected, shadowNotification.getLatestEventInfo().getContentText());
	}	
	
	@Test
	public void contentInfoIsListPriceIfHoneycomb() {
		buildFreeAppNotificationWithMockBuilder();
		setSdkInt(Build.VERSION_CODES.HONEYCOMB);
		freeAppNotification.buildNotification();
		String expected = "List Price: " + freeAppData.getAppListPrice();
		verify(mockBuilder).setContentInfo(expected);
	}	

	@Test
	public void contentTextIsAppListPriceAndAppDeveloperIfGingerbread() {
		buildFreeAppNotification();
		setSdkInt(Build.VERSION_CODES.GINGERBREAD_MR1);
		Notification notification = freeAppNotification.buildNotification();
		ShadowNotification shadowNotification = shadowOf(notification);
		String expected = freeAppData.getAppListPrice() + " - By: " + freeAppData.getAppDeveloper();
		assertEquals(expected, shadowNotification.getLatestEventInfo().getContentText());
	}
	
	@Test
	public void doesNotCallDescriptionIfICSAndPrefTrue() {
		buildFreeAppNotificationWithMockAppData();
		pref.edit().putBoolean(Preferences.PREF_EXPANDABLE_NOTIFICATION, true).commit();
		setSdkInt(Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1);
		freeAppNotification.buildNotification();
		verify(mockAppData, never()).getAppDescription();
	}
	
	@Test
	public void doesCallDescriptionIfJellybeanAndPrefTrue() {
		buildFreeAppNotificationWithMockAppData();
		pref.edit().putBoolean(Preferences.PREF_EXPANDABLE_NOTIFICATION, true).commit();
		setSdkInt(Build.VERSION_CODES.JELLY_BEAN);
		freeAppNotification.buildNotification();
		verify(mockAppData).getAppDescription();
	}
	
	@Test
	public void doesNotCallDescriptionIfICSAndPrefFalse() {
		buildFreeAppNotificationWithMockAppData();
		pref.edit().putBoolean(Preferences.PREF_EXPANDABLE_NOTIFICATION, false).commit();
		setSdkInt(Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1);
		freeAppNotification.buildNotification();
		verify(mockAppData, never()).getAppDescription();
	}
	
	@Test
	public void doesNotCallDescriptionIfJellybeanAndPrefFalse() {
		buildFreeAppNotificationWithMockAppData();
		pref.edit().putBoolean(Preferences.PREF_EXPANDABLE_NOTIFICATION, false).commit();
		setSdkInt(Build.VERSION_CODES.JELLY_BEAN);
		freeAppNotification.buildNotification();
		verify(mockAppData, never()).getAppDescription();
	}
	
	@Test
	public void shouldShowNotificationIfPrefIsShowGamesTrueAndAppIsGames() {
		buildFreeAppNotificationWithMockAppData();
		when(mockAppData.getAppCategory()).thenReturn(CATEGORY_GAMES);
		pref.edit().putBoolean(Preferences.PREF_NOTIFY_FOR_GAMES, true).commit();
		assertTrue(freeAppNotification.shouldShowNotification());
	}
	
	@Test
	public void shouldShowNotificationIfPrefIsShowGamesFalseAndAppIsNotGames() {
		buildFreeAppNotificationWithMockAppData();
		when(mockAppData.getAppCategory()).thenReturn(CATEGORY_OTHER);
		pref.edit().putBoolean(Preferences.PREF_NOTIFY_FOR_GAMES, false).commit();
		assertTrue(freeAppNotification.shouldShowNotification());
	}
	
	@Test
	public void shouldNotShowNotificationIfPrefIsShowGamesFalseAndAppIsGames() {
		buildFreeAppNotificationWithMockAppData();
		when(mockAppData.getAppCategory()).thenReturn(CATEGORY_GAMES);
		pref.edit().putBoolean(Preferences.PREF_NOTIFY_FOR_GAMES, false).commit();
		assertFalse(freeAppNotification.shouldShowNotification());
	}

	private void populateFreeAppData() {
		File file = new File("src/test/resources/testAppData.html");		
		try {
			URL url = file.toURI().toURL();
			freeAppData = AppDataReader.downloadAppData(url.toString()).getFreeAppData();
		} catch (IOException e) {
			fail(e.getMessage());
		}
	}

	private void setSdkInt(int i) {
		Robolectric.Reflection.setFinalStaticField(Build.VERSION.class, "SDK_INT", i);
	}

	private void buildFreeAppNotification() {
		populateFreeAppData();		
		freeAppNotification = new AppDataNotification(context, mockPendingIntent, freeAppData);		
	}

	private void buildFreeAppNotificationWithMockBuilder() {
		populateFreeAppData();
		freeAppNotification = new AppDataNotificationTester(context, mockPendingIntent, freeAppData);
		mockBuilder = mock(NotificationCompat.Builder.class);
		((AppDataNotificationTester) freeAppNotification).setBuilder(mockBuilder);
	}
	
	private void buildFreeAppNotificationWithMockAppData() {
		mockAppData = mock(FreeAppData.class);
		freeAppNotification = new AppDataNotification(context, mockPendingIntent, mockAppData);
	}

}
