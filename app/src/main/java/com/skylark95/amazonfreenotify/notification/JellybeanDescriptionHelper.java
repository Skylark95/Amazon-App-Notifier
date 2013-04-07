package com.skylark95.amazonfreenotify.notification;

import android.app.Notification;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationCompat.Builder;
import android.text.Html;

import com.skylark95.amazonfreenotify.beans.FreeAppData;

public class JellybeanDescriptionHelper implements DescriptionHelper {

	
	protected JellybeanDescriptionHelper() {
	}

	@Override
	public Notification buildBigTextStyleNotification(Builder builder, FreeAppData appData) {
		return new NotificationCompat.BigTextStyle(builder)
			.bigText(Html.fromHtml(appData.getAppDescription()))
			.setSummaryText("By: " + appData.getAppDeveloper())
			.build();

	}

}
