package com.skylark95.amazonfreenotify.ui.util;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockDialogFragment;
import com.skylark95.amazonfreenotify.R;
import com.skylark95.amazonfreenotify.ui.dialog.HtmlDialogFragment;
import com.skylark95.amazonfreenotify.ui.settings.Preferences;

public class ButtonMenuActions {
	
	private ButtonMenuActions() {}

	public static void launchPreferences(Context context) {
		Intent prefrences = new Intent(context, Preferences.class);
		context.startActivity(prefrences);
	}

	public static void testNotification(Context context) {
		Toast.makeText(context, R.string.test_notification_toast, Toast.LENGTH_SHORT).show();
		//TODO launch notification service on separate thread
		Toast.makeText(context, "Just kidding. I still have to write that part.", Toast.LENGTH_SHORT).show();
	}

	public static void showChangelog(FragmentManager manager) {
		SherlockDialogFragment dialog = HtmlDialogFragment.newInstance(R.string.changelog_title, R.raw.html_changelog);
		dialog.show(manager, "changelog");
	}
	
	public static void showUkUsers(FragmentManager manager) {
		SherlockDialogFragment dialog = HtmlDialogFragment.newInstance(R.string.uk_users_title, R.raw.html_uk_users);
		dialog.show(manager, "ukusers");
	}

}
