package com.skylark95.amazonfreenotify.file;

import java.io.Serializable;

import android.content.Context;
import android.media.RingtoneManager;
import android.net.Uri;

import com.skylark95.amazonfreenotify.R;

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
		if (!context.getResources().getBoolean(R.bool.using_emulator)) {
			Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
			ringtoneTitle = RingtoneManager.getRingtone(context, uri).getTitle(context);
			ringtoneUri = uri.toString();
		} else {
			ringtoneTitle = "Default Ringtone";
			ringtoneUri = "URI";
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
