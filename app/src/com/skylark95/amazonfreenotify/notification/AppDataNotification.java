package com.skylark95.amazonfreenotify.notification;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import com.skylark95.amazonfreenotify.R;
import com.skylark95.amazonfreenotify.beans.FreeAppData;

public class AppDataNotification implements FreeAppNotification {

	private Context context;
	private Intent intent;
	private FreeAppData freeAppData;
	
	private static final int ID = 1;
	
	public AppDataNotification(Context context, Intent intent, FreeAppData freeAppData) {
		this.context = context;
		this.intent = intent;
		this.freeAppData = freeAppData;
	}

	@SuppressLint("NewApi")
	public void show() {	
		Notification noti = new Notification.BigTextStyle(
			      new Notification.Builder(context)
			         .setContentTitle(freeAppData.getAppTitle())
			         .setContentText(buildText())
			         .setSmallIcon(R.drawable.ic_launcher))
			      .bigText(freeAppData.getAppDescription())
			      .build();
			 
		
		NotificationCompat.Builder notificationBuilder = 
			new NotificationCompat.Builder(context)
			.setSmallIcon(R.drawable.ic_launcher)
			.setContentTitle(freeAppData.getAppTitle())
			.setTicker(freeAppData.getAppTitle())
			.setContentText(buildText())
			.setContentIntent(PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_ONE_SHOT))
			.setAutoCancel(true);		
		
		NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
		notificationManager.notify(ID, noti);
//		notificationManager.notify(ID, notificationBuilder.build());		
	}
	
	private String buildText() {
		return new StringBuilder()
		.append("By: ")
		.append(freeAppData.getAppDeveloper())
		.append(" Price: ")
		.append(freeAppData.getAppListPrice())
		.toString();
	}

}
