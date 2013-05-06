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
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.skylark95.amazonfreenotify.R;
import com.skylark95.amazonfreenotify.service.TestAppNotificationService;
import com.skylark95.amazonfreenotify.settings.Preferences;
import com.skylark95.amazonfreenotify.settings.SettingsUtils;
import com.xtremelabs.robolectric.RobolectricTestRunner;
import com.xtremelabs.robolectric.shadows.ShadowFragmentActivity;
import com.xtremelabs.robolectric.shadows.ShadowIntent;
import com.xtremelabs.robolectric.shadows.ShadowTextView;

@RunWith(RobolectricTestRunner.class)
public class SettingsFragmentTest {

	private static final String TEMP_TEXT = "Temp Text";

	private SherlockFragment fragment;
	private SherlockFragmentActivity activity;
	private View view;

	private SharedPreferences pref;

	@Before
	public void setUp() {
		fragment = new SettingsFragment();
		startFragment(fragment);
		activity = fragment.getSherlockActivity();
		view = fragment.getView();
		pref = PreferenceManager.getDefaultSharedPreferences(activity);
	}

	@Test
	public void changeSettingsButtonDoesLaunchSettings() {
		Button changeSettingsButton = (Button) view.findViewById(R.id.change_settings_button);
		changeSettingsButton.performClick();

		ShadowFragmentActivity shadowFragmentActivity = shadowOf(activity);
		Intent startedIntent = shadowFragmentActivity.getNextStartedActivity();
		ShadowIntent shadowIntent = shadowOf(startedIntent);

		assertEquals(shadowIntent.getComponent().getClassName(), Preferences.class.getName());
	}

	@Test
	public void testNotificationButtonDoesStartTestNotificationService() {
		Button testNotificationButton = (Button) view.findViewById(R.id.test_notification_button);
		testNotificationButton.performClick();

		ShadowFragmentActivity shadowFragmentActivity = shadowOf(activity);
		Intent startedService = shadowFragmentActivity.getNextStartedService();
		ShadowIntent shadowIntent = shadowOf(startedService);

		assertEquals(shadowIntent.getComponent().getClassName(), TestAppNotificationService.class.getName());
	}

	@Test
	public void onResumeUpdatesNotificationSoundText() {
		TextView notificationSoundText = (TextView) view.findViewById(R.id.notification_sound_label);
		notificationSoundText.setText(TEMP_TEXT);

		fragment.onResume();

		String expected = activity.getString(R.string.notification_sound_label) + " "
				+ SettingsUtils.getRingtoneDisplayValue(activity);
		assertEquals(expected, notificationSoundText.getText());
	}

	@Test
	public void onResumeUpdatesNotificationDaysText() {
		TextView notificationDaysText = (TextView) view.findViewById(R.id.notification_days_label);
		notificationDaysText.setText(TEMP_TEXT);

		fragment.onResume();

		String expected = activity.getString(R.string.notification_days_label) + " "
				+ SettingsUtils.getDaysDisplayValue(activity);
		assertEquals(expected, notificationDaysText.getText());
	}

	@Test
	public void onResumeUpdatesNotificationTimeText() {
		TextView notificationTimeText = (TextView) view.findViewById(R.id.notification_time_label);
		notificationTimeText.setText(TEMP_TEXT);

		fragment.onResume();

		String expected = activity.getString(R.string.notification_time_label) + " "
				+ SettingsUtils.getTimeDisplayValue(activity);
		assertEquals(expected, notificationTimeText.getText());
	}

	@Test
	public void onResumeUpdatesNotifyForGamesText() {
		TextView notifyForGamesText = (TextView) view.findViewById(R.id.notifiy_for_games);
		notifyForGamesText.setText(TEMP_TEXT);

		fragment.onResume();

		String expected = activity.getString(R.string.notifiy_for_games_label) + " "
				+ booleanToYesNo(pref.getBoolean(Preferences.PREF_NOTIFY_FOR_GAMES, true));
		assertEquals(expected, notifyForGamesText.getText());
	}

	@Test
	public void onResumeUpdatesExpandedNotificationText() {
		TextView expandableNotificationText = (TextView) view.findViewById(R.id.expandable_notification);
		expandableNotificationText.setText(TEMP_TEXT);

		fragment.onResume();

		String expected = activity.getString(R.string.expandable_notification) + " "
				+ booleanToYesNo(pref.getBoolean(Preferences.PREF_EXPANDABLE_NOTIFICATION, true));
		assertEquals(expected, expandableNotificationText.getText());
	}

	@Test
	public void onResumeUpdatesVibrateText() {
		TextView vibrateText = (TextView) view.findViewById(R.id.vibrate_label);
		vibrateText.setText(TEMP_TEXT);

		fragment.onResume();

		String expected = activity.getString(R.string.vibrate_label) + " "
				+ booleanToYesNo(pref.getBoolean(Preferences.PREF_VIBRATE, true));
		assertEquals(expected, vibrateText.getText());
	}
	
	@Test
	public void onResumeUpdatesPlayNotificationSoundText() {
		TextView playNotificationSoundText = (TextView) view.findViewById(R.id.play_notification_sound_label);
		playNotificationSoundText.setText(TEMP_TEXT);

		fragment.onResume();

		String expected = activity.getString(R.string.play_notification_sound_label) + " "
				+ booleanToYesNo(pref.getBoolean(Preferences.PREF_PLAY_NOTIFICATION_SOUND, true));
		assertEquals(expected, playNotificationSoundText.getText());
	}
	
	@Test
	public void onResumeUpdatesShowOnBootText() {
		TextView showOnBootText = (TextView) view.findViewById(R.id.show_on_boot_label);
		showOnBootText.setText(TEMP_TEXT);

		fragment.onResume();

		String expected = activity.getString(R.string.show_on_boot_label) + " "
				+ booleanToYesNo(pref.getBoolean(Preferences.PREF_SHOW_ON_BOOT, true));
		assertEquals(expected, showOnBootText.getText());
	}

	@Test
	public void onResumeUpdatesShowNamePriceText() {
		TextView showNamePriceText = (TextView) view.findViewById(R.id.show_name_price_label);
		showNamePriceText.setText(TEMP_TEXT);

		fragment.onResume();

		String expected = activity.getString(R.string.show_name_price_label) + " "
				+ booleanToYesNo(pref.getBoolean(Preferences.PREF_SHOW_NAME_PRICE, true));
		assertEquals(expected, showNamePriceText.getText());
	}
	
	@Test
	public void onResumeUpdatesNotificationsEnabledGreenText() {
		TextView notificationsEnabledText = (TextView) view.findViewById(R.id.notifications_enabled_label);
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
		TextView notificationsEnabledText = (TextView) view.findViewById(R.id.notifications_enabled_label);
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
