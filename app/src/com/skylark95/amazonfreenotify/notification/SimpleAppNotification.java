package com.skylark95.amazonfreenotify.notification;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.support.v4.app.NotificationCompat;

import com.skylark95.amazonfreenotify.R;

public class SimpleAppNotification extends FreeAppNotification {
	
	private Context context;
	private PendingIntent pendingIntent;
	private String contentText;
	
	protected SimpleAppNotification(Context context, PendingIntent pendingIntent, String contentText) {
		super(context);
		this.context = context;
		this.pendingIntent = pendingIntent;
		this.contentText = contentText;
	}

	@Override
	protected Notification buildNotification() {
		String contentTitle = context.getString(R.string.notification_simple_title);
		
		NotificationCompat.Builder builder = getBaseBuilder(pendingIntent); 
		builder.setContentTitle(contentTitle);
		builder.setContentText(contentText);		
		
		addTickerText(builder, contentTitle, contentText);
		
		return builder.build();
	}
	
}
