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
