package com.skylark95.amazonfreenotify.util;

import java.util.Set;

import android.content.res.Resources;


public class Utils {
	
	public static String formatTime(String time) {
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
	
	public static String formatDays(Set<String> days, Resources res) {
		return null;
	}

}
