package com.skylark95.amazonfreenotify;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.widget.TabHost;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.sample.fragments.TabsAdapter;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.skylark95.amazonfreenotify.fragment.AboutFragment;
import com.skylark95.amazonfreenotify.fragment.DonateFragment;
import com.skylark95.amazonfreenotify.fragment.SettingsFragment;
import com.skylark95.amazonfreenotify.ui.ButtonMenuActions;

/**
 * Main Activity for Amazon App Notifier
 * 
 * @author Derek
 */
public class AmazonAppNotifier extends SherlockFragmentActivity {
	
	private TabHost tabHost;
	private ViewPager viewPager;
	private TabsAdapter tabsAdapter;
	
	private static final String TAB_KEY = "tab";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_amazon_app_notifier);		
		
		tabHost = (TabHost) findViewById(android.R.id.tabhost);
		tabHost.setup();
		
		viewPager = (ViewPager) findViewById(R.id.pager);
		
		tabsAdapter = new TabsAdapter(this, tabHost, viewPager);		
		tabsAdapter.addTab(tabHost.newTabSpec("settings").setIndicator("Settings"), SettingsFragment.class, null);
		tabsAdapter.addTab(tabHost.newTabSpec("about").setIndicator("About"), AboutFragment.class, null);
		tabsAdapter.addTab(tabHost.newTabSpec("donate").setIndicator("Donate"), DonateFragment.class, null);
		
		if (savedInstanceState != null) {
	        tabHost.setCurrentTabByTag(savedInstanceState.getString(TAB_KEY));
	    }
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
	    super.onSaveInstanceState(outState);
	    outState.putString(TAB_KEY, tabHost.getCurrentTabTag());
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    MenuInflater inflater = getSupportMenuInflater();
	    inflater.inflate(R.menu.activity_main, menu);
	    return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		ButtonMenuActions actions = new ButtonMenuActions();
		
		switch (item.getItemId()) {
		case R.id.menu_donate:
			tabHost.setCurrentTabByTag("donate");
			return true;
		case R.id.menu_change_settings:
			actions.launchPreferences(this);
			return true;
		case R.id.menu_test_notification:
			actions.testNotification(this);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

}
