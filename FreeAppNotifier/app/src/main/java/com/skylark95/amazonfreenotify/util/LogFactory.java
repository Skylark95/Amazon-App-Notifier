package com.skylark95.amazonfreenotify.util;

public final class LogFactory {

    public static final Logger getLogger(Class<?> clazz) {
        return new LoggerImpl(clazz);
    }
}
