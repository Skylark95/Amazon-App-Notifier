package com.skylark95.amazonfreenotify.activity;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import com.actionbarsherlock.ActionBarSherlockRobolectric;
import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.view.MenuItem;
import com.xtremelabs.robolectric.RobolectricTestRunner;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(RobolectricTestRunner.class)
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
