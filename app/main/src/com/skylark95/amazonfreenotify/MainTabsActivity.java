package com.skylark95.amazonfreenotify;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.widget.TabHost;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.sample.fragments.TabsAdapter;
import com.skylark95.amazonfreenotify.fragment.AboutFragment;
import com.skylark95.amazonfreenotify.fragment.DonateFragment;
import com.skylark95.amazonfreenotify.fragment.SettingsFragment;

/**
 * Main Activity for Amazon App Notifier
 * 
 * @author Derek
 */
public class MainTabsActivity extends SherlockFragmentActivity {
	
	private TabHost tabHost;
	private ViewPager viewPager;
	private TabsAdapter tabsAdapter;
	
	private static final String TAB_KEY = "tab";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);		
		
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

}
