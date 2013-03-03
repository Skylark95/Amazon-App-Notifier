package com.skylark95.amazonfreenotify;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.sample.fragments.TabsAdapter;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.commonsware.cwac.wakeful.WakefulIntentService;
import com.skylark95.amazonfreenotify.alarm.FreeAppNotificationListener;
import com.skylark95.amazonfreenotify.settings.FirstStartPreferences;
import com.skylark95.amazonfreenotify.settings.Preferences;
import com.skylark95.amazonfreenotify.tabs.AboutFragment;
import com.skylark95.amazonfreenotify.tabs.ButtonMenuActions;
import com.skylark95.amazonfreenotify.tabs.DonateFragment;
import com.skylark95.amazonfreenotify.tabs.SettingsFragment;
import com.skylark95.amazonfreenotify.util.Logger;

/**
 * Main Activity for Amazon App Notifier
 * 
 * @author Derek
 */
public class AmazonAppNotifier extends SherlockFragmentActivity {
	
	private static final String TAB_KEY = "tab_position";	
	private static final String TAG = Logger.getTag(AmazonAppNotifier.class);
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.v(TAG, "ENTER - onCreate()");
		Preferences.setDefaultValues(this);
		setContentView(R.layout.activity_amazon_app_notifier);
		buildTabs(savedInstanceState);
		
		if (FirstStartPreferences.isFirstStart(this)) {
			Log.v(TAG, "Schedule alarms for first time");
			WakefulIntentService.scheduleAlarms(new FreeAppNotificationListener(), this);
		}		
		
		Log.v(TAG, "EXIT - onCreate()");
	}

	private void buildTabs(Bundle savedInstanceState) {
		ActionBar actionBar = getSupportActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
		TabsAdapter tabsAdapter = new TabsAdapter(this, viewPager);		
		tabsAdapter.addTab(actionBar.newTab().setText(getString(R.string.settings_tab_title)), SettingsFragment.class, null);
		tabsAdapter.addTab(actionBar.newTab().setText(getString(R.string.about_tab_title)), AboutFragment.class, null);
		tabsAdapter.addTab(actionBar.newTab().setText(getString(R.string.donate_tab_title)), DonateFragment.class, null);
		
		if (savedInstanceState != null) {
			Log.v(TAG, "Restoring tab state");
			actionBar.getTabAt(savedInstanceState.getInt(TAB_KEY)).select();
	    }
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
	    super.onSaveInstanceState(outState);
	    Log.v(TAG, "Saving tab state");
	    outState.putInt(TAB_KEY, getSupportActionBar().getSelectedTab().getPosition());
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    MenuInflater inflater = getSupportMenuInflater();
	    inflater.inflate(R.menu.activity_main, menu);
	    return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {		
		switch (item.getItemId()) {
		case R.id.menu_donate:
			Log.v(TAG, "MENU - Donate");
			getSupportActionBar().getTabAt(getSupportActionBar().getTabCount() - 1).select();
			return true;
		case R.id.menu_change_settings:
			Log.v(TAG, "MENU - Change Settings");
			ButtonMenuActions.launchPreferences(this);
			return true;
		case R.id.menu_test_notification:
			Log.v(TAG, "MENU - Test Notification");
			ButtonMenuActions.testNotification(this);
			return true;
		case R.id.menu_changelog:
			Log.v(TAG, "MENU - Changelog");
			ButtonMenuActions.showChangelog(getSupportFragmentManager());
			return true;
		case R.id.menu_uk_users:
			Log.v(TAG, "MENU - UK Users");
			ButtonMenuActions.showUkUsers(getSupportFragmentManager());
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

}
