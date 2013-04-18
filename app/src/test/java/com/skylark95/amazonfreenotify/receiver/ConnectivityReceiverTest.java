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

import android.content.BroadcastReceiver;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;

import com.skylark95.amazonfreenotify.settings.OnBootPreferences;
import com.skylark95.amazonfreenotify.settings.Preferences;
import com.xtremelabs.robolectric.RobolectricTestRunner;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(RobolectricTestRunner.class)
public class ConnectivityReceiverTest extends NotifyOnBootIntentFilterTest {

    
    @Before
    public void setUp() {        
        super.setUp();
        OnBootPreferences.setOnBoot(activity, true);
    }
    
    @Test
    public void onRecieveDoesNotStartNotificationServiceIfOnBootFalse() {
        givenAMatchingAction();
        givenConnectionStatusIs(true);
        OnBootPreferences.setOnBoot(activity, false);
        whenOnRecieveIsCalled();
        thenTheServiceIsNotStarted();
    }
    
    @Test
    public void onRecieveDoesStartNotificationServiceIfOnBootTrue() {
        givenAMatchingAction();
        givenConnectionStatusIs(true);
        OnBootPreferences.setOnBoot(activity, true);
        whenOnRecieveIsCalled();
        thenTheServiceIsStarted();
    }
    
    @Test
    @Ignore // Not sure on the Robolectric Implementation for this
    public void onRecieveDoesNotStartNotificationServiceIfTimeout() {
        givenAMatchingAction();
        givenConnectionStatusIs(true);
        OnBootPreferences.setOnBoot(activity, true);
        //TODO set system clock to past 5 minutes
        whenOnRecieveIsCalled();
        thenTheServiceIsNotStarted();
    }
    
    @Test
    public void onRecieveDoesSetOnBootFalsefOnBootTrue() {
        givenAMatchingAction();
        givenConnectionStatusIs(true);
        OnBootPreferences.setOnBoot(activity, true);
        whenOnRecieveIsCalled();
        assertFalse(OnBootPreferences.isOnBoot(activity));
    }
    
    @Test
    public void onRecieveDoesSetOnBootFalsefOnBootTrueAndNotConnectedPrefOnBootTrue() {
        givenAMatchingAction();
        givenConnectionStatusIs(false);
        pref.edit().putBoolean(Preferences.PREF_SHOW_NAME_PRICE, true).commit();
        OnBootPreferences.setOnBoot(activity, true);
        whenOnRecieveIsCalled();
        assertTrue(OnBootPreferences.isOnBoot(activity));
    }
    
    @Test
    public void onRecieveDoesSetOnBootFalsefOnBootTrueAndNotConnectedPrefOnBootFalse() {
        givenAMatchingAction();
        givenConnectionStatusIs(false);
        pref.edit().putBoolean(Preferences.PREF_SHOW_NAME_PRICE, false).commit();
        OnBootPreferences.setOnBoot(activity, true);
        whenOnRecieveIsCalled();
        assertFalse(OnBootPreferences.isOnBoot(activity));
    }
    
    @Test
    public void onReceiveDoesNotEnableConnectivityReceiverIfConnected() {
        givenAMatchingAction();
        givenConnectionStatusIs(true);
        whenOnRecieveIsCalled();
        thenConnectivityReceiverIs(PackageManager.COMPONENT_ENABLED_STATE_DISABLED);
    }  
    
    @Test
    public void onReceiveDoesCancelTimeoutAlarm() {
        givenAMatchingAction();
        givenConnectionStatusIs(true);
        OnBootNotificationHandler.scheduleTimeout(activity);
        thenTimeoutRecieverIs(true);
        whenOnRecieveIsCalled();
        thenTimeoutRecieverIs(false);
    }

    @Override
    protected void givenAMatchingAction() {
        when(mockIntent.getAction()).thenReturn(ConnectivityManager.CONNECTIVITY_ACTION);        
    }

    @Override
    protected BroadcastReceiver registerReceiverUnderTest() {
        return new ConnectivityReceiver();
    }

}
