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

import android.content.pm.PackageManager;

import com.skylark95.amazonfreenotify.settings.Preferences;

import org.junit.Test;

public abstract class NotifyOnBootIntentFilterTest extends AbstractNotifyOnBootTest {

    @Test
    public void onReceiveDoesNotStartNotificationServiceIfNotMatchingAction() {     
        givenConnectionStatusIs(true);
        whenOnRecieveIsCalled();
        thenTheServiceIsNotStarted();
    }

    @Test
    public void onReceiveDoesNotStartNotificationServiceIfNotConnectedAndOnlineNotification() {
        givenAMatchingAction();
        givenConnectionStatusIs(false);
        pref.edit()
            .putBoolean(Preferences.PREF_SHOW_ON_BOOT, true)
            .putBoolean(Preferences.PREF_SHOW_NAME_PRICE, true)
            .commit();          
        
        whenOnRecieveIsCalled();
        thenTheServiceIsNotStarted();
    }

    @Test
    public void onReceiveDoesStartNotificationServiceIfNotConnectedAndOfflineNotification() {
        givenAMatchingAction();
        givenConnectionStatusIs(false);
        pref.edit()
            .putBoolean(Preferences.PREF_SHOW_ON_BOOT, true)
            .putBoolean(Preferences.PREF_SHOW_NAME_PRICE, false)
            .commit();      
        
        whenOnRecieveIsCalled();
        thenTheServiceIsStarted();
    }
    
    @Test
    public void onReceiveDoesEnableConnectivityReceiverIfOnBootNormalOffline() {
        givenAMatchingAction();
        givenConnectionStatusIs(false);
        whenOnRecieveIsCalled();
        thenConnectivityReceiverIs(PackageManager.COMPONENT_ENABLED_STATE_ENABLED);
    }  
    
    @Test
    public void onReceiveDoesEnableTimeoutAlarmIfOnBootNormalOffline() {
        givenAMatchingAction();
        givenConnectionStatusIs(false);
        whenOnRecieveIsCalled();
        thenTimeoutRecieverIs(true);
    }   

}
