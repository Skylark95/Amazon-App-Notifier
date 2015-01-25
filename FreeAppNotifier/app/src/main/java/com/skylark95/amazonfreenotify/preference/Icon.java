package com.skylark95.amazonfreenotify.preference;

import android.content.Context;

import com.skylark95.amazonfreenotify.R;

public enum Icon {

    APP(R.string.icon_app),
    ORANGE(R.string.icon_orange),
    BLUE(R.string.icon_blue),
    RED(R.string.icon_red),
    GREEN(R.string.icon_green),
    GRAYSCALE(R.string.icon_grayscale),
    TRANSPARENT(R.string.icon_transparent);

    private int resId;

    Icon(int redId) {
        this.resId = redId;
    }

    public static Icon defaultValue() {
        return APP;
    }

    public String toString(Context context) {
        return context.getString(resId);
    }

}
