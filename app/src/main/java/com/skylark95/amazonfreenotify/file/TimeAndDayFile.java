package com.skylark95.amazonfreenotify.file;

import java.io.Serializable;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * Holds values for the time and days notifications will appear.
 * 
 * @author Derek
 * @since 1.0
 * @deprecated
 */
@Deprecated
public class TimeAndDayFile implements Serializable {

	private static final long serialVersionUID = -49195225734027936L;
	private int hourOfDay;
	private int minuteOfDay;
	private Map<DayOfWeek, Boolean> daysOfWeek = new HashMap<DayOfWeek, Boolean>();

	public TimeAndDayFile() {
		loadDefaults();
	}

	private void loadDefaults() {
		daysOfWeek.put(DayOfWeek.MONDAY, true);
		daysOfWeek.put(DayOfWeek.TUESDAY, true);
		daysOfWeek.put(DayOfWeek.WEDNESDAY, true);
		daysOfWeek.put(DayOfWeek.THURSDAY, true);
		daysOfWeek.put(DayOfWeek.FRIDAY, true);
		daysOfWeek.put(DayOfWeek.SATURDAY, true);
		daysOfWeek.put(DayOfWeek.SUNDAY, true);
		Calendar cal = Calendar.getInstance();
		hourOfDay = cal.get(Calendar.HOUR_OF_DAY);
		minuteOfDay = cal.get(Calendar.MINUTE);
	}

	public int getHourOfDay() {
		return hourOfDay;
	}

	public void setHourOfDay(int hourOfDay) {
		this.hourOfDay = hourOfDay;
	}

	public int getMinuteOfDay() {
		return minuteOfDay;
	}

	public void setMinuteOfDay(int minuteOfDay) {
		this.minuteOfDay = minuteOfDay;
	}

	public Map<DayOfWeek, Boolean> getDaysOfWeek() {
		return daysOfWeek;
	}

	public void setDaysOfWeek(Map<DayOfWeek, Boolean> daysOfWeek) {
		this.daysOfWeek = daysOfWeek;
	}

}
