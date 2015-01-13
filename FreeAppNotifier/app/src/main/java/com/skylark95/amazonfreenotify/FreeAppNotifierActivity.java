package com.skylark95.amazonfreenotify;

import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_free_app_notifier);
        ButterKnife.inject(this);
        loadFreeAppDetails();
    }

    private void loadFreeAppDetails() {
        FreeApp freeApp = new MockFreeApp();
        appName.setText(freeApp.getName());
        developer.setText(freeApp.getDeveloper());
        priceWas.setText("$" + freeApp.getOriginalPrice().toPlainString());
        appRatingText.setText(freeApp.getRating().toPlainString());
        appRatingBar.setRating(freeApp.getRating().floatValue());
        priceWas.setPaintFlags(priceWas.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        category.setText(freeApp.getCateogry().getText());
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

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
