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

package com.skylark95.amazonfreenotify.activity;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.actionbarsherlock.ActionBarSherlockRobolectric;
import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.view.MenuItem;
import com.skylark95.robolectric.AmazonAppNotifierTestRunner;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AmazonAppNotifierTestRunner.class)
public class HelpActivityTest {    
    
    private static class HelpActivityTester extends HelpActivity {
        private ActionBar actionBar;
        
        public HelpActivityTester() {
            actionBar = mock(ActionBar.class);;
        }
        
        @Override
        public ActionBar getSupportActionBar() {
            return actionBar;
        }
    }
    
    private HelpActivityTester activity;
    private ActionBar mockActionBar;

    @Before
    public void setUp()  {
        ActionBarSherlockRobolectric.registerImplementation();
        activity = new HelpActivityTester();
        mockActionBar = activity.getSupportActionBar();
    }

    @Test
    public void verifyHomeUpIsEnabled() {
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
