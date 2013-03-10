package com.skylark95.amazonfreenotify.settings;

import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.preference.PreferenceManager;



public final class SettingsUtils {
	
	private static final String NONE_SELECTED = "None Selected";
	private static final String WEEKEND = "Weekend";
	private static final String WEEKDAYS = "Weekdays";
	private static final String ALL = "All";
	private static final int NOON = 12;
	private static final int MIDNIGHT_STANDARD = 12;
	private static final int MIDNIGHT_MILITARY = 0;
	private static final String AM = "AM";
	private static final String PM = "PM";
	
	private SettingsUtils() {
	}
	
	public static String getTimeDisplayValue(Context context) {
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
		String time = prefs.getString(Preferences.PREF_NOTIFICATION_TIME, "12:0");
		String[] split = time.split(":");
		int hour = Integer.parseInt(split[0]);
		String minute = split[1];
		String amPm = AM;
		
		if (hour == MIDNIGHT_MILITARY) {
			hour = MIDNIGHT_STANDARD;
		} else if (hour == NOON) {
			amPm = PM;
		} else if (hour > NOON) {
			amPm = PM;
			hour = hour - NOON;
		}
		if (minute.length() == 1) {
			minute = "0" + minute;
		}
		
		return String.format("%s:%s %s", hour, minute, amPm);
	}
	
	public static String getDaysDisplayValue(Context context) {		
		Set<DayOfWeek> days = loadNotificationDays(context);
		
		String summary;
		if (DayOfWeek.isAllDays(days)) {
			summary = ALL;
		} else if (DayOfWeek.isWeekdays(days)) {
			summary = WEEKDAYS;
		} else if (DayOfWeek.isWeekends(days)) {
			summary = WEEKEND;
		} else if (days.size() > 0) {
			summary = buildAbbrDaysSumm(days);
		} else {
			summary = NONE_SELECTED;
		}
		
		return summary;
	}

	private static String buildAbbrDaysSumm(Set<DayOfWeek> days) {
		List<String> abbrDays = DayOfWeek.listAbbrDays(days);		
		StringBuilder sb = new StringBuilder();
		Iterator<String> iterator = abbrDays.iterator();
		sb.append(iterator.next());
		while (iterator.hasNext()) {
			sb.append(", ");
			sb.append(iterator.next());
		}
		return sb.toString();
	}

	public static String getRingtoneDisplayValue(Context context) {
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
		String ringtoneStr = prefs.getString(Preferences.PREF_NOTIFICATION_SOUND, null);
		
		String ringtoneName;
		if (ringtoneStr != null) {
			Uri ringtoneUri = Uri.parse(ringtoneStr);
			Ringtone ringtone = RingtoneManager.getRingtone(context, ringtoneUri);
			ringtoneName = ringtone == null ? "Not Selected" : ringtone.getTitle(context);
		} else {
			ringtoneName = "Not Selected";
		}
		
		return ringtoneName;
	}
	
	public static long getTimeInMillis(Context context) {
		SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
		String timeStr = pref.getString(Preferences.PREF_NOTIFICATION_TIME, "12:0");
		String[] hourMinute = timeStr.split(":");
		
		int hour = Integer.parseInt(hourMinute[0]);
		int minute = Integer.parseInt(hourMinute[1]);
		
		Calendar cal = Calendar.getInstance();
		
		// Add one day if time has already passed
		if (cal.get(Calendar.HOUR_OF_DAY) >= hour
				&& cal.get(Calendar.MINUTE) >= minute) {
			cal.add(Calendar.DATE, 1);
		}
			
		cal.set(Calendar.HOUR_OF_DAY, hour);
		cal.set(Calendar.MINUTE, minute);
		cal.set(Calendar.SECOND, 0);
		
		return cal.getTimeInMillis();
	}
	
	public static boolean isTodayChecked(Context context) {
		Calendar cal = Calendar.getInstance();
		DayOfWeek today = DayOfWeek.getDayOfWeekByNum(cal.get(Calendar.DAY_OF_WEEK));
		Set<DayOfWeek> notificationDays = loadNotificationDays(context);
		return notificationDays.contains(today);
	}
	
	private static Set<DayOfWeek> loadNotificationDays(Context context) {
		Map<DayOfWeek, Boolean> notificationDays = new HashMap<DayOfWeek, Boolean>();
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
		notificationDays.put(DayOfWeek.SUNDAY, prefs.getBoolean(PrefNotificationDays.PREF_NOTIFICATION_DAYS_SUNDAY, true));		
		notificationDays.put(DayOfWeek.MONDAY, prefs.getBoolean(PrefNotificationDays.PREF_NOTIFICATION_DAYS_MONDAY, true));		
		notificationDays.put(DayOfWeek.TUESDAY, prefs.getBoolean(PrefNotificationDays.PREF_NOTIFICATION_DAYS_TUESDAY, true));		
		notificationDays.put(DayOfWeek.WEDNESDAY, prefs.getBoolean(PrefNotificationDays.PREF_NOTIFICATION_DAYS_WEDNESDAY, true));		
		notificationDays.put(DayOfWeek.THURSDAY, prefs.getBoolean(PrefNotificationDays.PREF_NOTIFICATION_DAYS_THURSDAY, true));		
		notificationDays.put(DayOfWeek.FRIDAY, prefs.getBoolean(PrefNotificationDays.PREF_NOTIFICATION_DAYS_FRIDAY, true));		
		notificationDays.put(DayOfWeek.SATURDAY, prefs.getBoolean(PrefNotificationDays.PREF_NOTIFICATION_DAYS_SATURDAY, true));
		
		Set<DayOfWeek> days = new HashSet<DayOfWeek>();
		for (Map.Entry<DayOfWeek, Boolean> preference : notificationDays.entrySet()) {
			if (preference.getValue()) {
				days.add(preference.getKey());
			}
		}
		
		return Collections.unmodifiableSet(days);
	}

}
