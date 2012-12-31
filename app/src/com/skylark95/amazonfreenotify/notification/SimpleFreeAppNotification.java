package com.skylark95.amazonfreenotify.notification;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import com.skylark95.amazonfreenotify.R;

public class SimpleFreeAppNotification implements FreeAppNotification {
	
	private Context context;
	private Intent intent;
	private String text;
	
	private static final int ID = 1;
	
	public SimpleFreeAppNotification(Context context, Intent intent, String text) {
		this.context = context;
		this.intent = intent;
		this.text = text;
	}

	public void show() {		
		NotificationCompat.Builder notificationBuilder = 
			new NotificationCompat.Builder(context)
			.setSmallIcon(R.drawable.ic_launcher)
			.setContentTitle(context.getString(R.string.notification_simple_title))
			.setTicker(context.getString(R.string.notification_simple_title))
			.setContentText(text)
			.setContentIntent(PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_ONE_SHOT))
			.setAutoCancel(true);		
		
		NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
		notificationManager.notify(ID, notificationBuilder.build());		
	}
	
}
