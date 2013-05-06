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

package com.skylark95.amazonfreenotify.receiver;

import android.content.BroadcastReceiver;

import com.skylark95.amazonfreenotify.settings.OnBootPreferences;
import com.skylark95.amazonfreenotify.settings.Preferences;
import com.xtremelabs.robolectric.RobolectricTestRunner;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(RobolectricTestRunner.class)
public class OnBootTimeoutReceiverTest extends AbstractNotifyOnBootTest {


    @Before
    public void setUp() {        
        super.setUp();
        OnBootPreferences.setOnBoot(activity, true);
    }
    
    @Test
    public void onReceiveDoesNotStartNotificationServiceIfOnBootEnabledAndOnBootPrefFalse() {
        pref.edit().putBoolean(Preferences.PREF_SHOW_NAME_PRICE, true).commit();
        OnBootPreferences.setOnBoot(activity, false);
        whenOnRecieveIsCalled();
        thenTheServiceIsNotStarted();
    }

    @Override
    protected void givenAMatchingAction() {
    }

    @Override
    protected BroadcastReceiver registerReceiverUnderTest() {
        return new OnBootTimeoutReceiver();
    }

}
