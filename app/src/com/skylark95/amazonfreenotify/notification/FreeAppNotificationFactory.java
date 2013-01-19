package com.skylark95.amazonfreenotify.notification;

import java.io.IOException;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.util.Log;

import com.skylark95.amazonfreenotify.R;
import com.skylark95.amazonfreenotify.beans.AppDataResponse;
import com.skylark95.amazonfreenotify.net.AppDataReader;
import com.skylark95.amazonfreenotify.settings.Preferences;
import com.skylark95.amazonfreenotify.util.Logger;
import com.skylark95.amazonfreenotify.util.NetworkUtils;

public final class FreeAppNotificationFactory {
	
	private static final String TAG = Logger.getTag(FreeAppNotificationFactory.class);
	
	private FreeAppNotificationFactory() {
	}

	public static FreeAppNotification buildNotification(Context context) {
		PendingIntent appStore = getAppStoreIntent(context);
		FreeAppNotification notification = buildNotification(context, appStore);
		
		return notification;
	}

	private static FreeAppNotification buildNotification(Context context, PendingIntent appStore) {
		FreeAppNotification notification;
		if (NetworkUtils.isDeviceOnline(context)) {
			notification = buildOnlineNotification(context, appStore);			
		} else {
			notification = buildOfflineNotification(context, appStore);
		}
		return notification;
	}

	private static FreeAppNotification buildOnlineNotification(Context context, PendingIntent appStore) {
		FreeAppNotification notification;
		SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
		if (pref.getBoolean(Preferences.PREF_SHOW_NAME_PRICE, true)) {
			notification = buildAppDataNotification(context, appStore);
		} else {
			notification = buildSimpleNotification(context, appStore);
		}
		return notification;
	}

	private static FreeAppNotification buildAppDataNotification(Context context, PendingIntent appStore) {
		FreeAppNotification notification;
		AppDataResponse response = null;
		try {
			response = AppDataReader.downloadAppData(context.getString(R.string.app_data_url));
			if (appStore != null) {
				notification = new AppDataNotification(context, appStore, response.getFreeAppData());
			} else {
				String title = response.getFreeAppData().getAppTitle();
				String text = getMissingAppStoreText(context);
				PendingIntent storeDownloadLink = getAppStoreDownloadIntent(context);				
				notification = new SimpleAppNotification(context, storeDownloadLink, title, text);
			}
		} catch (IOException e) {
			Log.e(TAG, "Download Error (IOException): " + e.getMessage(), e);
			notification = buildErrorNotification(context, appStore);
		}		
		
		return notification;
	}

	private static FreeAppNotification buildErrorNotification(Context context, PendingIntent appStore) {
		FreeAppNotification notification;
		String text = context.getString(R.string.notification_error_text);
		if (appStore != null) {
			notification = new SimpleAppNotification(context, appStore, text);
		} else {
			PendingIntent storeDownloadLink = getAppStoreDownloadIntent(context);
			notification = new SimpleAppNotification(context, storeDownloadLink, text);
		}
		return notification;
	}

	private static FreeAppNotification buildSimpleNotification(Context context, PendingIntent appStore) {
		FreeAppNotification notification;
		if (appStore != null) {
			String text = context.getString(R.string.notification_simple_text);
			notification = new SimpleAppNotification(context, appStore, text);
		} else {
			String text = getMissingAppStoreText(context);			
			PendingIntent storeDownloadLink = getAppStoreDownloadIntent(context);
			notification = new SimpleAppNotification(context, storeDownloadLink, text);			
		}
		return notification;
	}

	private static FreeAppNotification buildOfflineNotification(Context context, PendingIntent appStore) {
		FreeAppNotification notification;
		String text = context.getString(R.string.notification_simple_offline_text);		
		if (appStore != null) {
			notification = new SimpleAppNotification(context, appStore, text);
		} else {
			PendingIntent storeDownloadLink = getAppStoreDownloadIntent(context);
			notification = new SimpleAppNotification(context, storeDownloadLink, text);
		}
		return notification;
	}

	private static PendingIntent getAppStoreDownloadIntent(Context context) {
		String url = context.getString(R.string.app_store_download_url);
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.setData(Uri.parse(url));
		return PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_ONE_SHOT);
	}

	private static PendingIntent getAppStoreIntent(Context context) {		
		Intent intent = context.getPackageManager().getLaunchIntentForPackage(context.getString(R.string.app_store_package));
		PendingIntent pendingIntent = null;
		
		if (intent != null) {
			pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_ONE_SHOT);
		} 
		
		return pendingIntent;
	}

	private static String getMissingAppStoreText(Context context) {
		return context.getString(R.string.notification_simple_no_app_store_text);
	}

}
