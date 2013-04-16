/*
 * This file is part of Amazon App Notifier
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

package com.skylark95.amazonfreenotify.receiver;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import android.content.Intent;

import com.skylark95.amazonfreenotify.settings.OnBootPreferences;
import com.skylark95.amazonfreenotify.settings.Preferences;
import com.xtremelabs.robolectric.RobolectricTestRunner;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(RobolectricTestRunner.class)
public class NotifyOnBootReceiverTest extends AbstractNotifyOnBootTest {

	@Before
	public void setUp() {
		receiver = new NotifyOnBootReceiver();
		finishSetup();
	}
	
	@Test
	public void onReceiveDoesSetOnBootPrefFlagIfOnBootNormal() {
	    givenAMatchingAction();
	    OnBootPreferences.setOnBoot(activity, true);
	    whenOnRecieveIsCalled();
	    assertFalse(OnBootPreferences.isOnBoot(activity));
	}
	
	@Test
    public void onReceiveDoesSetOnBootPrefFlagIfOnBootNoInternet() {
        givenAMatchingAction();
        OnBootPreferences.setOnBoot(activity, false);
        givenConnectionStatusIs(false);
        pref.edit()
            .putBoolean(Preferences.PREF_SHOW_ON_BOOT, true)
            .putBoolean(Preferences.PREF_SHOW_NAME_PRICE, true)
            .commit();  
        
        whenOnRecieveIsCalled();
        assertTrue(OnBootPreferences.isOnBoot(activity));
    }
	
    @Test
	public void onReceiveDoesNotSetOnBootPrefFlagNotOnBoot() {
	    OnBootPreferences.setOnBoot(activity, false);
	    givenConnectionStatusIs(false);
	    whenOnRecieveIsCalled();
	    assertFalse(OnBootPreferences.isOnBoot(activity));
	}
    
    @Test
    public void onReceiveDoesNotEnableConnectivityReceiverIfConnected() {
        givenAMatchingAction();
        givenConnectionStatusIs(true);
        whenOnRecieveIsCalled();
        thenConnectivityReceiverIs(false);
    }   
    
    @Test
    public void onReceiveDoesEnableConnectivityReceiverIfOnBootNormal() {
        givenAMatchingAction();
        givenConnectionStatusIs(false);
        whenOnRecieveIsCalled();
        thenConnectivityReceiverIs(true);
    }   

    @Override
    protected void givenAMatchingAction() {
        when(mockIntent.getAction()).thenReturn(Intent.ACTION_BOOT_COMPLETED);
    }    

}
