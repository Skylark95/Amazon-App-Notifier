/*
 * This file is part of Amazon App Notifier
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

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import android.content.Context;
import android.util.Log;

/**
 * Manages all IO for the app.
 * 
 * @author Derek
 * @since 1.0
 * @deprecated
 */
@Deprecated
public class FileManager {

	public final static String TIME_AND_DATE_FILENAME = "TimeAndDay.ser";
	public final static String NOTIFICATION_SOUND_FILENAME = "NotificationSound.ser";
	public final static String CHECKBOXES_FILENAME = "Checkbox.ser";
	public final static String STARTUP_FILENAME = "Startup.ser";
	private final static String CLASS_NAME = FileManager.class.getSimpleName();

	/**
	 * Read settings for {@link TimeAndDayFile}.
	 * 
	 * @param context The context to query.
	 * @return {@link TimeAndDayFile}.
	 * @since 1.0
	 */
	public static TimeAndDayFile readTimeAndDay(Context context) {
		TimeAndDayFile timeAndDayFile;
		String file = TIME_AND_DATE_FILENAME;
		try {
			timeAndDayFile = (TimeAndDayFile) read(file, context);
		} catch (FileNotFoundException e) {
			Log.d(CLASS_NAME, file + " not found.  Will create new file with defaults.");
			timeAndDayFile = new TimeAndDayFile();
			write(timeAndDayFile, context);
		} catch (ClassNotFoundException e) {
			Log.d(CLASS_NAME, "ClassNotFoundException on " + file + ".  Will create new file with defaults.");
			timeAndDayFile = new TimeAndDayFile();
			write(timeAndDayFile, context);
		} catch (IOException e) {
			Log.e(CLASS_NAME, "An IO Exception occurred reading " + file);
			timeAndDayFile = new TimeAndDayFile();
		}
		return timeAndDayFile;
	}
	
	/**
	 * @param context
	 * @return true if file is deleted
	 * @since 2.0
	 */
	public static boolean deleteTimeAndDayLegacyFile(Context context) {
	    return delete(TIME_AND_DATE_FILENAME, context);
	}

	/**
	 * Read settings for {@link NotificationSoundFile}.
	 * 
	 * @param context The context to query.
	 * @return {@link NotificationSoundFile}.
	 * @since 1.0
	 */
	public static NotificationSoundFile readNotificationSound(Context context) {
		NotificationSoundFile notificationSoundFile;
		String file = NOTIFICATION_SOUND_FILENAME;
		try {
			notificationSoundFile = (NotificationSoundFile) read(file, context);
		} catch (FileNotFoundException e) {
			Log.d(CLASS_NAME, file + " not found.  Will create new file with defaults.");
			notificationSoundFile = new NotificationSoundFile(context);
			write(notificationSoundFile, context);
		} catch (ClassNotFoundException e) {
			Log.d(CLASS_NAME, "ClassNotFoundException on " + file + ".  Will create new file with defaults.");
			notificationSoundFile = new NotificationSoundFile(context);
			write(notificationSoundFile, context);
		} catch (IOException e) {
			Log.e(CLASS_NAME, "An IO Exception occurred reading " + file);
			notificationSoundFile = new NotificationSoundFile(context);
		}
		return notificationSoundFile;
	}
	
	/**
     * @param context
     * @return true if file is deleted
     * @since 2.0
     */
    public static boolean deleteNotificationSoundLegacyFile(Context context) {
        return delete(NOTIFICATION_SOUND_FILENAME, context);
    }

	/**
	 * Read settings for {@link CheckboxesFile}.
	 * 
	 * @param context The context to query.
	 * @return {@link CheckboxesFile}.
	 * @since 1.0
	 */
	public static CheckboxesFile readCheckboxes(Context context) {
		CheckboxesFile checkboxesFile;
		String file = CHECKBOXES_FILENAME;
		try {
			checkboxesFile = (CheckboxesFile) read(file, context);
		} catch (FileNotFoundException e) {
			Log.d(CLASS_NAME, file + " not found.  Will create new file with defaults.");
			checkboxesFile = new CheckboxesFile();
			write(checkboxesFile, context);
		} catch (ClassNotFoundException e) {
			Log.d(CLASS_NAME, "ClassNotFoundException on " + file + ".  Will create new file with defaults.");
			checkboxesFile = new CheckboxesFile();
			write(checkboxesFile, context);
		} catch (IOException e) {
			Log.e(CLASS_NAME, "An IO Exception occurred reading " + file);
			checkboxesFile = new CheckboxesFile();
		}
		return checkboxesFile;
	}
	
