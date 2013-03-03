package com.skylark95.amazonfreenotify.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public enum DayOfWeek {
	SUNDAY (Calendar.SUNDAY, "Sunday", "Sun"), 
	MONDAY (Calendar.MONDAY, "Monday", "Mon"),
	TUESDAY (Calendar.TUESDAY, "Tuesday", "Tu"), 
	WEDNESDAY (Calendar.WEDNESDAY, "Wednesday", "Wed"), 
	THURSDAY (Calendar.THURSDAY, "Thursday", "Tr"), 
	FRIDAY (Calendar.FRIDAY, "Friday", "Fri"), 
	SATURDAY (Calendar.SATURDAY, "Saturday", "Sat");
	
	private Integer num;
	private String fullName;
	private String abbrName;	
	
	private static final Set<DayOfWeek> ALL_DAYS = 
			new HashSet<DayOfWeek>(Arrays.asList(new DayOfWeek[] {MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY}));
	private static final Set<DayOfWeek> WEEKDAYS = 
			new HashSet<DayOfWeek>(Arrays.asList(new DayOfWeek[] {MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY}));
	private static final Set<DayOfWeek> WEEKENDS = 
			new HashSet<DayOfWeek>(Arrays.asList(new DayOfWeek[] {SATURDAY, SUNDAY}));
	
	private DayOfWeek(int num, String fullName, String abbrName) {
		this.num = num;
		this.fullName = fullName;
		this.abbrName = abbrName;
	}

	public Integer getNum() {
		return num;
	}

	public String getFullName() {
		return fullName;
	}

	public String getAbbrName() {
		return abbrName;
	}
	
	public static boolean isAllDays(Set<DayOfWeek> daysOfWeek) {
		return ALL_DAYS.equals(daysOfWeek);
	}
	
	public static boolean isWeekdays(Set<DayOfWeek> daysOfWeek) {
		return WEEKDAYS.equals(daysOfWeek);
	}
	
	public static boolean isWeekends(Set<DayOfWeek> daysOfWeek) {
		return WEEKENDS.equals(daysOfWeek);
	}
	
	public static DayOfWeek getDayOfWeekByNum(int num) {
		DayOfWeek dayOfWeek = null;
		for (DayOfWeek day : ALL_DAYS) {
			if (day.getNum() == num) {
				dayOfWeek = day;
				break;
			}
		}
		return dayOfWeek;
	}
	
	public static List<String> listAbbrDays(Set<DayOfWeek> daysOfWeek) {
		List<DayOfWeek> daysList = new ArrayList<DayOfWeek>(daysOfWeek);
		Collections.sort(daysList, new WeekOrder());
		
		List<String> abbrDays = new ArrayList<String>();
		for (DayOfWeek dayOfWeek : daysList) {
			abbrDays.add(dayOfWeek.getAbbrName());
		}
		
		return abbrDays;
	}
		
	private static class WeekOrder implements Comparator<DayOfWeek> {

		@Override
		public int compare(DayOfWeek lhs, DayOfWeek rhs) {
			return lhs.getNum().compareTo(rhs.getNum());
		}
		
	}

}
