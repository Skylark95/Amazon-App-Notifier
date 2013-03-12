package com.skylark95.amazonfreenotify.settings;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.view.MenuItem;
import com.xtremelabs.robolectric.RobolectricTestRunner;

@RunWith(RobolectricTestRunner.class)
public class PrefNotificationDaysTest {

	private static class PrefNotificationDaysTester extends PrefNotificationDays {
		
		private ActionBar actionBar;
		
		public PrefNotificationDaysTester() {
			actionBar = mock(ActionBar.class);
		}

		@Override
		public ActionBar getSupportActionBar() {
			return actionBar;
		}		
		
	}

	private PrefNotificationDaysTester activity;
	private ActionBar mockActionBar;
	
	@Before
	public void setUp() {
		activity = new PrefNotificationDaysTester();
		mockActionBar = activity.getSupportActionBar();
	}

	@Test
	public void onCreateSetsDisplayUpHomeEnabled() {
		activity.onCreate(null);
		verify(mockActionBar).setDisplayHomeAsUpEnabled(true);
	}
	
	@Test
	public void onOptionsItemSelectedHandlesHome() {
		MenuItem mockMenuItem = mock(MenuItem.class);
		when(mockMenuItem.getItemId()).thenReturn(android.R.id.home);
		assertTrue(activity.onOptionsItemSelected(mockMenuItem));
	}
	
	@Test
	public void onOptionsItemSelectedDefaultCallsSuper() {
		MenuItem mockMenuItem = mock(MenuItem.class);
		when(mockMenuItem.getItemId()).thenReturn(0);
		assertFalse(activity.onOptionsItemSelected(mockMenuItem));
	}

}
