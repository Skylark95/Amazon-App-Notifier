package com.skylark95.amazonfreenotify.api;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class FreeAppManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(FreeAppManager.class);

    public abstract FreeApp getFreeApp(Context context);

    /*
     * See: https://developer.amazon.com/appsandservices/community/post/Tx3A1TVL67TB24B/Linking-To-the-Amazon-Appstore-for-Android.html
     */
    public void openAppStore(Context context) {
        final FreeApp freeApp = getFreeApp(context);
        try {
            openUri(context, "amzn://apps/android?asin=" + freeApp.getAsin());
        } catch(ActivityNotFoundException e) {
            LOGGER.warn("Amazon Appstore Not Installed");
            openUri(context, "http://www.amazon.com/gp/mas/dl/android?asin=" + freeApp.getAsin());
        }
    }

    private void openUri(Context context, String uri) {
        context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(uri)));
    }

}
