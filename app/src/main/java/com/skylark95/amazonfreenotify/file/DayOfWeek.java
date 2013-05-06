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

/**
 * Days of the week used for saving notification days.
 * 
 * @author Derek
 * @since 1.0
 * @deprecated
 */
@Deprecated
public enum DayOfWeek {
	MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY;

	/**
	 * Returns the appropriate {@link DayOfWeek} for an index.
	 * 
	 * @param i An index between 0 and 6 where Monday = 0.
	 * @return The {@link DayOfWeek} associated with an index or null if none found.
	 */
	public static DayOfWeek findDay(int i) {
		switch (i) {
		case 0:
			return DayOfWeek.MONDAY;
		case 1:
			return DayOfWeek.TUESDAY;
		case 2:
			return DayOfWeek.WEDNESDAY;
		case 3:
			return DayOfWeek.THURSDAY;
		case 4:
			return DayOfWeek.FRIDAY;
		case 5:
			return DayOfWeek.SATURDAY;
		case 6:
			return DayOfWeek.SUNDAY;
		default:
			return null;
		}
	}

}
