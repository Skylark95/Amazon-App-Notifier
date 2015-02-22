package com.skylark95.amazonfreenotify.api;

import android.content.Context;

import com.skylark95.amazonfreenotify.R;

import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Locale;

public class MockFreeApp implements FreeApp {

    private Context context;

    public MockFreeApp(Context context) {
        this.context = context;
    }

    @Override
    public String getName() {
        return "Monument Valley";
    }

    public String getAsin() {
        return "B00KA7JIII";
    }

    @Override
    public BigDecimal getOriginalPrice() {
        return new BigDecimal("3.99");
    }

    @Override
    public float getRating() {
        return 4.4f;
    }

    @Override
    public String getDeveloper() {
        return "ustwo Studio Ltd";
    }

    @Override
    public String getCateogry() {
        return context.getString(R.string.category_games);
    }

    @Override
    public String getIconUrl() {
        return "http://ecx.images-amazon.com/images/I/615FLdfccwL._SY300_.png";
    }

    @Override
    public String getAppUrl() {
        return "http://www.amazon.com/gp/product/B00KA7JIII/";
    }

    @Override
    public String getDescription() {
        return "In Monument Valley you will manipulate impossible architecture and guide a silent princess through a stunningly beautiful world.  Monument Valley is a surreal exploration through fantastical architecture and impossible geometry. Guide the silent princess Ida through mysterious monuments, uncovering hidden paths, unfolding optical illusions and outsmarting the enigmatic Crow People.";
    }

    @Override
    public Locale getLocale() {
        return Locale.US;
    }

    private URL url(String url) {
        try {
            return new URL(url);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }
}
