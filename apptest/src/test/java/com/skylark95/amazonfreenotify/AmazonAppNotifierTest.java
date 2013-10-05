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

package com.skylark95.amazonfreenotify;

import static com.xtremelabs.robolectric.Robolectric.shadowOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import android.app.AlarmManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.actionbarsherlock.ActionBarSherlockRobolectric;
import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.Tab;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.skylark95.amazonfreenotify.activity.HelpActivity;
import com.skylark95.amazonfreenotify.service.TestAppNotificationService;
import com.skylark95.amazonfreenotify.settings.Preferences;
import com.skylark95.robolectric.AmazonAppNotifierTestRunner;
import com.xtremelabs.robolectric.shadows.ShadowAlarmManager;
import com.xtremelabs.robolectric.shadows.ShadowAlarmManager.ScheduledAlarm;
import com.xtremelabs.robolectric.shadows.ShadowFragmentActivity;
import com.xtremelabs.robolectric.shadows.ShadowIntent;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AmazonAppNotifierTestRunner.class)
public class AmazonAppNotifierTest {
	
	private static final String IS_FIRST_START = "_is_first_start";
	private static final String TAB_POSITION = "tab_position";

	private static class AmazonAppNotifierTestWrapper extends AmazonAppNotifier {
		
		private ActionBar actionBar;
		private MenuInflater menuInflater;
		
		public AmazonAppNotifierTestWrapper() {
			this(buildMockActionBar(), mock(MenuInflater.class));
		}

		private static ActionBar buildMockActionBar() {
			ActionBar mockActionBar = mock(ActionBar.class);
			
			Tab mockTab = mock(Tab.class);
			when(mockActionBar.newTab()).thenReturn(mockTab);
			when(mockActionBar.getTabAt(anyInt())).thenReturn(mockTab);
			when(mockActionBar.getSelectedTab()).thenReturn(mockTab);
			when(mockTab.setText(anyString())).thenReturn(mockTab);	
			return mockActionBar;
		}

		public AmazonAppNotifierTestWrapper(ActionBar actionBar, MenuInflater menuInflater) {
			ActionBarSherlockRobolectric.registerImplementation();
			this.actionBar = actionBar;
			this.menuInflater = menuInflater;
		}

		@Override
		public ActionBar getSupportActionBar() {
			return actionBar;
		}

		@Override
		public MenuInflater getSupportMenuInflater() {
			return menuInflater;
		}		
				
	}
	
	private SherlockFragmentActivity activity;
	private ActionBar mockActionBar;
	private MenuInflater mockMenuInflater;
	private Tab mockTab;

	@Before
    public void setUp() {
		activity = new AmazonAppNotifierTestWrapper();
		mockActionBar = activity.getSupportActionBar();
		mockMenuInflater = activity.getSupportMenuInflater();		
		mockTab = mockActionBar.newTab();				
	}
	
	@Test
	public void createsTabsWhenOnCreateIsCalled() {
		Tab mockSettingsTab = mock(Tab.class);
		Tab mockAboutTab = mock(Tab.class);
		Tab mockDonateTab = mock(Tab.class);
		String settingsTitle = activity.getString(R.string.settings_tab_title);
		String aboutTitle = activity.getString(R.string.about_tab_title);
		String donateTitle = activity.getString(R.string.donate_tab_title);				
		when(mockTab.setText(settingsTitle)).thenReturn(mockSettingsTab);
		when(mockTab.setText(aboutTitle)).thenReturn(mockAboutTab);
		when(mockTab.setText(donateTitle)).thenReturn(mockDonateTab);
		
		ShadowFragmentActivity shadowFragmentActivity = shadowOf(activity);
		shadowFragmentActivity.callOnCreate(null);
		
		verify(mockActionBar).addTab(mockSettingsTab);
		verify(mockActionBar).addTab(mockAboutTab);
		verify(mockActionBar).addTab(mockDonateTab);		
	}
	
