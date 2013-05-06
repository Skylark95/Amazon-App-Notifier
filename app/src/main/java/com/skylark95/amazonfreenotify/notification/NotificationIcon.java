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
        // default
        return ORANGE_ICON; 
    }

}
