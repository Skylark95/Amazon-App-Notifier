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
