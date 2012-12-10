package com.skylark95.amazonfreenotify.util;


public class Logger {
	
	private Logger() {}

	public static <T> String getTag(Class<T> clazz) {
		return clazz.getSimpleName();
	}	

}
