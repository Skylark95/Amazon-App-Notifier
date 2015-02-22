package com.skylark95.amazonfreenotify;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.RatingBar;
import android.widget.TextView;

import com.robotium.solo.Solo;
import com.skylark95.amazonfreenotify.api.MockFreeApp;

import static org.assertj.android.api.Assertions.assertThat;

public class FreeAppNotifierActivityInstrumentationTest extends ActivityInstrumentationTestCase2<FreeAppNotifierActivity> {

    private Solo solo;
    private MockFreeApp mockFreeApp;

    public FreeAppNotifierActivityInstrumentationTest() {
        super(FreeAppNotifierActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        solo = new Solo(getInstrumentation(), getActivity());
        mockFreeApp = new MockFreeApp(getActivity());
    }

    public void testCanLoadFreeAppValues() {
        assertThat((TextView) solo.getView(R.id.app_name)).hasText(mockFreeApp.getName());
        assertThat((TextView) solo.getView(R.id.developer)).hasText(mockFreeApp.getDeveloper());
        assertThat((TextView) solo.getView(R.id.price_was)).hasText("$" + mockFreeApp.getOriginalPrice().toPlainString());
        assertThat((RatingBar) solo.getView(R.id.app_rating_bar)).hasRating(mockFreeApp.getRating());
        assertThat((TextView) solo.getView(R.id.app_rating_text)).hasText(String.valueOf(mockFreeApp.getRating()));
        assertThat((TextView) solo.getView(R.id.category)).hasText(mockFreeApp.getCateogry());
        assertThat((TextView) solo.getView(R.id.description)).hasText(mockFreeApp.getDescription());
    }

    public void testCanShowAppImage() {
        assertThat(solo.getView(R.id.app_icon)).isVisible();
    }

    public void testCanOpenSettings() {
        solo.clickOnView(solo.getView(R.id.action_settings));
        assertTrue(solo.waitForActivity(SettingsActivity.class));
    }

    public void testCanOpenAbout() {
        solo.clickOnMenuItem("About");
        assertTrue(solo.waitForActivity(AboutActivity.class));
    }

    public void testCanOpenPlayStore() {
        solo.clickOnMenuItem("Rate");
        solo.waitForLogMessage("Google Play Store Not Installed");
    }

    public void testCanOpenAppStore() {
        solo.clickOnView(solo.getView(R.id.buy));
        solo.waitForLogMessage("Amazon Appstore Not Installed");
    }

    @Override
    protected void tearDown() throws Exception {
        solo.finishOpenedActivities();
    }

}