	@Test
	public void doesScheduleAlarmsOnFirstStart() {
		ShadowFragmentActivity shadowFragmentActivity = shadowOf(activity);
		shadowFragmentActivity.callOnCreate(null);
		
		AlarmManager alarmManager = (AlarmManager) shadowFragmentActivity.getApplicationContext().getSystemService(Context.ALARM_SERVICE);
		ShadowAlarmManager shadowAlarmManager = shadowOf(alarmManager);
		ScheduledAlarm nextScheduledAlarm = shadowAlarmManager.getNextScheduledAlarm();
		assertNotNull(nextScheduledAlarm);
	}
	
	@Test
	public void doesNotScheduleAlarmsIfNotFirstStart() {
		ShadowFragmentActivity shadowFragmentActivity = shadowOf(activity);
		SharedPreferences pref = shadowFragmentActivity.getSharedPreferences(IS_FIRST_START, Context.MODE_PRIVATE);		
		Editor editor = pref.edit();
		editor.putBoolean(IS_FIRST_START, false);
		editor.commit();
		
		shadowFragmentActivity.callOnCreate(null);
		
		AlarmManager alarmManager = (AlarmManager) shadowFragmentActivity.getApplicationContext().getSystemService(Context.ALARM_SERVICE);
		ShadowAlarmManager shadowAlarmManager = shadowOf(alarmManager);
		ScheduledAlarm nextScheduledAlarm = shadowAlarmManager.getNextScheduledAlarm();
		assertNull(nextScheduledAlarm);
	}
	
	@Test
	public void doesRestoreSavedTabIfBundleNotNull() {
		ShadowFragmentActivity shadowFragmentActivity = shadowOf(activity);
		Tab mockTab1 = mock(Tab.class);
		when(mockActionBar.getTabAt(1)).thenReturn(mockTab1);
		Bundle bundle = new Bundle();
		bundle.putInt(TAB_POSITION, 1);		
		
		shadowFragmentActivity.callOnCreate(bundle);
		verify(mockActionBar).getTabAt(1);
		verify(mockTab1).select();
	}
	
	@Test
	public void doesNotRestoreSavedTabIfBundleNull() {
		ShadowFragmentActivity shadowFragmentActivity = shadowOf(activity);
		Tab mockTab1 = mock(Tab.class);
		when(mockActionBar.getTabAt(anyInt())).thenReturn(mockTab1);
		
		shadowFragmentActivity.callOnCreate(null);
		verify(mockActionBar, never()).getTabAt(anyInt());
		verify(mockTab1, never()).select();
	}
	
	@Test
	public void doesSaveTabStateWhenCallingOnSaveInstanceState() {
		ShadowFragmentActivity shadowFragmentActivity = shadowOf(activity);
		when(mockTab.getPosition()).thenReturn(2);
		Bundle bundle = new Bundle();
		
		shadowFragmentActivity.callOnSaveInstanceState(bundle);		
		assertEquals(2, bundle.getInt(TAB_POSITION));
	}
	
	@Test
	public void doesInflateMenuWhenCallingOnCreateOptionsMenu() {
		Menu mockMenu = mock(Menu.class);
		
		activity.onCreateOptionsMenu(mockMenu);
		verify(mockMenuInflater).inflate(R.menu.activity_main, mockMenu);
	}
	
	@Test
	public void menuDoesShowDonateTab() {
		MenuItem mockDonateMenu = mock(MenuItem.class);        
		Tab mockDonateTab = mock(Tab.class);
        when(mockDonateMenu.getItemId()).thenReturn(R.id.menu_donate);
        when(mockActionBar.getTabAt(2)).thenReturn(mockDonateTab);
        when(mockActionBar.getTabCount()).thenReturn(3);
        
		activity.onOptionsItemSelected(mockDonateMenu);		
		verify(mockDonateTab).select();
	}
	
