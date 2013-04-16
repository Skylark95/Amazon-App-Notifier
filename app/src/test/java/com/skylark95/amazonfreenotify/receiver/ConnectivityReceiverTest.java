package com.skylark95.amazonfreenotify.receiver;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import android.net.ConnectivityManager;

import com.skylark95.amazonfreenotify.settings.OnBootPreferences;
import com.skylark95.amazonfreenotify.settings.Preferences;
import com.xtremelabs.robolectric.Robolectric;
import com.xtremelabs.robolectric.RobolectricTestRunner;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(RobolectricTestRunner.class)
public class ConnectivityReceiverTest extends AbstractNotifyOnBootTest {

    
    @Before
    public void setUp() {        
        receiver = new ConnectivityReceiver();
        finishSetup();
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

    @Override
    protected void givenAMatchingAction() {
        when(mockIntent.getAction()).thenReturn(ConnectivityManager.CONNECTIVITY_ACTION);        
    }

}