	/**
     * @param context
     * @return true if file is deleted
     * @since 2.0
     */
    public static boolean deleteCheckboxesLegacyFile(Context context) {
        return delete(CHECKBOXES_FILENAME, context);
    }

	/**
	 * Read settings for {@link StartupFile}.
	 * 
	 * @param context The context to query.
	 * @return {@link StartupFile}.
	 * @since 1.0
	 */
	public static StartupFile readStartup(Context context) {
		StartupFile startupFile;
		String file = STARTUP_FILENAME;
		try {
			startupFile = (StartupFile) read(file, context);
		} catch (FileNotFoundException e) {
			Log.d(CLASS_NAME, file + " not found.  Will create new file with defaults.");
			startupFile = new StartupFile(context);
			write(startupFile, context);
		} catch (ClassNotFoundException e) {
			Log.d(CLASS_NAME, "ClassNotFoundException on " + file + ".  Will create new file with defaults.");
			startupFile = new StartupFile(context);
			write(startupFile, context);
		} catch (IOException e) {
			Log.e(CLASS_NAME, "An IO Exception occurred reading " + file);
			startupFile = new StartupFile(context);
		}
		return startupFile;
	}
	
	/**
     * @param context
     * @return true if file is deleted
     * @since 2.0
     */
    public static boolean deleteStartupLegacyFile(Context context) {
        return delete(STARTUP_FILENAME, context);
    }

	private static Object read(String file, Context context) throws FileNotFoundException, ClassNotFoundException,
			IOException {
		ObjectInputStream in;
		in = new ObjectInputStream(context.openFileInput(file));
		Object object = in.readObject();
		Log.v(CLASS_NAME, "Successfully read file " + file);
		return object;
	}
	
	private static boolean delete(String file, Context context) {
	    boolean result = context.deleteFile(file);
	    
	    if (!result) {
	        Log.v(CLASS_NAME, "Could not delete file " + file);
	        return result;
	    }
	    
	    Log.v(CLASS_NAME, "Successfully deleted file " + file);
	    return result;
	}

	/**
	 * Write settings for {@link TimeAndDayFile}.
	 * 
	 * @param timeAndDayFile Updated {@link TimeAndDayFile} object.
	 * @param context The context to query.
	 * @return True if successful.
	 * @since 1.0
	 */
	public static boolean write(TimeAndDayFile timeAndDayFile, Context context) {
		return write(TIME_AND_DATE_FILENAME, context, timeAndDayFile);
	}

	/**
	 * Write settings for {@link NotificationSoundFile}.
	 * 
	 * @param notificationSoundFile Updated {@link NotificationSoundFile} object.
	 * @param context The context to query.
	 * @return True if successful.
	 * @since 1.0
	 */
	public static boolean write(NotificationSoundFile notificationSoundFile, Context context) {
		return write(NOTIFICATION_SOUND_FILENAME, context, notificationSoundFile);
	}

	/**
	 * Write settings for {@link CheckboxesFile}.
	 * 
	 * @param checkboxesFile Updated {@link CheckboxesFile} object.
	 * @param context The context to query.
	 * @return True if successful.
	 * @since 1.0
	 */
	public static boolean write(CheckboxesFile checkboxesFile, Context context) {
		return write(CHECKBOXES_FILENAME, context, checkboxesFile);
	}

	/**
	 * Write settings for {@link StartupFile}.
	 * 
	 * @param startupFile Updated {@link StartupFile} object.
	 * @param context The context to query.
	 * @return True if successful.
	 * @since 1.0
	 */
	public static boolean write(StartupFile startupFile, Context context) {
		return write(STARTUP_FILENAME, context, startupFile);
	}

	private static boolean write(String file, Context context, Object object) {
		try {
			ObjectOutputStream out = new ObjectOutputStream(context.openFileOutput(file, Context.MODE_PRIVATE));
			out.writeObject(object);
			out.close();
			Log.v(CLASS_NAME, "Successfully wrote to file " + file);
			return true;
		} catch (IOException e) {
			Log.e(CLASS_NAME, "An IO Exception occurred writing " + file);
		}
		return false;
	}
}
