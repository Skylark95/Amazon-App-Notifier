package com.skylark95.amazonfreenotify.notification;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationCompat.Builder;
import android.util.Log;

import com.skylark95.amazonfreenotify.R;
import com.skylark95.amazonfreenotify.settings.Preferences;
import com.skylark95.amazonfreenotify.util.Logger;

public abstract class FreeAppNotification {	
	
	private Context context;
	
	private static final String NOTIFICATION_TAG = "FreeAppNotification";
	private static final int NOTIFICATION_ID = 1;
	
	private static final String TAG = Logger.getTag(FreeAppNotification.class);
	
	protected FreeAppNotification(Context context) {
		this.context = context;
	}
	
	protected abstract Notification buildNotification();
	
	protected abstract boolean shouldShowNotification();
	
	public void showNotificationReguardless() {
		showNotification();
	}
	
	public void showNotificationIfNecessary() {
		if (shouldShowNotification()) {
			Log.i(TAG, "Showing Notification");
			showNotification();
		} else {
			Log.i(TAG, "NOT Showing Notification");
		}
	}
	
	private void showNotification() {
		Notification notification = buildNotification();
		NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);		
		notificationManager.notify(NOTIFICATION_TAG, NOTIFICATION_ID, notification);
	}
	
	protected Builder getBaseBuilder(PendingIntent pendingIntent) {	
		Log.v(TAG, "Base Builder Called");
		NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
			.setSmallIcon(R.drawable.notify_icon)
			.setContentIntent(pendingIntent)
			.setDefaults(getDefaults())
			.setAutoCancel(true);
		
		addSound(builder);
		
		return builder;
	}

	private void addSound(NotificationCompat.Builder builder) {
		Uri sound = getSound();
		if (sound != null && isSound()) {
			Log.v(TAG, "Setting notification sound");
			builder.setSound(sound);
		}
	}

	private int getDefaults() {
		int defaults = 0;
		if (isVibrate()) {
			Log.v(TAG, "Setting notification default vibrate");
			defaults |= Notification.DEFAULT_VIBRATE;
		}
		if (getSound() == null && isSound()) {
			Log.v(TAG, "Setting notification default sound");
			defaults |= Notification.DEFAULT_SOUND;
		}
		return defaults;
	}
	
	private Uri getSound() {
		Uri uri = null;
		
		SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
		String uriStr = pref.getString(Preferences.PREF_NOTIFICATION_SOUND, null);
		
		if (uriStr != null) {
			uri = Uri.parse(uriStr);
		}
		return uri;
	}

	private boolean isSound() {
		SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
		return pref.getBoolean(Preferences.PREF_PLAY_NOTIFICATION_SOUND, true);
	}

	private boolean isVibrate() {
		SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
		return pref.getBoolean(Preferences.PREF_VIBRATE, true);
	}

}
