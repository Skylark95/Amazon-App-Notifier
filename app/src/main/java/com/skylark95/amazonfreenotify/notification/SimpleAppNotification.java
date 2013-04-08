/*
 * This file is part of Amazon App Notifier
 *
 * Copyright 2013 Derek <derek@skylark95.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Amazon App Notifier is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Amazon App Notifier. if not, If not, see <http://www.gnu.org/licenses/>.
 *
 *
 */

package com.skylark95.amazonfreenotify.notification;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.skylark95.amazonfreenotify.R;
import com.skylark95.amazonfreenotify.util.Logger;

public class SimpleAppNotification extends FreeAppNotification {
	
	private PendingIntent pendingIntent;
	private String contentTitle;
	private String contentText;
	
	private static final String TAG = Logger.getTag(SimpleAppNotification.class);
	
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
		Log.v(TAG, "ENTER - buildNotification()");
		NotificationCompat.Builder builder = getBaseBuilder(pendingIntent); 
		builder.setContentTitle(contentTitle)
			.setContentText(contentText)
			.setTicker(buildTickerText());
		
		Log.v(TAG, "EXIT - buildNotification()");
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
		Log.v(TAG, "shouldShowNotification = true");
		return true;
	}
	
}
