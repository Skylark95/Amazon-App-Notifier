package com.skylark95.amazonfreenotify.settings;

import static com.xtremelabs.robolectric.Robolectric.*;
import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.xtremelabs.robolectric.RobolectricTestRunner;
import com.xtremelabs.robolectric.shadows.ShadowApplication;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(RobolectricTestRunner.class)
public class AppVersionPreferencesTest {
    
    private Context context;
    private PackageManager mockPackageManager;
    private PackageInfo packageInfo;
    private SharedPreferences pref;

    @Before
    public void setUp() throws NameNotFoundException {
        context = new SherlockFragmentActivity();
        packageInfo = new PackageInfo();
        pref = context.getSharedPreferences(AppVersionPreferences.PREF_APP_VERSION_CODE, Context.MODE_PRIVATE);
        mockPackageManager = mock(PackageManager.class);
        ShadowApplication shadowApplication = shadowOf(application);
        shadowApplication.setPackageManager(mockPackageManager);
        
    }

    @Test
    public void doesNotDetectUpdateIfVersionCodeZero() {
        whenTheVersionCodeIs(0);
        assertFalse(AppVersionPreferences.isAppUpdate(context));
    }
    
    @Test
    public void doesDetectUpdateIfNoExistingVersionCode() {
        whenTheVersionCodeIs(1);
        assertTrue(AppVersionPreferences.isAppUpdate(context));
    }
    
    @Test
    public void doesUpdateVersionCodeIfUpdate() {
        assertEquals(0, pref.getInt(AppVersionPreferences.PREF_APP_VERSION_CODE, 0));
        whenTheVersionCodeIs(1);
        assertTrue(AppVersionPreferences.isAppUpdate(context));
        assertEquals(1, pref.getInt(AppVersionPreferences.PREF_APP_VERSION_CODE, 0));
        assertFalse(AppVersionPreferences.isAppUpdate(context));
    }
    
    @Test
    public void doesNotDetectUpdateIfMatchingVersion() {
        whenTheVersionCodeIs(1);
        pref.edit().putInt(AppVersionPreferences.PREF_APP_VERSION_CODE, 1).commit();
        assertFalse(AppVersionPreferences.isAppUpdate(context));
    }

    private void whenTheVersionCodeIs(int versionCode) {
        packageInfo.versionCode = versionCode;
        try {
            when(mockPackageManager.getPackageInfo(context.getPackageName(), 0)).thenReturn(packageInfo);
        } catch (NameNotFoundException e) {
            fail(e.getMessage());
        }
    }

}
