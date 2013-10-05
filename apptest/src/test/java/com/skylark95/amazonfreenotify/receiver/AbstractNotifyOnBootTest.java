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

import static com.xtremelabs.robolectric.Robolectric.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import android.app.AlarmManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.preference.PreferenceManager;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.skylark95.amazonfreenotify.service.FreeAppNotificationService;
import com.skylark95.amazonfreenotify.settings.Preferences;
import com.xtremelabs.robolectric.Robolectric;
import com.xtremelabs.robolectric.res.RobolectricPackageManager;
import com.xtremelabs.robolectric.res.RobolectricPackageManager.ComponentState;
import com.xtremelabs.robolectric.shadows.ShadowAlarmManager;
import com.xtremelabs.robolectric.shadows.ShadowAlarmManager.ScheduledAlarm;
import com.xtremelabs.robolectric.shadows.ShadowFragmentActivity;
import com.xtremelabs.robolectric.shadows.ShadowIntent;
import com.xtremelabs.robolectric.shadows.ShadowNetworkInfo;

import org.junit.Before;
import org.junit.Test;

public abstract class AbstractNotifyOnBootTest {
    
    protected SherlockFragmentActivity activity;
    protected BroadcastReceiver receiver;
    protected Intent mockIntent;
    protected SharedPreferences pref;
    
    @Before
    public void setUp() {
        receiver = registerReceiverUnderTest();
        activity = new SherlockFragmentActivity();
        mockIntent = mock(Intent.class);
        pref = PreferenceManager.getDefaultSharedPreferences(activity);
    }
    
    @Test
    public void onReceiveStartsNotificationServiceIfMatchingActionAndShowOnBootEnabled() {
        givenAMatchingAction();
        givenConnectionStatusIs(true);
        pref.edit()
            .putBoolean(Preferences.PREF_SHOW_ON_BOOT, true)
            .putBoolean(Preferences.PREF_SHOW_NAME_PRICE, true)
            .commit();      
        
        whenOnRecieveIsCalled();
        thenTheServiceIsStarted();
    }

    @Test
    public void onReceiveDoesNotStartNotificationServiceIfMatchingActionAndShowOnBootDisabled() {
        givenAMatchingAction();
        givenConnectionStatusIs(true);
        pref.edit()
            .putBoolean(Preferences.PREF_SHOW_ON_BOOT, false)
            .commit();      
        
        whenOnRecieveIsCalled();
        thenTheServiceIsNotStarted();
    }
    
    protected abstract void givenAMatchingAction();
    
    protected abstract BroadcastReceiver registerReceiverUnderTest();

    protected void givenConnectionStatusIs(boolean isConnected) {
        ConnectivityManager connMgr = (ConnectivityManager) Robolectric.application.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        ShadowNetworkInfo shadowNetworkInfo = shadowOf(networkInfo);
        shadowNetworkInfo.setConnectionStatus(isConnected);
    }
    
    protected void thenConnectivityReceiverIs(int enabledState) {
        ComponentName componentName = new ComponentName(activity, ConnectivityReceiver.class);
        RobolectricPackageManager packageManager = (RobolectricPackageManager) activity.getPackageManager();
        ComponentState componentState = packageManager.getComponentState(componentName);
        if (enabledState == PackageManager.COMPONENT_ENABLED_STATE_ENABLED){
            assertEquals(componentState.flags, PackageManager.DONT_KILL_APP);
            assertEquals(componentState.newState, PackageManager.COMPONENT_ENABLED_STATE_ENABLED);
        } else if (enabledState == PackageManager.COMPONENT_ENABLED_STATE_DISABLED) {
            assertEquals(componentState.flags, PackageManager.DONT_KILL_APP);
            assertEquals(componentState.newState, PackageManager.COMPONENT_ENABLED_STATE_DISABLED);  
        } else {
            assertNull(componentState);
        }
    }
    
    protected void thenTheServiceIsStarted() {
        ShadowFragmentActivity shadowFragmentActivity = shadowOf(activity);
        Intent startedService = shadowFragmentActivity.getNextStartedService();
        ShadowIntent shadowIntent = shadowOf(startedService);
            
        assertEquals(shadowIntent.getComponent().getClassName(), FreeAppNotificationService.class.getName());
    }

    protected void thenTheServiceIsNotStarted() {
        ShadowFragmentActivity shadowFragmentActivity = shadowOf(activity);
        Intent startedService = shadowFragmentActivity.getNextStartedService();
        
        assertNull(startedService);
    }
    
    protected void thenTimeoutRecieverIs(boolean b) {
        AlarmManager alarmManager = (AlarmManager) activity.getSystemService(Context.ALARM_SERVICE);
        ShadowAlarmManager shadowAlarmManager = shadowOf(alarmManager);
        ScheduledAlarm nextScheduledAlarm = shadowAlarmManager.getNextScheduledAlarm();
        
        if (b) {
            assertNotNull(nextScheduledAlarm);
            assertEquals(AlarmManager.ELAPSED_REALTIME_WAKEUP, nextScheduledAlarm.type);
            assertEquals(ConnectivityReceiver.TIMEOUT, nextScheduledAlarm.triggerAtTime);            
        } else {
            assertNull(nextScheduledAlarm);
        }
            
    }

    protected void whenOnRecieveIsCalled() {
        receiver.onReceive(activity, mockIntent);
    }

}
