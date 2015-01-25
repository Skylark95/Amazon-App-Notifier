package com.skylark95.amazonfreenotify;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.skylark95.amazonfreenotify.api.FreeApp;
import com.skylark95.amazonfreenotify.api.MockFreeApp;
import com.skylark95.amazonfreenotify.view.HideViewCallback;
import com.squareup.picasso.Picasso;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class FreeAppNotifierActivity extends ActionBarActivity {

    @InjectView(R.id.app_name) TextView appName;
    @InjectView(R.id.app_icon_progress) ProgressBar appIconProgress;
    @InjectView(R.id.app_icon) ImageView appIcon;
    @InjectView(R.id.developer) TextView developer;
    @InjectView(R.id.price_was) TextView priceWas;
    @InjectView(R.id.app_rating_text) TextView appRatingText;
    @InjectView(R.id.app_rating_bar) RatingBar appRatingBar;
    @InjectView(R.id.category) TextView category;
    @InjectView(R.id.description) TextView description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_free_app_notifier);
        ButterKnife.inject(this);
        loadFreeAppDetails();
    }

    private void loadFreeAppDetails() {
        FreeApp freeApp = new MockFreeApp(this);
        appName.setText(freeApp.getName());
        developer.setText(freeApp.getDeveloper());
        priceWas.setText("$" + freeApp.getOriginalPrice().toPlainString());
        appRatingText.setText(freeApp.getRating().toPlainString());
        appRatingBar.setRating(freeApp.getRating().floatValue());
        priceWas.setPaintFlags(priceWas.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        category.setText(freeApp.getCateogry().toString(this));
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
        int id = item.getItemId();

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

    private void startActivity(Class<? extends Activity> activity) {
        startActivity(new Intent(this, activity));
    }

    private void openPlayStore() {
        final String appPackageName = getPackageName();
        try {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
        } catch (ActivityNotFoundException e) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
        }
    }
}
