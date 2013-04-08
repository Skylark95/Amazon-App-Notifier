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

import static com.skylark95.amazonfreenotify.util.TestUtils.*;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.view.View;

import com.actionbarsherlock.app.SherlockFragment;
import com.skylark95.amazonfreenotify.R;
import com.xtremelabs.robolectric.RobolectricTestRunner;

@RunWith(RobolectricTestRunner.class)
public class DonateFragmentTest {

	
	private SherlockFragment fragment;
	private View view;
	
	@Before
	public void setUp() {
		fragment = new DonateFragment();
		startFragment(fragment);
		view = fragment.getView();
	}

	@Test
	public void donateTabDoesHaveDonateButton() {
		assertNotNull(view.findViewById(R.id.donate_button));
	}
	
	@Test
	public void donateTabHasDonateAmountsSpinner() {
		assertNotNull(view.findViewById(R.id.donation_amounts_spinner));
	}

}
