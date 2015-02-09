package com.skylark95.amazonfreenotify.view;

import android.view.View;

import com.squareup.picasso.Callback;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HideViewCallback implements Callback {

    private static final Logger LOGGER = LoggerFactory.getLogger(HideViewCallback.class);

    private final View view;

    public HideViewCallback(View view) {
        this.view = view;
    }

    @Override
    public void onSuccess() {
        view.setVisibility(View.GONE);
    }

    @Override
    public void onError() {
        LOGGER.warn("Failed to load app icon");
    }
}
