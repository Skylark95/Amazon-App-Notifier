package com.skylark95.amazonfreenotify;

import static com.xtremelabs.robolectric.Robolectric.*;
import static org.junit.Assert.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.app.AlarmManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.actionbarsherlock.ActionBarSherlockRobolectric;
import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.Tab;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.skylark95.amazonfreenotify.service.TestAppNotificationService;
import com.skylark95.amazonfreenotify.settings.Preferences;
import com.xtremelabs.robolectric.RobolectricTestRunner;
import com.xtremelabs.robolectric.shadows.ShadowAlarmManager;
import com.xtremelabs.robolectric.shadows.ShadowAlarmManager.ScheduledAlarm;
import com.xtremelabs.robolectric.shadows.ShadowFragmentActivity;
import com.xtremelabs.robolectric.shadows.ShadowIntent;

@RunWith(RobolectricTestRunner.class)
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
        
        assertEquals(ukUsersDialog.getArguments().getInt("title"), R.string.uk_users_title);
        assertEquals(ukUsersDialog.getArguments().getInt("html"), R.string.html_uk_users);
	}
	
	@Test
	public void menuDoesCallSuperForDefault() {
		MenuItem mockDefaultMenu = mock(MenuItem.class);        
        when(mockDefaultMenu.getItemId()).thenReturn(0);
        
        boolean result = activity.onOptionsItemSelected(mockDefaultMenu);
        assertEquals(new SherlockFragmentActivity().onOptionsItemSelected(mockDefaultMenu), result);
	}

}
