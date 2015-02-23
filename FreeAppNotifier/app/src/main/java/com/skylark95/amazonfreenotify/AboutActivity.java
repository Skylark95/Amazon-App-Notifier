package com.skylark95.amazonfreenotify;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class AboutActivity extends ActionBarActivity {

    private static final Logger LOGGER = LoggerFactory.getLogger(AboutActivity.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WebView webView = new WebView(this);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new SetVersionWebViewClient());
        webView.loadUrl("file:///android_asset/about.html");
        setContentView(webView);
    }

    private String getVersionHeader() throws PackageManager.NameNotFoundException {
        PackageInfo packageInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
        return getString(R.string.version) + " " + packageInfo.versionName;
    }

    private class SetVersionWebViewClient extends WebViewClient {

        @Override
        public void onPageFinished(WebView view, String url) {
            try {
                view.loadUrl(String.format("javascript:setVersion('%s')", getVersionHeader()));
            } catch (PackageManager.NameNotFoundException e) {
                LOGGER.warn("Could not set app version on about page", e);
            }
        }

        /*
         * Open all links in browser
         */
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
            return true;
        }
    }

}
