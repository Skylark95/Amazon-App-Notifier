package com.skylark95.amazonfreenotify.tabs;

import static com.skylark95.amazonfreenotify.util.TestUtils.*;
import static com.xtremelabs.robolectric.Robolectric.*;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.widget.Button;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.skylark95.amazonfreenotify.R;
import com.skylark95.amazonfreenotify.service.TestAppNotificationService;
import com.skylark95.amazonfreenotify.settings.Preferences;
import com.skylark95.amazonfreenotify.util.SettingsUtils;
import com.xtremelabs.robolectric.RobolectricTestRunner;
import com.xtremelabs.robolectric.shadows.ShadowFragmentActivity;
import com.xtremelabs.robolectric.shadows.ShadowIntent;
import com.xtremelabs.robolectric.shadows.ShadowTextView;

@RunWith(RobolectricTestRunner.class)
public class SettingsFragmentTest {

	private static final String TEMP_TEXT = "Temp Text";

	private SherlockFragment fragment;
	private SherlockFragmentActivity activity;

	private SharedPreferences pref;

	@Before
	public void setUp() {
		fragment = new SettingsFragment();
		startFragment(fragment);
		activity = fragment.getSherlockActivity();
		pref = PreferenceManager.getDefaultSharedPreferences(activity);
	}

	@Test
	public void changeSettingsButtonDoesLaunchSettings() {
		Button changeSettingsButton = (Button) fragment.getView().findViewById(R.id.change_settings_button);
		changeSettingsButton.performClick();

		ShadowFragmentActivity shadowFragmentActivity = shadowOf(activity);
		Intent startedIntent = shadowFragmentActivity.getNextStartedActivity();
		ShadowIntent shadowIntent = shadowOf(startedIntent);

		assertEquals(shadowIntent.getComponent().getClassName(), Preferences.class.getName());
	}

	@Test
	public void testNotificationButtonDoesStartTestNotificationService() {
		Button testNotificationButton = (Button) fragment.getView().findViewById(R.id.test_notification_button);
		testNotificationButton.performClick();

		ShadowFragmentActivity shadowFragmentActivity = shadowOf(activity);
		Intent startedService = shadowFragmentActivity.getNextStartedService();
		ShadowIntent shadowIntent = shadowOf(startedService);

		assertEquals(shadowIntent.getComponent().getClassName(), TestAppNotificationService.class.getName());
	}

	@Test
	public void onResumeUpdatesNotificationSoundText() {
		TextView notificationSoundText = (TextView) fragment.getView().findViewById(R.id.notification_sound_label);
		notificationSoundText.setText(TEMP_TEXT);

		fragment.onResume();

		String expected = activity.getString(R.string.notification_sound_label) + " "
				+ SettingsUtils.getRingtoneDisplayValue(activity);
		assertEquals(expected, notificationSoundText.getText());
	}

	@Test
	public void onResumeUpdatesNotificationDaysText() {
		TextView notificationDaysText = (TextView) fragment.getView().findViewById(R.id.notification_days_label);
		notificationDaysText.setText(TEMP_TEXT);

		fragment.onResume();

		String expected = activity.getString(R.string.notification_days_label) + " "
				+ SettingsUtils.getDaysDisplayValue(activity);
		assertEquals(expected, notificationDaysText.getText());
	}

	@Test
	public void onResumeUpdatesNotificationTimeText() {
		TextView notificationTimeText = (TextView) fragment.getView().findViewById(R.id.notification_time_label);
		notificationTimeText.setText(TEMP_TEXT);

		fragment.onResume();

		String expected = activity.getString(R.string.notification_time_label) + " "
				+ SettingsUtils.getTimeDisplayValue(activity);
		assertEquals(expected, notificationTimeText.getText());
	}

	@Test
	public void onResumeUpdatesNotifyForGamesText() {
		TextView notifyForGamesText = (TextView) fragment.getView().findViewById(R.id.notifiy_for_games);
		notifyForGamesText.setText(TEMP_TEXT);

		fragment.onResume();

		String expected = activity.getString(R.string.notifiy_for_games_label) + " "
				+ booleanToYesNo(pref.getBoolean(Preferences.PREF_NOTIFY_FOR_GAMES, true));
		assertEquals(expected, notifyForGamesText.getText());
	}

