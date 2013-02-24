package com.skylark95.amazonfreenotify;

import static com.xtremelabs.robolectric.Robolectric.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.content.Intent;
import android.support.v4.app.Fragment;

import com.actionbarsherlock.ActionBarSherlock;
import com.actionbarsherlock.ActionBarSherlockRobolectric;
import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.Tab;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.MenuItem;
import com.skylark95.amazonfreenotify.service.TestAppNotificationService;
import com.skylark95.amazonfreenotify.settings.Preferences;
import com.xtremelabs.robolectric.RobolectricTestRunner;
import com.xtremelabs.robolectric.shadows.ShadowFragmentActivity;
import com.xtremelabs.robolectric.shadows.ShadowIntent;

@RunWith(RobolectricTestRunner.class)
public class AmazonAppNotifierTest {
	
	private class ActionBarActivityTester extends AmazonAppNotifier {
		
		private ActionBar actionBar;

		public ActionBarActivityTester(ActionBar actionBar) {
			this.actionBar = actionBar;
		}

		@Override
		public ActionBar getSupportActionBar() {
			return actionBar;
		}		
		
	}
	
	private SherlockFragmentActivity activity;
	private SherlockFragmentActivity actionBarActivity;
	private ActionBar mockActionBar;

	@Before
    public void setUp() {
		ActionBarSherlock.registerImplementation(ActionBarSherlockRobolectric.class);
		mockActionBar = mock(ActionBar.class);
		actionBarActivity = new ActionBarActivityTester(mockActionBar);
		activity = new AmazonAppNotifier();		
	}
	
	@Test
	public void createsTabsWhenOnCreateIsCalled() {
		Tab mockTab = mock(Tab.class);
		when(mockActionBar.newTab()).thenReturn(mockTab);
		
		Tab mockSettingsTab = mock(Tab.class);
		Tab mockAboutTab = mock(Tab.class);
		Tab mockDonateTab = mock(Tab.class);
		String settingsTitle = actionBarActivity.getString(R.string.settings_tab_title);
		String aboutTitle = actionBarActivity.getString(R.string.about_tab_title);
		String donateTitle = actionBarActivity.getString(R.string.donate_tab_title);				
		when(mockTab.setText(settingsTitle)).thenReturn(mockSettingsTab);
		when(mockTab.setText(aboutTitle)).thenReturn(mockAboutTab);
		when(mockTab.setText(donateTitle)).thenReturn(mockDonateTab);
		
		ShadowFragmentActivity shadowFragmentActivity = shadowOf(actionBarActivity);
		shadowFragmentActivity.callOnCreate(null);
		
		verify(mockActionBar).addTab(mockSettingsTab);
		verify(mockActionBar).addTab(mockAboutTab);
		verify(mockActionBar).addTab(mockDonateTab);		
	}
	
	@Test
	public void menuDoesLaunchSettings() {
		MenuItem mockChangeSettingsMenu = mock(MenuItem.class);        
        when(mockChangeSettingsMenu.getItemId()).thenReturn(R.id.menu_change_settings);
		
		activity.onOptionsItemSelected(mockChangeSettingsMenu);		
		ShadowFragmentActivity shadowFragmentActivity = shadowOf(activity);
		Intent startedIntent = shadowFragmentActivity.getNextStartedActivity();
		ShadowIntent shadowIntent = shadowOf(startedIntent);
		
		assertEquals(shadowIntent.getComponent().getClassName(), Preferences.class.getName());
	}
	
	@Test
	public void menuDoesStartTestNotificationService() {
		MenuItem mockTestNotificationMenu = mock(MenuItem.class);        
        when(mockTestNotificationMenu.getItemId()).thenReturn(R.id.menu_test_notification);
        
        activity.onOptionsItemSelected(mockTestNotificationMenu);
        ShadowFragmentActivity shadowFragmentActivity = shadowOf(activity);
        Intent startedService = shadowFragmentActivity.getNextStartedService();
        ShadowIntent shadowIntent = shadowOf(startedService);
        
        assertEquals(shadowIntent.getComponent().getClassName(), TestAppNotificationService.class.getName());
	}
	
	@Test
	public void menuDoesShowChangelogDialog() {
		MenuItem mockChangelogMenu = mock(MenuItem.class);        
        when(mockChangelogMenu.getItemId()).thenReturn(R.id.menu_changelog);
        
        activity.onOptionsItemSelected(mockChangelogMenu);
        ShadowFragmentActivity shadowFragmentActivity = shadowOf(activity);
        Fragment changelogDialog = shadowFragmentActivity.getSupportFragmentManager().findFragmentByTag("changelog");
        
        assertNotNull(changelogDialog);
        assertEquals(changelogDialog.getArguments().getInt("title"), R.string.changelog_title);
        assertEquals(changelogDialog.getArguments().getInt("html"), R.string.html_changelog);
	}
	
	@Test
	public void menuDoesShowUkUsersDialog() {
		MenuItem mockUkUsersMenu = mock(MenuItem.class);        
        when(mockUkUsersMenu.getItemId()).thenReturn(R.id.menu_uk_users);
        
        activity.onOptionsItemSelected(mockUkUsersMenu);
        ShadowFragmentActivity shadowFragmentActivity = shadowOf(activity);
        Fragment ukUsersDialog = shadowFragmentActivity.getSupportFragmentManager().findFragmentByTag("ukusers");
        
        assertNotNull(ukUsersDialog);
        assertEquals(ukUsersDialog.getArguments().getInt("title"), R.string.uk_users_title);
        assertEquals(ukUsersDialog.getArguments().getInt("html"), R.string.html_uk_users);
	}

}
