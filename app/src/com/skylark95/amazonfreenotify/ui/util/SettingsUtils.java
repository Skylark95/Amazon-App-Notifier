package com.skylark95.amazonfreenotify.ui.util;

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

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.skylark95.amazonfreenotify.ui.settings.PrefNotificationDays;
import com.skylark95.amazonfreenotify.ui.settings.Preferences;


public class SettingsUtils {
	
	private static final Set<String> WEEKDAYS = ImmutableSet.of("Monday", "Tuesday", "Wednesday", "Thursday", "Friday");
	private static final Set<String> WEEKEND =  ImmutableSet.of("Saturday", "Sunday");	
	
	public static String getTimeDisplayValue(Context context) {
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
		String time = prefs.getString(Preferences.PREF_NOTIFICATION_TIME, "12:00");
		String[] split = time.split(":");
		int hour = Integer.parseInt(split[0]);
		String minute = split[1];
		String amPm = "AM";
		
		if (hour == 0) {
			hour = 12;
		} else if (hour == 12) {
			amPm = "PM";
		} else if (hour > 12) {
			amPm = "PM";
			hour = hour - 12;
		}
		if (minute.length() == 1) {
			minute = "0" + minute;
		}
		
		return String.format("%s:%s %s", hour, minute, amPm);
	}
	
	public static String getDaysDisplayValue(Context context) {		
		Map<String, Boolean> notificationDayPrefs = loadNotificationDays(context);
		List<String> days = Lists.newArrayList();
		
		for (Map.Entry<String, Boolean> preference : notificationDayPrefs.entrySet()) {
			if (preference.getValue()) {
				days.add(preference.getKey());
			}
		}
		
		String summary;
		if (days.size() == 7) {
			summary = "All";
		} else if (days.size() == 5 && days.containsAll(WEEKDAYS)) {
			summary = "Weekdays";
		} else if (days.size() == 2 && days.containsAll(WEEKEND)) {
			summary = "Weekend";
		} else if (days.size() > 0){
			List<String> abbrDays = getAbbrDays(days);			
			StringBuilder sb = new StringBuilder();
			Iterator<String> iterator = abbrDays.iterator();
			sb.append(iterator.next());
			while(iterator.hasNext()) {
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
		List<String> abbrDays = Lists.newArrayList();
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
		return ImmutableList.copyOf(abbrDays);
	}
	
	private static Map<String, Boolean> loadNotificationDays(Context context) {
		Map<String, Boolean> notificationDays = Maps.newHashMap();
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
		notificationDays.put("Sunday", prefs.getBoolean(PrefNotificationDays.PREF_NOTIFICATION_DAYS_SUNDAY, true));		
		notificationDays.put("Monday", prefs.getBoolean(PrefNotificationDays.PREF_NOTIFICATION_DAYS_MONDAY, true));		
		notificationDays.put("Tuesday", prefs.getBoolean(PrefNotificationDays.PREF_NOTIFICATION_DAYS_TUESDAY, true));		
		notificationDays.put("Wednesday", prefs.getBoolean(PrefNotificationDays.PREF_NOTIFICATION_DAYS_WEDNESDAY, true));		
		notificationDays.put("Thursday", prefs.getBoolean(PrefNotificationDays.PREF_NOTIFICATION_DAYS_THURSDAY, true));		
		notificationDays.put("Friday", prefs.getBoolean(PrefNotificationDays.PREF_NOTIFICATION_DAYS_FRIDAY, true));		
		notificationDays.put("Saturday", prefs.getBoolean(PrefNotificationDays.PREF_NOTIFICATION_DAYS_SATURDAY, true));		
		return ImmutableMap.copyOf(notificationDays);
	}

}