	@Test
	public void menuDoesLaunchSettings() {
	    whenTheMenuIsPressed(R.id.menu_change_settings);
	    thenTheActivityIsLaunched(Preferences.class);
	}
	
	@Test
	public void menuDoesStartTestNotificationService() {
	    whenTheMenuIsPressed(R.id.menu_test_notification);
	    thenTheServiceIsStarted(TestAppNotificationService.class);        
	}
	
	@Test
	public void menuDoesShowChangelogDialog() {
	    whenTheMenuIsPressed(R.id.menu_changelog);
	    thenTheFragmentIsLaunched("changelog", R.string.changelog_title, R.string.html_changelog);
	}
	
	@Test
	public void menuDoesShowUkUsersDialog() {
	    whenTheMenuIsPressed(R.id.menu_uk_users);
	    thenTheFragmentIsLaunched("ukusers", R.string.uk_users_title, R.string.html_uk_users);        
	}
	
	@Test
	public void menuDoesShowHelpActivity() {
	    whenTheMenuIsPressed(R.id.menu_help);	    
        thenTheActivityIsLaunched(HelpActivity.class);
	}

    @Test
    public void menuDoesOpenPlayStore() {
        whenTheMenuIsPressed(R.id.menu_rate);
        thenTheActionIs(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.skylark95.amazonfreenotify"));
        MenuItem mockDefaultMenu = mock(MenuItem.class);        
        when(mockDefaultMenu.getItemId()).thenReturn(R.id.menu_rate);
        
        activity.onOptionsItemSelected(mockDefaultMenu);
        
    }
	
	@Test
	public void menuDoesCallSuperForDefault() {
		MenuItem mockDefaultMenu = mock(MenuItem.class);        
        when(mockDefaultMenu.getItemId()).thenReturn(0);
        
        boolean result = activity.onOptionsItemSelected(mockDefaultMenu);
        assertEquals(new SherlockFragmentActivity().onOptionsItemSelected(mockDefaultMenu), result);
	}

    private void thenTheActionIs(String action, Uri data) {
        ShadowFragmentActivity shadowFragmentActivity = shadowOf(activity);
        Intent startedIntent = shadowFragmentActivity.getNextStartedActivity();
        ShadowIntent shadowIntent = shadowOf(startedIntent);
        
        assertEquals(shadowIntent.getAction(), action);
        assertEquals(shadowIntent.getData(), data);        
    }

    private void whenTheMenuIsPressed(int menuId) {
        MenuItem mockMenuItem = mock(MenuItem.class);        
        when(mockMenuItem.getItemId()).thenReturn(menuId);        
        activity.onOptionsItemSelected(mockMenuItem);
    }

    private void thenTheFragmentIsLaunched(String fragmentTag, int title, int html) {
        ShadowFragmentActivity shadowFragmentActivity = shadowOf(activity);
        Fragment ukUsersDialog = shadowFragmentActivity.getSupportFragmentManager().findFragmentByTag(fragmentTag);
        
        assertEquals(ukUsersDialog.getArguments().getInt("title"), title);
        assertEquals(ukUsersDialog.getArguments().getInt("html"), html);        
    }

    private void thenTheActivityIsLaunched(Class<?> activityClass) {
        ShadowFragmentActivity shadowFragmentActivity = shadowOf(activity);
        Intent startedIntent = shadowFragmentActivity.getNextStartedActivity();
        ShadowIntent shadowIntent = shadowOf(startedIntent);        
        assertEquals(shadowIntent.getComponent().getClassName(), activityClass.getName());
    }

    private void thenTheServiceIsStarted(Class<?> serviceClass) {
        ShadowFragmentActivity shadowFragmentActivity = shadowOf(activity);
        Intent startedService = shadowFragmentActivity.getNextStartedService();
        ShadowIntent shadowIntent = shadowOf(startedService);
        
        assertEquals(shadowIntent.getComponent().getClassName(), serviceClass.getName());        
    }

}
