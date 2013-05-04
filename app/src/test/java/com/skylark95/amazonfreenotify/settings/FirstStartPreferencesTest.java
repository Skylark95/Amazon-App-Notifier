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

package com.skylark95.amazonfreenotify.settings;

import static com.xtremelabs.robolectric.Robolectric.*;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import android.app.AlarmManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.skylark95.amazonfreenotify.AmazonAppNotifier;
import com.skylark95.amazonfreenotify.file.CheckboxesFile;
import com.skylark95.amazonfreenotify.file.DayOfWeek;
import com.skylark95.amazonfreenotify.file.FileManager;
import com.skylark95.amazonfreenotify.file.NotificationSoundFile;
import com.skylark95.amazonfreenotify.file.StartupFile;
import com.skylark95.amazonfreenotify.file.TimeAndDayFile;
import com.skylark95.amazonfreenotify.settings.FirstStartPreferences.FirstStartTask;
import com.xtremelabs.robolectric.RobolectricTestRunner;
import com.xtremelabs.robolectric.shadows.ShadowAlarmManager;
import com.xtremelabs.robolectric.shadows.ShadowAlarmManager.ScheduledAlarm;
import com.xtremelabs.robolectric.shadows.ShadowFragmentActivity;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.FileNotFoundException;
import java.util.Map;
import java.util.Map.Entry;

@RunWith(RobolectricTestRunner.class)
public class FirstStartPreferencesTest {

    private AmazonAppNotifier context;
    private LegacyPreferencesConverter converter;
    private SharedPreferences pref;
    private FirstStartTask firstStartTask;
    
    @Before
    public void setUp() {
        context = new AmazonAppNotifier();
        converter = LegacyPreferencesConverter.getInstance();
        pref = PreferenceManager.getDefaultSharedPreferences(context);
        firstStartTask = new FirstStartTask(context);
    }

    @Test
    public void noUpdateWhenStartUpFileDoesNotExist() {
        // Given The Startup File Does Not Exist
        assertFalse(converter.isUpdateFromV1(context));
    }
    
    @Test
    public void updateWhenStartUpFileDoesExist() {
        givenTheStartupFileExists();
        assertTrue(converter.isUpdateFromV1(context));
    }
    
    @Test
    public void doesNotScheduleAlarmIfPreviousVersionHadTheOptionDisabled() {
        givenNotificationsAre(false);
        whenTheDataIsConverted();
        
        ShadowFragmentActivity shadowFragmentActivity = shadowOf(context);
        AlarmManager alarmManager = (AlarmManager) shadowFragmentActivity.getApplicationContext().getSystemService(Context.ALARM_SERVICE);
        ShadowAlarmManager shadowAlarmManager = shadowOf(alarmManager);
        ScheduledAlarm nextScheduledAlarm = shadowAlarmManager.getNextScheduledAlarm();
        assertNull(nextScheduledAlarm);
    }
    
    @Test
    public void doesSetFirstStartToFalseWhenFinished() {
        // Given it is the first start
        SharedPreferences firstStartpref = context.getSharedPreferences(FirstStartPreferences.PREF_IS_FIRST_START, Context.MODE_PRIVATE);
        firstStartpref.edit().putBoolean(FirstStartPreferences.PREF_IS_FIRST_START, true).commit();
        
        whenTheDataIsConverted();
        
        assertFalse(firstStartpref.getBoolean(FirstStartPreferences.PREF_IS_FIRST_START, true));
    }
    
    @Test
    public void canReadDaySettings() {
        givenAllDaysAreDisabled();
        whenTheDataIsConverted();
        thenAllDaysAreDisabled();
    }
    
    @Test
    public void canReadTimeSettings() {
        givenTheTimeIs(15, 05);
        whenTheDataIsConverted();
        assertThat(15 + ":" + 05, equalTo(pref.getString(Preferences.PREF_NOTIFICATION_TIME, "12:00")));
    }
    
    @Test
    public void canReadShowOnBootTrue() {
        givenShowOnBootIs(true);
        whenTheDataIsConverted();
        assertTrue(pref.getBoolean(Preferences.PREF_SHOW_ON_BOOT, false));
    }
    
    @Test
    public void canReadShowOnBootFalse() {
        givenShowOnBootIs(false);
        whenTheDataIsConverted();
        assertFalse(pref.getBoolean(Preferences.PREF_SHOW_ON_BOOT, true));
    }
    
    @Test
    public void canReadNotificationsEnabledTrue() {
        givenNotificationsAre(true);
        whenTheDataIsConverted();
        assertTrue(pref.getBoolean(Preferences.PREF_ENABLED, false));
    }
    
    @Test
    public void canReadNotificationsEnabledFalse() {
        givenNotificationsAre(false);
        whenTheDataIsConverted();
        assertFalse(pref.getBoolean(Preferences.PREF_ENABLED, true));
    }
    
    @Test
    public void canReadShowNamePriceTrue() {
        givenShowNamePriceIs(true);
        whenTheDataIsConverted();
        assertTrue(pref.getBoolean(Preferences.PREF_SHOW_NAME_PRICE, false));
    }
    
    @Test
    public void canReadShowNamePriceFalse() {
        givenShowNamePriceIs(false);
        whenTheDataIsConverted();
        assertFalse(pref.getBoolean(Preferences.PREF_SHOW_NAME_PRICE, true));
    }
    
    @Test
    public void canReadPlaySoundTrue() {
        givenPlaySoundIs(true);
        whenTheDataIsConverted();
        assertTrue(pref.getBoolean(Preferences.PREF_PLAY_NOTIFICATION_SOUND, false));
    }
    
