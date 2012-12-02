package com.skylark95.amazonfreenotify.ui;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.skylark95.amazonfreenotify.R;
import com.skylark95.amazonfreenotify.settings.Preferences;

public class ButtonMenuActions {

	public void launchPreferences(Context context) {
		Intent prefrences = new Intent(context, Preferences.class);
		context.startActivity(prefrences);
	}

	public void testNotification(Context context) {
		Toast.makeText(context, R.string.test_notification_toast, Toast.LENGTH_SHORT).show();
		//TODO launch notification service on separate thread
		Toast.makeText(context, "Just kidding. I still have to write that part.", Toast.LENGTH_SHORT).show();
	}

}
