package com.skylark95.amazonfreenotify.notification;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.skylark95.amazonfreenotify.R;
import com.skylark95.amazonfreenotify.util.NetworkUtils;

public final class FreeAppNotificationFactory {
	
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
			notification = buildSimpleNotification(context, appStore);
		} else {
			notification = buildOfflineNotification(context, appStore);
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

	private static String getMissingAppStoreText(Context context) {
		return context.getString(R.string.notification_simple_no_app_store_text);
	}

	private static PendingIntent getAppStoreIntent(Context context) {		
		Intent intent = context.getPackageManager().getLaunchIntentForPackage(context.getString(R.string.app_store_package));
		PendingIntent pendingIntent = null;
		
		if (intent != null) {
			pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_ONE_SHOT);
		} 
		
		return pendingIntent;
	}

}
