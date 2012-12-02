package com.skylark95.amazonfreenotify.fragment;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragment;
import com.skylark95.amazonfreenotify.R;
import com.skylark95.amazonfreenotify.settings.Preferences;
import com.skylark95.amazonfreenotify.ui.ButtonMenuActions;

public class SettingsFragment extends SherlockFragment {

	private View view;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_settings, container, false);

		setupTextLabels(view);
		setupButtons(view);

		return view;
	}
	
	@Override
	public void onResume() {
		super.onResume();
		setupTextLabels(view);
	}

	private void setupTextLabels(View view) {
		SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getActivity());

		setNotificationsEnabledText(view, pref);

		TextView noificationTimeText = (TextView) view.findViewById(R.id.notification_time_label);
		noificationTimeText.setText(getActivity().getString(R.string.notification_time_label) + " " + "12:00 PM");

		TextView notificationDaysText = (TextView) view.findViewById(R.id.notification_days_label);		
		notificationDaysText.setText(getActivity().getString(R.string.notification_days_label) + " " + "All");

		TextView notificationSoundText = (TextView) view.findViewById(R.id.notification_sound_label);
		notificationSoundText.setText(getActivity().getString(R.string.notification_sound_label) + " " + "My Ringtone");

		setShowNamePriceText(view, pref);
		setShowOnBootText(view, pref);
		setPlayNotificationSoundText(view, pref);
		setVibrateText(view, pref);
		setExpandedNotificationText(view, pref);
		setNotifyForGamesText(view, pref);
	}

	private void setNotifyForGamesText(View view, SharedPreferences pref) {
		TextView notifyForGamesText = (TextView) view.findViewById(R.id.notifiy_for_games);
		boolean notifyForGames = pref.getBoolean(Preferences.PREF_NOTIFY_FOR_GAMES, true);
		
		notifyForGamesText.setText(getActivity().getString(R.string.notifiy_for_games_label) + " " + booleanToYesNo(notifyForGames));
	}

	private void setExpandedNotificationText(View view, SharedPreferences pref) {
		TextView expandedNotificationText = (TextView) view.findViewById(R.id.expandable_notification);
		boolean expandedNotification = pref.getBoolean(Preferences.PREF_EXPANDABLE_NOTIFICATION, true);
		
		expandedNotificationText.setText(getActivity().getString(R.string.expandable_notification) + " " + booleanToYesNo(expandedNotification));
	}

	private void setVibrateText(View view, SharedPreferences pref) {
		TextView vibrateText = (TextView) view.findViewById(R.id.vibrate_label);
		boolean vibrate = pref.getBoolean(Preferences.PREF_VIBRATE, true);
		
		vibrateText.setText(getActivity().getString(R.string.vibrate_label) + " " + booleanToYesNo(vibrate));
	}

	private void setPlayNotificationSoundText(View view, SharedPreferences pref) {
		TextView playNotificationSoundText = (TextView) view.findViewById(R.id.play_notification_sound_label);
		boolean playNofificationSound = pref.getBoolean(Preferences.PREF_PLAY_NOTIFICATION_SOUND, true);
		
		playNotificationSoundText.setText(getActivity().getString(R.string.play_notification_sound_label) + " " + booleanToYesNo(playNofificationSound));
	}

	private void setShowOnBootText(View view, SharedPreferences pref) {
		TextView showOnBootText = (TextView) view.findViewById(R.id.show_on_boot_label);
		boolean showOnBoot = pref.getBoolean(Preferences.PREF_SHOW_ON_BOOT, true);
		
		showOnBootText.setText(getActivity().getString(R.string.show_on_boot_label) + " " + booleanToYesNo(showOnBoot));
	}

	private void setShowNamePriceText(View view, SharedPreferences pref) {
		TextView showNamePriceText = (TextView) view.findViewById(R.id.show_name_price_label);
		boolean showNamePrice = pref.getBoolean(Preferences.PREF_SHOW_NAME_PRICE, true);
		
		showNamePriceText.setText(getActivity().getString(R.string.show_name_price_label) + " " + booleanToYesNo(showNamePrice));
	}

	private void setupButtons(View view) {
		final ButtonMenuActions actions = new ButtonMenuActions();
	
		Button changeSettingsButton = (Button) view.findViewById(R.id.change_settings_button);
		changeSettingsButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				actions.launchPreferences(getActivity());
			}
		});
	
		Button testNotificationButton = (Button) view.findViewById(R.id.test_notification_button);
		testNotificationButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				actions.testNotification(getActivity());
			}
		});
	}

	private void setNotificationsEnabledText(View view, SharedPreferences pref) {
		TextView notificationsEnabledText = (TextView) view.findViewById(R.id.notifications_enabled_label);
		boolean enabled = pref.getBoolean(Preferences.PREF_ENABLED, true);
		
		if (enabled) {
			notificationsEnabledText.setTextColor(Color.GREEN);
		} else {
			notificationsEnabledText.setTextColor(Color.RED);
		}		
		
		notificationsEnabledText.setText(getActivity().getString(R.string.notifications_enabled_label) + " " + booleanToYesNo(enabled));
	}

	private String booleanToYesNo(boolean b) {
		if (b) {
			return "yes";
		}
		return "no";
	}

}
