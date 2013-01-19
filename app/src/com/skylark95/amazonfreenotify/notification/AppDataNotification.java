package com.skylark95.amazonfreenotify.notification;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;

import com.skylark95.amazonfreenotify.beans.FreeAppData;
import com.skylark95.amazonfreenotify.settings.Preferences;

public class AppDataNotification extends FreeAppNotification {

	private Context context;
	private PendingIntent pendingIntent;
	private FreeAppData freeAppData;
	
	public AppDataNotification(Context context, PendingIntent pendingIntent, FreeAppData freeAppData) {
		super(context);
		this.context = context;
		this.pendingIntent = pendingIntent;
		this.freeAppData = freeAppData;
	}
	
	@Override
	protected Notification buildNotification() {		
		NotificationCompat.Builder builder = getBaseBuilder(pendingIntent);
		builder.setContentTitle(freeAppData.getAppTitle());
		builder.setTicker(buildTickerText());
		
		addText(builder);
		Notification notification = buildWithDescription(builder);
		
		return notification;
	}

	private Notification buildWithDescription(NotificationCompat.Builder builder) {
		SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
		Notification notification;
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN
				&& pref.getBoolean(Preferences.PREF_EXPANDABLE_NOTIFICATION, true)) {
			DescriptionHelper helper = new JellybeanDescriptionHelper();
			notification = helper.buildBigTextStyleNotification(builder, freeAppData);
		} else {
			notification = builder.build();
		}
		return notification;
	}

	private void addText(NotificationCompat.Builder builder) {
		if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			builder.setContentText("By: " + freeAppData.getAppDeveloper());
			builder.setContentInfo("List Price: " + freeAppData.getAppListPrice());
		} else {
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
			.append("\n")
			.append("By: ")
			.append(freeAppData.getAppDeveloper())
			.append("\n")
			.append("List Price: ")
			.append(freeAppData.getAppListPrice())
			.toString();
	}

}
