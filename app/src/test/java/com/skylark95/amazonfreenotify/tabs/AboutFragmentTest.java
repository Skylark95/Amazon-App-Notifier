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

package com.skylark95.amazonfreenotify.tabs;

import static com.skylark95.amazonfreenotify.util.TestUtils.*;
import static com.xtremelabs.robolectric.Robolectric.*;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.skylark95.amazonfreenotify.R;
import com.xtremelabs.robolectric.RobolectricTestRunner;
import com.xtremelabs.robolectric.shadows.ShadowFragmentActivity;

@RunWith(RobolectricTestRunner.class)
public class AboutFragmentTest {

	private SherlockFragment fragment;
	private SherlockFragmentActivity activity;
	private View view;

	@Before
	public void setUp() {
		fragment = new AboutFragment();
		startFragment(fragment);
		activity = fragment.getSherlockActivity();
		view = fragment.getView();
	}

	@Test
	public void changelogButtonDoesLaunchChangelogDialog() {
		Button changelogButton = (Button) view.findViewById(R.id.changelog_button);
		changelogButton.performClick();
		
		ShadowFragmentActivity shadowFragmentActivity = shadowOf(activity);
        Fragment changelogDialog = shadowFragmentActivity.getSupportFragmentManager().findFragmentByTag("changelog");
        
        assertEquals(changelogDialog.getArguments().getInt("title"), R.string.changelog_title);
        assertEquals(changelogDialog.getArguments().getInt("html"), R.string.html_changelog);
	}
	
	@Test
	public void ukUsersButtonDoesLaunchUkUsersDialog() {
		Button ukUsersButton = (Button) view.findViewById(R.id.uk_users_button);
		ukUsersButton.performClick();
		
		ShadowFragmentActivity shadowFragmentActivity = shadowOf(activity);
		Fragment ukUsersDialog = shadowFragmentActivity.getSupportFragmentManager().findFragmentByTag("ukusers");
        
        assertEquals(ukUsersDialog.getArguments().getInt("title"), R.string.uk_users_title);
        assertEquals(ukUsersDialog.getArguments().getInt("html"), R.string.html_uk_users);
	}
	
	@Test
	public void doesSetVersionText() throws NameNotFoundException {
		TextView appVersionText = (TextView) view.findViewById(R.id.about_app_version);
		
		PackageManager manager = activity.getPackageManager();
		PackageInfo info = manager.getPackageInfo(activity.getPackageName(), 0);
		String expected = activity.getString(R.string.app_version_label) + " " + info.versionName;
		
		assertEquals(expected, appVersionText.getText());
	}

}
