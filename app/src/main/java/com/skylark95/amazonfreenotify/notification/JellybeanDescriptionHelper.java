/*
 * This file is part of Amazon App Notifier (Free App Notifier for Amazon)
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
