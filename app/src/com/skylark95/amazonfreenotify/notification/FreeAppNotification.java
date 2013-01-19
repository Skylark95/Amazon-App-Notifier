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

import com.skylark95.amazonfreenotify.R;
import com.skylark95.amazonfreenotify.settings.Preferences;

public abstract class FreeAppNotification {	
	
	private Context context;
	
	private static final String TAG = "FreeAppNotification";
	private static final int ID = 1;
	
	protected FreeAppNotification(Context context) {
		this.context = context;
	}
	
	protected abstract Notification buildNotification();
	
	public void showNotification() {
		Notification notification = buildNotification();
		NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);		
		notificationManager.notify(TAG, ID, notification);
	}
	
	protected Builder getBaseBuilder(PendingIntent pendingIntent) {		
		NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
			.setSmallIcon(R.drawable.notify_icon)
			.setContentIntent(pendingIntent)
			.setDefaults(getDefaults())
			.setAutoCancel(true);
		
		addSound(builder);
		
		return builder;
	}

	protected void addTickerText(Builder builder, String contentTitle, String contentText) {
		StringBuilder sb = new StringBuilder()
			.append(contentTitle)
			.append("\n")
			.append(contentText);
		
		builder.setTicker(sb.toString());		
	}

	private void addSound(NotificationCompat.Builder builder) {
		Uri sound = getSound();
		if (sound != null && isSound()) {
			builder.setSound(sound);
		}
	}

	private int getDefaults() {
		int defaults = 0;
		if (isVibrate()) {
			defaults |= Notification.DEFAULT_VIBRATE;
		}
		if (getSound() == null && isSound()) {
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
