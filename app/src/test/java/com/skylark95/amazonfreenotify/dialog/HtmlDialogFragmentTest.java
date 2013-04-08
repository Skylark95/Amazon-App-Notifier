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

package com.skylark95.amazonfreenotify.dialog;

import static com.xtremelabs.robolectric.Robolectric.*;
import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;

import android.os.Bundle;

import com.actionbarsherlock.app.SherlockDialogFragment;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.skylark95.amazonfreenotify.R;
import com.xtremelabs.robolectric.RobolectricTestRunner;
import com.xtremelabs.robolectric.shadows.ShadowDialog;

@RunWith(RobolectricTestRunner.class)
public class HtmlDialogFragmentTest {

	private static final String KEY_TITLE = "title";
	private static final String KEY_HTML = "html";

	@Test
	public void callingNewInstanceSetsArguments() {
		SherlockDialogFragment htmlDialog = HtmlDialogFragment.newInstance(0, 1);
		Bundle bundle = htmlDialog.getArguments();
		assertEquals(0, bundle.getInt(KEY_TITLE));
		assertEquals(1, bundle.getInt(KEY_HTML));
	}
	
	@Test
	public void onCreateDialogCreatesCorrectDialogWithTitle() {
		SherlockFragmentActivity activity = new SherlockFragmentActivity();
		SherlockDialogFragment htmlDialog = HtmlDialogFragment.newInstance(R.string.changelog_title, R.string.html_changelog);
		htmlDialog.show(activity.getSupportFragmentManager(), "test");
		
		ShadowDialog shadowDialog = shadowOf(htmlDialog.getDialog());
		String title = activity.getString(R.string.changelog_title);
		assertEquals(title, shadowDialog.getTitle());
	}

}
