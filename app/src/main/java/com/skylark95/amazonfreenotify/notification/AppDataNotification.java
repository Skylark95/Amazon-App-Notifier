package com.skylark95.amazonfreenotify.notification;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.skylark95.amazonfreenotify.beans.FreeAppData;
import com.skylark95.amazonfreenotify.settings.Preferences;
import com.skylark95.amazonfreenotify.util.Logger;

public class AppDataNotification extends FreeAppNotification {

	private Context context;
	private PendingIntent pendingIntent;
	private FreeAppData freeAppData;
	
	private static final String CATEGORY_GAMES = "Games";

	private static final String TAG = Logger.getTag(AppDataNotification.class);
	
	protected AppDataNotification(Context context, PendingIntent pendingIntent, FreeAppData freeAppData) {
		super(context);
		this.context = context;
		this.pendingIntent = pendingIntent;
		this.freeAppData = freeAppData;
	}
	
	@Override
	protected Notification buildNotification() {
		Log.v(TAG, "ENTER - buildNotification()");
		NotificationCompat.Builder builder = getBaseBuilder(pendingIntent);
		builder.setContentTitle(freeAppData.getAppTitle());
		builder.setTicker(buildTickerText());
		
		addText(builder);
		Notification notification = buildWithDescription(builder);
		
		Log.v(TAG, "EXIT - buildNotification()");
		return notification;
	}

	private Notification buildWithDescription(NotificationCompat.Builder builder) {
		SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
		Notification notification;
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN
				&& pref.getBoolean(Preferences.PREF_EXPANDABLE_NOTIFICATION, true)) {
			Log.v(TAG, "Adding description");
			DescriptionHelper helper = new JellybeanDescriptionHelper();
			notification = helper.buildBigTextStyleNotification(builder, freeAppData);
		} else {
			Log.v(TAG, "Not adding description");
			notification = builder.build();
		}
		return notification;
	}

	private void addText(NotificationCompat.Builder builder) {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			Log.v(TAG, "Adding Honeycomb text");
			builder.setContentText("By: " + freeAppData.getAppDeveloper());
			builder.setContentInfo("List Price: " + freeAppData.getAppListPrice());
		} else {
			Log.v(TAG, "Adding Gingerbread text");
			String text = new StringBuilder()
				.append(freeAppData.getAppListPrice())
				.append(" - ")
				.append("By: ")
				.append(freeAppData.getAppDeveloper())
				.toString();
			
			builder.setContentText(text);
		}
	}

	private String buildTickerText() {
		return new StringBuilder()
			.append(freeAppData.getAppTitle())
			.append('\n')
			.append("By: ")
			.append(freeAppData.getAppDeveloper())
			.append('\n')
			.append("List Price: ")
			.append(freeAppData.getAppListPrice())
			.toString();
	}

	@Override
	protected boolean shouldShowNotification() {
		boolean retVal;
		
		if (isShowGames()) {
			retVal = true;
		} else {			
			retVal = !isAppGame();
		}
		
		Log.v(TAG, "shouldShowNotification = " + retVal);
		
		return retVal;
	}

	private boolean isAppGame() {
		Log.i(TAG, "APP CATEGORY = " + freeAppData.getAppCategory());
		return CATEGORY_GAMES.equals(freeAppData.getAppCategory());
	}

	private boolean isShowGames() {
		SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
		return pref.getBoolean(Preferences.PREF_NOTIFY_FOR_GAMES, true);
	}

}
