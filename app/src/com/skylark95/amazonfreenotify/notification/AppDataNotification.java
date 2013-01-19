package com.skylark95.amazonfreenotify.notification;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import com.skylark95.amazonfreenotify.R;
import com.skylark95.amazonfreenotify.beans.FreeAppData;

public class AppDataNotification extends FreeAppNotification {

	private Context context;
	private Intent intent;
	private FreeAppData freeAppData;
	
	public AppDataNotification(Context context, Intent intent, FreeAppData freeAppData) {
		super(context);
		this.context = context;
		this.intent = intent;
		this.freeAppData = freeAppData;
	}
	
	@Override
	protected Notification buildNotification() {
		return new NotificationCompat.Builder(context)
			.setSmallIcon(R.drawable.ic_launcher)
			.setContentTitle(freeAppData.getAppTitle())
			.setTicker(freeAppData.getAppTitle())
			.setContentText(buildContentText())
			.setContentIntent(PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_ONE_SHOT))
			.setAutoCancel(true)
			.build();
	}

	private String buildContentText() {
		return new StringBuilder()
		.append("By: ")
		.append(freeAppData.getAppDeveloper())
		.append(" Price: ")
		.append(freeAppData.getAppListPrice())
		.toString();
	}

}
