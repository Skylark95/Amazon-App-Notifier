package com.skylark95.amazonfreenotify.preference;


import android.content.SharedPreferences;
import android.preference.Preference;

import com.skylark95.amazonfreenotify.R;
import com.skylark95.amazonfreenotify.TestConfig;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.util.FragmentTestUtil;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

@RunWith(RobolectricTestRunner.class)
@Config(manifest = TestConfig.MANIFEST, emulateSdk = TestConfig.EMULATE_SDK)
public class SettingsFragmentTest {

    private static final String NOTIFICATION_URI = "notificationUri";

    @Spy private SettingsFragment settingsFragment;
    @Mock private Preference.OnPreferenceChangeListener updateNotificationSoundSummaryListener;
    @Mock private SharedPreferences.OnSharedPreferenceChangeListener updateNotificationIconSummaryListener;
    @Mock private Preference notificationSound;
    @Mock private SharedPreferences sharedPreferences;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        doReturn(updateNotificationSoundSummaryListener).when(settingsFragment).getUpdateNotificationSoundSummaryListener();
        doReturn(updateNotificationIconSummaryListener).when(settingsFragment).getUpdateNotificationIconSummaryListener();
        doReturn(sharedPreferences).when(settingsFragment).getSharedPreferences();
        doReturn(notificationSound).when(settingsFragment).findPreference(Settings.KEY_PREF_NOTIFICATION_SOUND);
        doReturn(NOTIFICATION_URI).when(sharedPreferences).getString(Settings.KEY_PREF_NOTIFICATION_SOUND, null);
        FragmentTestUtil.startFragment(settingsFragment);
    }

    @Test
    public void doesAddPreferencesFromResource() {
        verify(settingsFragment).addPreferencesFromResource(R.xml.preferences);
    }

    @Test
    public void doesRegisterUpdatePreferenceSummaryListener() {
        verify(notificationSound).setOnPreferenceChangeListener(updateNotificationSoundSummaryListener);
    }

    @Test
    public void doesRegisterUpdateNotificationIconSummaryListener() {
        verify(sharedPreferences).registerOnSharedPreferenceChangeListener(updateNotificationIconSummaryListener);
    }

    @Test
    public void doesSetNotificationSoundSummary() {
        verify(updateNotificationSoundSummaryListener).onPreferenceChange(notificationSound, NOTIFICATION_URI);
    }

    @Test
    public void doesSetNotificationIconSummary() {
        verify(updateNotificationIconSummaryListener).onSharedPreferenceChanged(sharedPreferences, Settings.KEY_PREF_NOTIFY_ICON_COLOR);
    }

    @Test
    public void doesUnregisterUpdateNotificationIconSummaryListenerOnPause() {
        settingsFragment.onPause();
        verify(sharedPreferences).unregisterOnSharedPreferenceChangeListener(updateNotificationIconSummaryListener);
    }

    @Test
    public void canLazyLoadUpdateNotificationSoundSummaryListener() {
        settingsFragment = new SettingsFragment();
        updateNotificationSoundSummaryListener = settingsFragment.getUpdateNotificationSoundSummaryListener();
        assertThat(settingsFragment.getUpdateNotificationSoundSummaryListener()).isSameAs(updateNotificationSoundSummaryListener);
    }

    @Test
    public void canLazyLoadUpdateNotificationIconSummaryListener() {
        settingsFragment = new SettingsFragment();
        updateNotificationIconSummaryListener = settingsFragment.getUpdateNotificationIconSummaryListener();
        assertThat(settingsFragment.getUpdateNotificationIconSummaryListener()).isSameAs(updateNotificationIconSummaryListener);
    }

}
