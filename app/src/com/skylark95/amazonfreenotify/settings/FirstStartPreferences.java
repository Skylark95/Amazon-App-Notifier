package com.skylark95.amazonfreenotify.settings;

import com.skylark95.amazonfreenotify.util.Logger;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;


public final class FirstStartPreferences {
	
	private static final String PREF_IS_FIRST_START = "_is_first_start";
	
	private static final String TAG = Logger.getTag(FirstStartPreferences.class);
	
	private FirstStartPreferences() {
	}
	
	public static boolean isFirstStart(Context context) {
		SharedPreferences pref = context.getSharedPreferences(PREF_IS_FIRST_START, Context.MODE_PRIVATE);
		boolean firstStart = pref.getBoolean(PREF_IS_FIRST_START, true);
		
		if (firstStart) {
			Log.i(TAG, "App First Start");
			handleFirstStart(pref);
		}
		
		return firstStart;
	}

	private static void handleFirstStart(SharedPreferences pref) {
		Editor editor = pref.edit();
		editor.putBoolean(PREF_IS_FIRST_START, false);
		editor.commit();
	}

}