	@Test
	public void onResumeUpdatesExpandedNotificationText() {
		TextView expandableNotificationText = (TextView) fragment.getView().findViewById(R.id.expandable_notification);
		expandableNotificationText.setText(TEMP_TEXT);

		fragment.onResume();

		String expected = activity.getString(R.string.expandable_notification) + " "
				+ booleanToYesNo(pref.getBoolean(Preferences.PREF_EXPANDABLE_NOTIFICATION, true));
		assertEquals(expected, expandableNotificationText.getText());
	}

	@Test
	public void onResumeUpdatesVibrateText() {
		TextView vibrateText = (TextView) fragment.getView().findViewById(R.id.vibrate_label);
		vibrateText.setText(TEMP_TEXT);

		fragment.onResume();

		String expected = activity.getString(R.string.vibrate_label) + " "
				+ booleanToYesNo(pref.getBoolean(Preferences.PREF_VIBRATE, true));
		assertEquals(expected, vibrateText.getText());
	}
	
	@Test
	public void onResumeUpdatesPlayNotificationSoundText() {
		TextView playNotificationSoundText = (TextView) fragment.getView().findViewById(R.id.play_notification_sound_label);
		playNotificationSoundText.setText(TEMP_TEXT);

		fragment.onResume();

		String expected = activity.getString(R.string.play_notification_sound_label) + " "
				+ booleanToYesNo(pref.getBoolean(Preferences.PREF_PLAY_NOTIFICATION_SOUND, true));
		assertEquals(expected, playNotificationSoundText.getText());
	}
	
	@Test
	public void onResumeUpdatesShowOnBootText() {
		TextView showOnBootText = (TextView) fragment.getView().findViewById(R.id.show_on_boot_label);
		showOnBootText.setText(TEMP_TEXT);

		fragment.onResume();

		String expected = activity.getString(R.string.show_on_boot_label) + " "
				+ booleanToYesNo(pref.getBoolean(Preferences.PREF_SHOW_ON_BOOT, true));
		assertEquals(expected, showOnBootText.getText());
	}

	@Test
	public void onResumeUpdatesShowNamePriceText() {
		TextView showNamePriceText = (TextView) fragment.getView().findViewById(R.id.show_name_price_label);
		showNamePriceText.setText(TEMP_TEXT);

		fragment.onResume();

		String expected = activity.getString(R.string.show_name_price_label) + " "
				+ booleanToYesNo(pref.getBoolean(Preferences.PREF_SHOW_NAME_PRICE, true));
		assertEquals(expected, showNamePriceText.getText());
	}
	
	@Test
	public void onResumeUpdatesNotificationsEnabledGreenText() {
		TextView notificationsEnabledText = (TextView) fragment.getView().findViewById(R.id.notifications_enabled_label);
		notificationsEnabledText.setText(TEMP_TEXT);

		fragment.onResume();

		String expected = activity.getString(R.string.notifications_enabled_label) + " "
				+ booleanToYesNo(pref.getBoolean(Preferences.PREF_ENABLED, true));
		assertEquals(expected, notificationsEnabledText.getText());
		ShadowTextView shadowTextView = shadowOf(notificationsEnabledText);
		assertEquals(Color.GREEN, shadowTextView.getTextColorHexValue().intValue());
	}
	
	@Test
	public void onResumeUpdatesNotificationsEnabledRedText() {
		TextView notificationsEnabledText = (TextView) fragment.getView().findViewById(R.id.notifications_enabled_label);
		notificationsEnabledText.setText(TEMP_TEXT);

		pref.edit().putBoolean(Preferences.PREF_ENABLED, false).commit();		
		fragment.onResume();

		String expected = activity.getString(R.string.notifications_enabled_label) + " "
				+ booleanToYesNo(pref.getBoolean(Preferences.PREF_ENABLED, true));
		assertEquals(expected, notificationsEnabledText.getText());
		ShadowTextView shadowTextView = shadowOf(notificationsEnabledText);
		assertEquals(Color.RED, shadowTextView.getTextColorHexValue().intValue());
	}
	
	private String booleanToYesNo(boolean b) {
		if (b) {
			return "yes";
		}
		return "no";
	}

}
