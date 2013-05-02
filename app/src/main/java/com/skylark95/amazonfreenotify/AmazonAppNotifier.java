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

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.sample.fragments.TabsAdapter;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.skylark95.amazonfreenotify.donate.google.IabHelper;
import com.skylark95.amazonfreenotify.donate.google.IabHelper.OnIabPurchaseFinishedListener;
import com.skylark95.amazonfreenotify.settings.AppVersionPreferences;
import com.skylark95.amazonfreenotify.settings.FirstStartPreferences;
import com.skylark95.amazonfreenotify.settings.FirstStartPreferences.FirstStartTask;
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

    private IabHelper iabHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.v(TAG, "ENTER - onCreate()");

        // Only set if not called before
        Preferences.setDefaultValues(this);

        // Build View
        ViewPager viewPager = new ViewPager(this);
        viewPager.setId(R.id.pager);
        setContentView(viewPager);
        buildTabs(savedInstanceState, viewPager);

        // First Start Setup
        if (FirstStartPreferences.isFirstStart(this)) {
            new FirstStartTask(this).execute();
        } else if (AppVersionPreferences.isAppUpdate(this)) {
            ButtonMenuActions.showChangelog(getSupportFragmentManager());
        }

        Log.v(TAG, "EXIT - onCreate()");
    }

    public void reload() {
        Intent intent = getIntent();
        overridePendingTransition(0, 0);
        finish();
        overridePendingTransition(0, 0);
        startActivity(intent);
    }

    private void buildTabs(Bundle savedInstanceState, ViewPager viewPager) {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        TabsAdapter tabsAdapter = new TabsAdapter(this, viewPager);
        tabsAdapter.addTab(actionBar.newTab().setText(getString(R.string.settings_tab_title)),
                SettingsFragment.class, null);
        tabsAdapter.addTab(actionBar.newTab().setText(getString(R.string.about_tab_title)),
                AboutFragment.class, null);
        tabsAdapter.addTab(actionBar.newTab().setText(getString(R.string.donate_tab_title)),
                DonateFragment.class, null);

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

    /*
     * Workaround for IAB API not playing nice with fragments
     */
    public void startDonation(IabHelper helper, String sku, int requestCode,
            OnIabPurchaseFinishedListener purchaseFinishedListener) {
        iabHelper = helper;
        iabHelper.launchPurchaseFlow(this, sku, requestCode, purchaseFinishedListener);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d(TAG, "onActivityResult(" + requestCode + "," + resultCode + "," + data);

        // Pass on the activity result to the helper for handling
        if (iabHelper != null && iabHelper.handleActivityResult(requestCode, resultCode, data)) {
            Log.d(TAG, "onActivityResult handled by IABUtil.");
            iabHelper = null;
        } else {
            // not handled, so handle it ourselves (here's where you'd
            // perform any handling of activity results not related to in-app
            // billing...
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

}
