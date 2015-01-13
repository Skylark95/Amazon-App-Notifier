package com.skylark95.amazonfreenotify.util;

public interface Logger {

    int warn(String message);
    int warn(String message, Throwable throwable);

}
