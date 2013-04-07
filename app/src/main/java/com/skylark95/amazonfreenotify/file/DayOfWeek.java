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
