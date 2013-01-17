package com.skylark95.amazonfreenotify.notification;

import java.io.IOException;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.provider.Settings;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.skylark95.amazonfreenotify.R;
import com.skylark95.amazonfreenotify.beans.AppDataResponse;
import com.skylark95.amazonfreenotify.beans.FreeAppData;
import com.skylark95.amazonfreenotify.net.AppDataReader;
import com.skylark95.amazonfreenotify.ui.settings.Preferences;
import com.skylark95.amazonfreenotify.util.NetworkUtils;

public final class FreeAppNotificationFactory {
	
	private FreeAppNotificationFactory() {
	}

	public static FreeAppNotification build(Context context) {
		FreeAppNotification notification;
		SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
		Intent appStore = context.getPackageManager().getLaunchIntentForPackage(context.getString(R.string.app_store_package));
		
		if (NetworkUtils.isDeviceOnline(context)) {
			if (pref.getBoolean(Preferences.PREF_SHOW_NAME_PRICE, true)) {
				notification = buildAppDataNotification(context, appStore);
			} else {
				notification = buildSimpleNotification(context, appStore);
			}
		} else {
			notification = buildSimpleOfflineNotification(context);
		}
		return notification;
	}

	private static FreeAppNotification buildAppDataNotification(Context context, Intent appStore) {
		FreeAppNotification notification = null;
		try {
			AppDataResponse response = AppDataReader.downloadAppData(context.getString(R.string.app_data_url));
			FreeAppData freeAppData = response.getFreeAppData();
			
			//if (appStore != null) {
				notification = new AppDataNotification(context, appStore, freeAppData);
			/*} else {
				String text = context.getString(R.string.notification_simple_no_app_store_text);			
				String url = context.getString(R.string.app_store_download_url);
				Intent storeDownloadLink = new Intent(Intent.ACTION_VIEW);
				storeDownloadLink.setData(Uri.parse(url));			
				notification = new SimpleNotification(context, storeDownloadLink, text);
			}*/
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return notification;
	}

	private static FreeAppNotification buildSimpleOfflineNotification(Context context) {
		String text = context.getString(R.string.notification_simple_offline_text);
		Intent networkSettings = new Intent(Settings.ACTION_WIRELESS_SETTINGS);
		FreeAppNotification notification = new SimpleNotification(context, networkSettings, text);
		return notification;
	}

	private static FreeAppNotification buildSimpleNotification(Context context, Intent appStore) {
		FreeAppNotification notification;
		if (appStore != null) {
			String text = context.getString(R.string.notification_simple_text);
			notification = new SimpleNotification(context, appStore, text);
		} else {
			String text = context.getString(R.string.notification_simple_no_app_store_text);			
			String url = context.getString(R.string.app_store_download_url);
			Intent storeDownloadLink = new Intent(Intent.ACTION_VIEW);
			storeDownloadLink.setData(Uri.parse(url));			
			notification = new SimpleNotification(context, storeDownloadLink, text);
		}
		return notification;
	}

}
