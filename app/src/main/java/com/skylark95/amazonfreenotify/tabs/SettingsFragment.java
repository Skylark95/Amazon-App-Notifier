package com.skylark95.amazonfreenotify.tabs;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragment;
import com.skylark95.amazonfreenotify.R;
import com.skylark95.amazonfreenotify.settings.Preferences;
import com.skylark95.amazonfreenotify.util.Logger;
import com.skylark95.amazonfreenotify.util.SettingsUtils;

public class SettingsFragment extends SherlockFragment {

	private View settingsView;
	
	private static final String TAG = Logger.getTag(SettingsFragment.class);

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		Log.v(TAG, "ENTER - onCreateView()");
		settingsView = inflater.inflate(R.layout.fragment_settings, container, false);
		setupButtons(settingsView);
		Log.v(TAG, "EXIT - onCreateView()");
		return settingsView;
	}
	
	@Override
	public void onResume() {
		super.onResume();
		Log.v(TAG, "ENTER - onResume()");
		refreshTextLabels(settingsView);
		Log.v(TAG, "EXIT - onResume()");
	}

	private void setupButtons(View view) {	
		Button changeSettingsButton = (Button) view.findViewById(R.id.change_settings_button);
		changeSettingsButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Log.v(TAG, "BUTTON - Change Settings");
				ButtonMenuActions.launchPreferences(getSherlockActivity());
			}
		});
	
		Button testNotificationButton = (Button) view.findViewById(R.id.test_notification_button);
		testNotificationButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Log.v(TAG, "BUTTON - Test Notification");
				ButtonMenuActions.testNotification(getSherlockActivity());
			}
		});
	}

	private void refreshTextLabels(View view) {
		Log.v(TAG, "Refreshing Text Labels");
		SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getSherlockActivity());

		setNotificationsEnabledText(view, pref);
		setNotificationTimeText(view);
		setNotificationDaysText(view);
		setNotificationSoundText(view);
		setShowNamePriceText(view, pref);
		setShowOnBootText(view, pref);
		setPlayNotificationSoundText(view, pref);
		setVibrateText(view, pref);
		setExpandedNotificationText(view, pref);
		setNotifyForGamesText(view, pref);
		Log.v(TAG, "DONE Refreshing Text Labels");
	}

	private void setNotificationSoundText(View view) {
		TextView notificationSoundText = (TextView) view.findViewById(R.id.notification_sound_label);
		String ringtone = SettingsUtils.getRingtoneDisplayValue(getSherlockActivity());
		
		notificationSoundText.setText(getSherlockActivity().getString(R.string.notification_sound_label) + " " + ringtone);
	}

	private void setNotificationDaysText(View view) {
		TextView notificationDaysText = (TextView) view.findViewById(R.id.notification_days_label);
		String days = SettingsUtils.getDaysDisplayValue(getSherlockActivity());
		
		notificationDaysText.setText(getSherlockActivity().getString(R.string.notification_days_label) + " " + days);
	}

	private void setNotificationTimeText(View view) {
		TextView noificationTimeText = (TextView) view.findViewById(R.id.notification_time_label);
		String time = SettingsUtils.getTimeDisplayValue(getSherlockActivity());
		
		noificationTimeText.setText(getSherlockActivity().getString(R.string.notification_time_label) + " " + time);
	}

	private void setNotifyForGamesText(View view, SharedPreferences pref) {
		TextView notifyForGamesText = (TextView) view.findViewById(R.id.notifiy_for_games);
		boolean notifyForGames = pref.getBoolean(Preferences.PREF_NOTIFY_FOR_GAMES, true);
		
		notifyForGamesText.setText(getSherlockActivity().getString(R.string.notifiy_for_games_label) + " " + booleanToYesNo(notifyForGames));
	}

	private void setExpandedNotificationText(View view, SharedPreferences pref) {
		TextView expandedNotificationText = (TextView) view.findViewById(R.id.expandable_notification);
		boolean expandedNotification = pref.getBoolean(Preferences.PREF_EXPANDABLE_NOTIFICATION, true);
		
		expandedNotificationText.setText(getSherlockActivity().getString(R.string.expandable_notification) + " " + booleanToYesNo(expandedNotification));
	}

	private void setVibrateText(View view, SharedPreferences pref) {
		TextView vibrateText = (TextView) view.findViewById(R.id.vibrate_label);
		boolean vibrate = pref.getBoolean(Preferences.PREF_VIBRATE, true);
		
		vibrateText.setText(getSherlockActivity().getString(R.string.vibrate_label) + " " + booleanToYesNo(vibrate));
	}

	private void setPlayNotificationSoundText(View view, SharedPreferences pref) {
		TextView playNotificationSoundText = (TextView) view.findViewById(R.id.play_notification_sound_label);
		boolean playNofificationSound = pref.getBoolean(Preferences.PREF_PLAY_NOTIFICATION_SOUND, true);
		
		playNotificationSoundText.setText(getSherlockActivity().getString(R.string.play_notification_sound_label) + " " + booleanToYesNo(playNofificationSound));
	}

	private void setShowOnBootText(View view, SharedPreferences pref) {
		TextView showOnBootText = (TextView) view.findViewById(R.id.show_on_boot_label);
		boolean showOnBoot = pref.getBoolean(Preferences.PREF_SHOW_ON_BOOT, true);
		
		showOnBootText.setText(getSherlockActivity().getString(R.string.show_on_boot_label) + " " + booleanToYesNo(showOnBoot));
	}

	private void setShowNamePriceText(View view, SharedPreferences pref) {
		TextView showNamePriceText = (TextView) view.findViewById(R.id.show_name_price_label);
		boolean showNamePrice = pref.getBoolean(Preferences.PREF_SHOW_NAME_PRICE, true);
		
		showNamePriceText.setText(getSherlockActivity().getString(R.string.show_name_price_label) + " " + booleanToYesNo(showNamePrice));
	}

	private void setNotificationsEnabledText(View view, SharedPreferences pref) {
		TextView notificationsEnabledText = (TextView) view.findViewById(R.id.notifications_enabled_label);
		boolean enabled = pref.getBoolean(Preferences.PREF_ENABLED, true);
		
		if (enabled) {
			notificationsEnabledText.setTextColor(Color.GREEN);
		} else {
			notificationsEnabledText.setTextColor(Color.RED);
		}		
		
		notificationsEnabledText.setText(getSherlockActivity().getString(R.string.notifications_enabled_label) + " " + booleanToYesNo(enabled));
	}

	private String booleanToYesNo(boolean b) {
		if (b) {
			return "yes";
		}
		return "no";
	}

}
