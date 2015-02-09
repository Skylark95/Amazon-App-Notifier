package com.skylark95.amazonfreenotify.preference;

import android.content.Context;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.preference.Preference;
import android.text.TextUtils;

import com.skylark95.amazonfreenotify.R;

public class UpdateNotificationSoundSummaryListener implements Preference.OnPreferenceChangeListener {

    private final Context context;

    public UpdateNotificationSoundSummaryListener(Context context) {
        this.context = context;
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        if (Settings.KEY_PREF_NOTIFICATION_SOUND.equals(preference.getKey())) {
            updateNotificationSoundSummary(preference, (String) newValue);
        }
        return true;
    }

    private void updateNotificationSoundSummary(Preference preference, String ringtoneUri) {
        if (TextUtils.isEmpty(ringtoneUri)) {
            preference.setSummary(context.getString(R.string.pref_notification_sound_none));
            return;
        }

        Ringtone ringtone = RingtoneManager.getRingtone(context, Uri.parse(ringtoneUri));
        preference.setSummary(ringtone.getTitle(context));
    }

}
