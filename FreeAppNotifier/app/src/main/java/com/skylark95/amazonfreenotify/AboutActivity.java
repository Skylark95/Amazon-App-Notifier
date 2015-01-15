package com.skylark95.amazonfreenotify;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.webkit.WebView;


public class AboutActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WebView webView = new WebView(this);
        webView.loadUrl("file:///android_asset/about.html");
        setContentView(webView);
    }

}
