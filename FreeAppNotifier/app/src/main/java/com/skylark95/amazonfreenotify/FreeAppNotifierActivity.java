package com.skylark95.amazonfreenotify;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import com.skylark95.amazonfreenotify.api.FreeApp;
import com.skylark95.amazonfreenotify.api.FreeAppManager;
import com.skylark95.amazonfreenotify.api.MockFreeAppManager;
import com.skylark95.amazonfreenotify.view.HideViewCallback;
import com.squareup.picasso.Picasso;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.NumberFormat;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;


public class FreeAppNotifierActivity extends ActionBarActivity {

    private static final Logger LOGGER = LoggerFactory.getLogger(FreeAppNotifierActivity.class);

    @InjectView(R.id.app_name) TextView appName;
    @InjectView(R.id.app_icon_progress) ProgressBar appIconProgress;
    @InjectView(R.id.app_icon) ImageView appIcon;
    @InjectView(R.id.developer) TextView developer;
    @InjectView(R.id.price_was) TextView priceWas;
    @InjectView(R.id.app_rating_text) TextView appRatingText;
    @InjectView(R.id.app_rating_bar) RatingBar appRatingBar;
    @InjectView(R.id.category) TextView category;
    @InjectView(R.id.description) TextView description;

    private final FreeAppManager appManager;

    public FreeAppNotifierActivity() {
        this(new MockFreeAppManager());
    }

    public FreeAppNotifierActivity(FreeAppManager appManager) {
        this.appManager = appManager;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_free_app_notifier);
        ButterKnife.inject(this);
        loadFreeAppDetails();
    }

    private void loadFreeAppDetails() {
        final FreeApp freeApp = appManager.getFreeApp(this);
        final NumberFormat numberFormat = NumberFormat.getCurrencyInstance(freeApp.getLocale());

        appName.setText(freeApp.getName());
        developer.setText(freeApp.getDeveloper());
        priceWas.setText(numberFormat.format(freeApp.getOriginalPrice()));
        priceWas.setPaintFlags(priceWas.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        appRatingText.setText(String.valueOf(freeApp.getRating()));
        appRatingBar.setRating(freeApp.getRating());
        category.setText(freeApp.getCateogry());
        description.setText(freeApp.getDescription());

        Picasso.with(this).load(freeApp.getIconUrl()).into(appIcon, new HideViewCallback(appIconProgress));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_free_app_notifier, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        final int id = item.getItemId();

        // App Settings
        if (id == R.id.action_settings) {
            startActivity(SettingsActivity.class);
            return true;
        }

        // About App
        if (id == R.id.action_about) {
            startActivity(AboutActivity.class);
            return true;
        }

        // Rate App
        if (id == R.id.action_rate) {
            openPlayStore();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.buy)
    public void buy() {
        appManager.openAppStore(this);
    }

    private void startActivity(Class<? extends Activity> activity) {
        startActivity(new Intent(this, activity));
    }

    private void openUri(String uri) {
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(uri)));
    }

    private void openPlayStore() {
        final String appPackageName = getPackageName();
        try {
            openUri("market://details?id=" + appPackageName);
        } catch (ActivityNotFoundException e) {
            LOGGER.warn("Google Play Store Not Installed");
            openUri("https://play.google.com/store/apps/details?id=" + appPackageName);
        }
    }
}
