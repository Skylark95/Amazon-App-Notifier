package com.skylark95.amazonfreenotify.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.preference.PreferenceManager;

import com.skylark95.amazonfreenotify.settings.PrefNotificationDays;
import com.skylark95.amazonfreenotify.settings.Preferences;


public final class SettingsUtils {
	
	private static final List<String> WEEKDAYS = Arrays.asList(new String[] {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday"});
	private static final List<String> WEEKEND = Arrays.asList(new String[] {"Saturday", "Sunday"});	
	private static final int NOON_HOUR = 12;
	private static final int MIDNIGHT_HOUR = 0;
	private static final int NUM_DAYS_WEEK = 7;
	private static final int NUM_DAYS_WEEKDAY = 5;
	private static final int NUM_DAYS_WEEKEND = 2;
	
	private SettingsUtils() {
	}
	
	public static String getTimeDisplayValue(Context context) {
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
		String time = prefs.getString(Preferences.PREF_NOTIFICATION_TIME, "12:00");
		String[] split = time.split(":");
		int hour = Integer.parseInt(split[0]);
		String minute = split[1];
		String amPm = "AM";
		
		if (hour == MIDNIGHT_HOUR) {
			hour = NOON_HOUR;
		} else if (hour == NOON_HOUR) {
			amPm = "PM";
		} else if (hour > NOON_HOUR) {
			amPm = "PM";
			hour = hour - NOON_HOUR;
		}
		if (minute.length() == 1) {
			minute = "0" + minute;
		}
		
		return String.format("%s:%s %s", hour, minute, amPm);
	}
	
	public static String getDaysDisplayValue(Context context) {		
		Map<String, Boolean> notificationDayPrefs = loadNotificationDays(context);
		List<String> days = new ArrayList<String>();
		
		for (Map.Entry<String, Boolean> preference : notificationDayPrefs.entrySet()) {
			if (preference.getValue()) {
				days.add(preference.getKey());
			}
		}
		
		String summary;
		if (days.size() == NUM_DAYS_WEEK) {
			summary = "All";
		} else if (days.size() == NUM_DAYS_WEEKDAY && days.containsAll(WEEKDAYS)) {
			summary = "Weekdays";
		} else if (days.size() == NUM_DAYS_WEEKEND && days.containsAll(WEEKEND)) {
			summary = "Weekend";
		} else if (days.size() > 0) {
			List<String> abbrDays = getAbbrDays(days);			
			StringBuilder sb = new StringBuilder();
			Iterator<String> iterator = abbrDays.iterator();
			sb.append(iterator.next());
			while (iterator.hasNext()) {
				sb.append(", ");
				sb.append(iterator.next());
			}
			summary = sb.toString();
		} else {
			summary = "None Selected";
		}
		
		return summary;
	}
	
	public static String getRingtoneDisplayValue(Context context) {
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
		String ringtoneStr = prefs.getString(Preferences.PREF_NOTIFICATION_SOUND, null);
		
		String ringtoneName;
		if (ringtoneStr != null) {
			Uri ringtoneUri = Uri.parse(ringtoneStr);
			Ringtone ringtone = RingtoneManager.getRingtone(context, ringtoneUri);
			ringtoneName = ringtone.getTitle(context);
		} else {
			ringtoneName = "Not Selected";
		}
		
		return ringtoneName;
	}

	private static List<String> getAbbrDays(List<String> days) {
		List<String> abbrDays = new ArrayList<String>();
		if (days.contains("Sunday")) {
			abbrDays.add("Sun");
		}
		if (days.contains("Monday")) {
			abbrDays.add("Mon");
		}
		if (days.contains("Tuesday")) {
			abbrDays.add("Tu");
		}
		if (days.contains("Wednesday")) {
			abbrDays.add("Wed");
		}
		if (days.contains("Thursday")) {
			abbrDays.add("Tr");
		}
		if (days.contains("Friday")) {
			abbrDays.add("Fri");
		}
		if (days.contains("Saturday")) {
			abbrDays.add("Sat");
		}
		return abbrDays;
	}
	
	private static Map<String, Boolean> loadNotificationDays(Context context) {
		Map<String, Boolean> notificationDays = new HashMap<String, Boolean>();
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
		notificationDays.put("Sunday", prefs.getBoolean(PrefNotificationDays.PREF_NOTIFICATION_DAYS_SUNDAY, true));		
		notificationDays.put("Monday", prefs.getBoolean(PrefNotificationDays.PREF_NOTIFICATION_DAYS_MONDAY, true));		
		notificationDays.put("Tuesday", prefs.getBoolean(PrefNotificationDays.PREF_NOTIFICATION_DAYS_TUESDAY, true));		
		notificationDays.put("Wednesday", prefs.getBoolean(PrefNotificationDays.PREF_NOTIFICATION_DAYS_WEDNESDAY, true));		
		notificationDays.put("Thursday", prefs.getBoolean(PrefNotificationDays.PREF_NOTIFICATION_DAYS_THURSDAY, true));		
		notificationDays.put("Friday", prefs.getBoolean(PrefNotificationDays.PREF_NOTIFICATION_DAYS_FRIDAY, true));		
		notificationDays.put("Saturday", prefs.getBoolean(PrefNotificationDays.PREF_NOTIFICATION_DAYS_SATURDAY, true));		
		return notificationDays;
	}

}
