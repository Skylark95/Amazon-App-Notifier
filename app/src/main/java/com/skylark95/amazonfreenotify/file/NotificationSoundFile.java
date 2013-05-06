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

package com.skylark95.amazonfreenotify.file;

import android.content.Context;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;

import java.io.Serializable;

/**
 * Contains values used to save ringtone settings.
 * 
 * @author Derek
 * @since 1.0
 * @deprecated
 */
@Deprecated
public class NotificationSoundFile implements Serializable {

	private static final long serialVersionUID = 2072090686359267431L;
	private String ringtoneUri;
	private String ringtoneTitle;

	public NotificationSoundFile(Context context) {
		loadDefaults(context);
	}

	private void loadDefaults(Context context) {
		Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
		Ringtone ringtone = RingtoneManager.getRingtone(context, uri);
		if (ringtone != null) {
    		ringtoneTitle = ringtone.getTitle(context);
    		ringtoneUri = uri.toString();
		}

	}

	public String getRingtoneUri() {
		return ringtoneUri;
	}

	public void setRingtoneUri(String ringtoneUri) {
		this.ringtoneUri = ringtoneUri;
	}

	public String getRingtoneTitle() {
		return ringtoneTitle;
	}

	public void setRingtoneTitle(String ringtoneTitle) {
		this.ringtoneTitle = ringtoneTitle;
	}
}
