package com.skylark95.amazonfreenotify.view;

import android.view.View;

import com.skylark95.amazonfreenotify.util.LogFactory;
import com.skylark95.amazonfreenotify.util.Logger;
import com.squareup.picasso.Callback;

public class HideViewCallback implements Callback {

    public static final Logger LOGGER = LogFactory.getLogger(HideViewCallback.class);

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
