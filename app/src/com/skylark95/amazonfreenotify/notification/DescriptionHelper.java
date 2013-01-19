package com.skylark95.amazonfreenotify.notification;

import android.app.Notification;
import android.support.v4.app.NotificationCompat.Builder;

import com.skylark95.amazonfreenotify.beans.FreeAppData;

public interface DescriptionHelper {
	
	public Notification buildBigTextStyleNotification(Builder builder, FreeAppData appData);

}
