package com.skylark95.amazonfreenotify.settings;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.skylark95.amazonfreenotify.file.CheckboxesFile;
import com.skylark95.amazonfreenotify.file.DayOfWeek;
import com.skylark95.amazonfreenotify.file.FileManager;
import com.skylark95.amazonfreenotify.file.NotificationSoundFile;
import com.skylark95.amazonfreenotify.file.StartupFile;
import com.skylark95.amazonfreenotify.file.TimeAndDayFile;

import java.util.Map;


public final class LegacyPreferencesConverter {
    
    private static final LegacyPreferencesConverter INSTANCE = new LegacyPreferencesConverter();
    
    private LegacyPreferencesConverter() {
    }

    public static LegacyPreferencesConverter getInstance() {
        return INSTANCE;        
    }

    public boolean isUpdateFromV1(Context context) {
        StartupFile startupFile = FileManager.readStartup(context);
        return !startupFile.isFirstStart();
    }
    
    public void convert(Context context) {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        convert(context, pref);
    }
    
    public void convert(Context context, SharedPreferences pref) {
        read(context, pref);
        delete(context);
    }
   
        
    private void delete(Context context) {        
        FileManager.deleteTimeAndDayLegacyFile(context);
        FileManager.deleteCheckboxesLegacyFile(context);
        FileManager.deleteNotificationSoundLegacyFile(context);
        FileManager.deleteStartupLegacyFile(context);
    }

    private void read(Context context, SharedPreferences pref) {        
        readTimeAndDay(context, pref);
        readCheckboxes(context, pref);
        readNotificationSound(context, pref);
    }

    private void readNotificationSound(Context context, SharedPreferences pref) {
        NotificationSoundFile notificationSound = FileManager.readNotificationSound(context);
        
        pref.edit()
            .putString(Preferences.PREF_NOTIFICATION_SOUND, notificationSound.getRingtoneUri())
            .commit();        
    }

    private void readCheckboxes(Context context, SharedPreferences pref) {
        CheckboxesFile checkboxes = FileManager.readCheckboxes(context);
        
        pref.edit()
            .putBoolean(Preferences.PREF_SHOW_ON_BOOT, checkboxes.isShowAtBoot())
            .putBoolean(Preferences.PREF_ENABLED, checkboxes.isShowNotifications())
            .putBoolean(Preferences.PREF_SHOW_NAME_PRICE, checkboxes.isUseInternet())
            .putBoolean(Preferences.PREF_PLAY_NOTIFICATION_SOUND, checkboxes.isUseSound())
            .putBoolean(Preferences.PREF_VIBRATE, checkboxes.isVibrate())
            .commit();        
    }

    private void readTimeAndDay(Context context, SharedPreferences pref) {
        TimeAndDayFile timeAndDay = FileManager.readTimeAndDay(context);
        String time = timeAndDay.getHourOfDay() + ":" + timeAndDay.getMinuteOfDay();
        Map<DayOfWeek, Boolean> daysOfWeek = timeAndDay.getDaysOfWeek();
            
        pref.edit()
            .putString(Preferences.PREF_NOTIFICATION_TIME, time)
            .putBoolean(PrefNotificationDays.PREF_NOTIFICATION_DAYS_SUNDAY, daysOfWeek.get(DayOfWeek.SUNDAY))
            .putBoolean(PrefNotificationDays.PREF_NOTIFICATION_DAYS_MONDAY, daysOfWeek.get(DayOfWeek.MONDAY))
            .putBoolean(PrefNotificationDays.PREF_NOTIFICATION_DAYS_TUESDAY, daysOfWeek.get(DayOfWeek.TUESDAY))
            .putBoolean(PrefNotificationDays.PREF_NOTIFICATION_DAYS_WEDNESDAY, daysOfWeek.get(DayOfWeek.WEDNESDAY))
            .putBoolean(PrefNotificationDays.PREF_NOTIFICATION_DAYS_THURSDAY, daysOfWeek.get(DayOfWeek.THURSDAY))
            .putBoolean(PrefNotificationDays.PREF_NOTIFICATION_DAYS_FRIDAY, daysOfWeek.get(DayOfWeek.FRIDAY))
            .putBoolean(PrefNotificationDays.PREF_NOTIFICATION_DAYS_SATURDAY, daysOfWeek.get(DayOfWeek.SATURDAY))
            .commit();
    } 

}
