package com.skylark95.amazonfreenotify.notification;

import com.skylark95.amazonfreenotify.R;

public enum NotificationIcon {
    
    ORANGE_ICON("orange", R.drawable.ic_stat_notify_orange),
    BLUE_ICON("blue", R.drawable.ic_stat_notify_blue),
    RED_ICON("red", R.drawable.ic_stat_notify_red),
    GREEN_ICON("green", R.drawable.ic_stat_notify_green),
    GRAYSCALE_ICON("grayscale", R.drawable.ic_stat_notify_grayscale),
    TRANSPARENT_ICON("transparent", R.drawable.ic_stat_notify_transparent);
    
    private String prefValue;
    private int resourceId;
    
    private NotificationIcon(String prefValue, int resourceId) {
        this.prefValue = prefValue;
        this.resourceId = resourceId;
    }
    
    public String getPrefValue() {
        return prefValue;
    }

    public int getResourceId() {
        return resourceId;
    }
    
    public static NotificationIcon getNotificationIconForPrefValue(String value) {
        for (NotificationIcon notificationIcon : NotificationIcon.values()) {
            if (notificationIcon.getPrefValue().equals(value))  {
                return notificationIcon;
            }
        }
        
        return ORANGE_ICON; //default
    }

}
