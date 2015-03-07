package com.skylark95.amazonfreenotify;

import android.content.pm.PackageManager;
import android.test.ActivityInstrumentationTestCase2;

import com.robotium.solo.By;
import com.robotium.solo.Solo;

import static org.assertj.core.api.Assertions.assertThat;
public class AboutActivityTest extends ActivityInstrumentationTestCase2<FreeAppNotifierActivity> {

    private Solo solo;

    public AboutActivityTest() {
        super(FreeAppNotifierActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        solo = new Solo(getInstrumentation(), getActivity());
        navigateToAbout();
    }

    @Override
    protected void tearDown() throws Exception {
        solo.finishOpenedActivities();
    }

    private void navigateToAbout() {
        solo.clickOnMenuItem("About");
        assertTrue(solo.waitForActivity(AboutActivity.class));
    }

    public void testCanAddVersionNameToAboutPage() throws PackageManager.NameNotFoundException {
        String versionName = getActivity().getPackageManager().getPackageInfo(getActivity().getPackageName(), 0).versionName;
        assertTrue(solo.waitForWebElement(By.id("version")));
        assertThat(solo.getWebElement(By.id("version"), 0).getText()).isEqualTo("Version " + versionName);
    }

}
