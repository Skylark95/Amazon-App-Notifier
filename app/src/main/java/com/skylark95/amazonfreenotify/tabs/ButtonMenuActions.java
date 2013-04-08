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

package com.skylark95.amazonfreenotify.tabs;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockDialogFragment;
import com.commonsware.cwac.wakeful.WakefulIntentService;
import com.skylark95.amazonfreenotify.R;
import com.skylark95.amazonfreenotify.dialog.HtmlDialogFragment;
import com.skylark95.amazonfreenotify.service.TestAppNotificationService;
import com.skylark95.amazonfreenotify.settings.Preferences;

public final class ButtonMenuActions {
	
	private ButtonMenuActions() {
	}
	

	public static void launchPreferences(Context context) {
		Intent prefrences = new Intent(context, Preferences.class);
		context.startActivity(prefrences);
	}

	public static void testNotification(Context context) {
		Toast.makeText(context, R.string.test_notification_toast, Toast.LENGTH_SHORT).show();
		WakefulIntentService.sendWakefulWork(context, TestAppNotificationService.class);
	}

	public static void showChangelog(FragmentManager manager) {
		SherlockDialogFragment dialog = HtmlDialogFragment.newInstance(R.string.changelog_title, R.string.html_changelog);
		dialog.show(manager, "changelog");
	}
	
	public static void showUkUsers(FragmentManager manager) {
		SherlockDialogFragment dialog = HtmlDialogFragment.newInstance(R.string.uk_users_title, R.string.html_uk_users);
		dialog.show(manager, "ukusers");
	}

}
