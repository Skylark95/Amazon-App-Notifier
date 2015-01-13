package com.skylark95.amazonfreenotify.util;

import android.util.Log;

public class LoggerImpl implements Logger {

    private String tag;

    public LoggerImpl(Class<?> clazz) {
        this(clazz.getSimpleName());
    }

    public LoggerImpl(String tag) {
        this.tag = tag;
    }

    @Override
    public int warn(String message) {
        return Log.w(tag, message);
    }

    @Override
    public int warn(String message, Throwable throwable) {
        return Log.w(tag, message, throwable);
    }

}
