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
