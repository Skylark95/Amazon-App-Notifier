package com.skylark95.amazonfreenotify.notification;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.support.v4.app.NotificationCompat;

import com.skylark95.amazonfreenotify.R;

public class SimpleAppNotification extends FreeAppNotification {
	
	private PendingIntent pendingIntent;
	private String contentTitle;
	private String contentText;
	
	protected SimpleAppNotification(Context context, PendingIntent pendingIntent, String contentText) {
		this(context, pendingIntent, context.getString(R.string.notification_simple_title), contentText);
	}

	protected SimpleAppNotification(Context context, PendingIntent pendingIntent, String contentTitle, String contentText) {
		super(context);
		this.pendingIntent = pendingIntent;
		this.contentTitle = contentTitle;
		this.contentText = contentText;
	}
	
	@Override
	protected Notification buildNotification() {		
		NotificationCompat.Builder builder = getBaseBuilder(pendingIntent); 
		builder.setContentTitle(contentTitle)
			.setContentText(contentText)
			.setTicker(buildTickerText());
		
		return builder.build();
	}
	
	private String buildTickerText() {
		return new StringBuilder()
			.append(contentTitle)
			.append('\n')
			.append(contentText)
			.toString();
	}

	@Override
	protected boolean shouldShowNotification() {
		return true;
	}
	
}