    @Test
    public void canReadPlaySoundFalse() {
        givenPlaySoundIs(false);
        whenTheDataIsConverted();
        assertFalse(pref.getBoolean(Preferences.PREF_PLAY_NOTIFICATION_SOUND, true));
    }
    
    @Test
    public void canReadVibrateTrue() {
        givenVibrateIs(true);
        whenTheDataIsConverted();
        assertTrue(pref.getBoolean(Preferences.PREF_VIBRATE, false));
    }
    
    @Test
    public void canReadVibrateFalse() {
        givenVibrateIs(false);
        whenTheDataIsConverted();
        assertFalse(pref.getBoolean(Preferences.PREF_VIBRATE, true));
    }
    
    @Test
    public void canDeleteOldFiles() {
        givenOldFilesExist();
        whenTheDataIsConverted();
        thenTheOldFileIsNotFound(FileManager.TIME_AND_DATE_FILENAME);
        thenTheOldFileIsNotFound(FileManager.CHECKBOXES_FILENAME);
        thenTheOldFileIsNotFound(FileManager.NOTIFICATION_SOUND_FILENAME);
        thenTheOldFileIsNotFound(FileManager.STARTUP_FILENAME);
    }
    
    private void givenOldFilesExist() {
        givenTheStartupFileExists();
        givenAllDaysAreDisabled();
        givenShowOnBootIs(false);
        givenTheNotificationSoundis("another uri");
        thenTheOldFileExists(FileManager.TIME_AND_DATE_FILENAME);
        thenTheOldFileExists(FileManager.CHECKBOXES_FILENAME);
        thenTheOldFileExists(FileManager.NOTIFICATION_SOUND_FILENAME);
        thenTheOldFileExists(FileManager.STARTUP_FILENAME);
    }

    private void thenTheOldFileIsNotFound(String file) {
        try {
            context.openFileInput(file);
            fail("Found: " + file);
        } catch (FileNotFoundException e) {
            // Expected
        }        
    }
    
    private void thenTheOldFileExists(String file) {
        try {
            context.openFileInput(file);            
        } catch (FileNotFoundException e) {
            fail("Not Found: " + file);
        }
    }

    @Test
    public void canReadNotificationSound() {
        givenTheNotificationSoundis("someUri");
        whenTheDataIsConverted();
        assertThat("someUri", equalTo(pref.getString(Preferences.PREF_NOTIFICATION_SOUND, null)));
    }

    private void givenTheNotificationSoundis(String uri) {
        NotificationSoundFile notificationSound = FileManager.readNotificationSound(context);
        notificationSound.setRingtoneUri(uri);
        FileManager.write(notificationSound, context);
    }

    private void givenVibrateIs(boolean b) {
        CheckboxesFile checkboxes = FileManager.readCheckboxes(context);
        checkboxes.setVibrate(b);
        FileManager.write(checkboxes, context);  
    }

    private void givenPlaySoundIs(boolean b) {
        CheckboxesFile checkboxes = FileManager.readCheckboxes(context);
        checkboxes.setUseSound(b);
        FileManager.write(checkboxes, context);         
    }

    private void givenShowNamePriceIs(boolean b) {
        CheckboxesFile checkboxes = FileManager.readCheckboxes(context);
        checkboxes.setUseInternet(b);
        FileManager.write(checkboxes, context);           
    }

    private void givenNotificationsAre(boolean b) {
        CheckboxesFile checkboxes = FileManager.readCheckboxes(context);
        checkboxes.setShowNotifications(b);
        FileManager.write(checkboxes, context);        
    }

    private void givenShowOnBootIs(boolean b) {
        CheckboxesFile checkboxes = FileManager.readCheckboxes(context);
        checkboxes.setShowAtBoot(b);
        FileManager.write(checkboxes, context);
    }
    
    private void givenTheTimeIs(int hour, int minute) {
        TimeAndDayFile timeAndDay = FileManager.readTimeAndDay(context);
        timeAndDay.setHourOfDay(hour);
        timeAndDay.setMinuteOfDay(minute);
        FileManager.write(timeAndDay, context);  
    }

    private void thenAllDaysAreDisabled() {
        assertFalse(pref.getBoolean(PrefNotificationDays.PREF_NOTIFICATION_DAYS_SUNDAY, true));
        assertFalse(pref.getBoolean(PrefNotificationDays.PREF_NOTIFICATION_DAYS_MONDAY, true));
        assertFalse(pref.getBoolean(PrefNotificationDays.PREF_NOTIFICATION_DAYS_TUESDAY, true));
        assertFalse(pref.getBoolean(PrefNotificationDays.PREF_NOTIFICATION_DAYS_WEDNESDAY, true));
        assertFalse(pref.getBoolean(PrefNotificationDays.PREF_NOTIFICATION_DAYS_THURSDAY, true));
        assertFalse(pref.getBoolean(PrefNotificationDays.PREF_NOTIFICATION_DAYS_FRIDAY, true));
        assertFalse(pref.getBoolean(PrefNotificationDays.PREF_NOTIFICATION_DAYS_SATURDAY, true));
    }

    private void whenTheDataIsConverted() {
        givenTheStartupFileExists();
        firstStartTask.execute();
    }

    private void givenAllDaysAreDisabled() {
        TimeAndDayFile timeAndDay = FileManager.readTimeAndDay(context);
        Map<DayOfWeek, Boolean> daysOfWeek = timeAndDay.getDaysOfWeek();
        
        for (Entry<DayOfWeek, Boolean> day : daysOfWeek.entrySet()) {
            day.setValue(false);
        }
        FileManager.write(timeAndDay, context);
    }

    private void givenTheStartupFileExists() {
        StartupFile startupFile = FileManager.readStartup(context);
        startupFile.setFirstStart(false);
        FileManager.write(startupFile, context);
    }

}
