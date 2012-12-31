package com.skylark95.amazonfreenotify.notification;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.provider.Settings;

import com.skylark95.amazonfreenotify.R;
import com.skylark95.amazonfreenotify.util.NetworkUtils;

public final class FreeAppNotificationFactory {
	
	private FreeAppNotificationFactory() {
	}

	public static FreeAppNotification build(Context context) {
		FreeAppNotification notification;
		SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
		Intent appStore = context.getPackageManager().getLaunchIntentForPackage(context.getString(R.string.app_store_package));
		
		if (NetworkUtils.isDeviceOnline(context)) {
//			if (!pref.getBoolean(Preferences.PREF_SHOW_NAME_PRICE, true)) {
				notification = buildSimpleNotification(context, appStore);
//			}			
		} else {
			notification = buildSimpleOfflineNotification(context);
		}
		return notification;
	}

	private static FreeAppNotification buildSimpleOfflineNotification(Context context) {
		String text = context.getString(R.string.notification_simple_offline_text);
		Intent networkSettings = new Intent(Settings.ACTION_WIRELESS_SETTINGS);
		FreeAppNotification notification = new SimpleFreeAppNotification(context, networkSettings, text);
		return notification;
	}

	private static FreeAppNotification buildSimpleNotification(Context context, Intent appStore) {
		FreeAppNotification notification;
		if (appStore != null) {
			String text = context.getString(R.string.notification_simple_text);
			notification = new SimpleFreeAppNotification(context, appStore, text);
		} else {
			String text = context.getString(R.string.notification_simple_no_app_store_text);
			
			String url = context.getString(R.string.app_store_download_url);
			Intent storeDownloadLink = new Intent(Intent.ACTION_VIEW);
			storeDownloadLink.setData(Uri.parse(url));
			
			notification = new SimpleFreeAppNotification(context, storeDownloadLink, text);
		}
		return notification;
	}

}
