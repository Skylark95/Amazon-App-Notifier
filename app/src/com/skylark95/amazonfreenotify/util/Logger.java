package com.skylark95.amazonfreenotify.util;


public final class Logger {
	
	private Logger() {		
	}

	public static String getTag(Class<?> clazz) {
		return clazz.getSimpleName();
	}	

}
