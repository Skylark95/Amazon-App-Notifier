package com.skylark95.amazonfreenotify.dialog;

import static com.xtremelabs.robolectric.Robolectric.*;
import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;

import android.os.Bundle;

import com.actionbarsherlock.app.SherlockDialogFragment;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.skylark95.amazonfreenotify.R;
import com.xtremelabs.robolectric.RobolectricTestRunner;
import com.xtremelabs.robolectric.shadows.ShadowDialog;

@RunWith(RobolectricTestRunner.class)
public class HtmlDialogFragmentTest {

	private static final String KEY_TITLE = "title";
	private static final String KEY_HTML = "html";

	@Test
	public void callingNewInstanceSetsArguments() {
		SherlockDialogFragment htmlDialog = HtmlDialogFragment.newInstance(0, 1);
		Bundle bundle = htmlDialog.getArguments();
		assertEquals(0, bundle.getInt(KEY_TITLE));
		assertEquals(1, bundle.getInt(KEY_HTML));
	}
	
	@Test
	public void onCreateDialogCreatesCorrectDialogWithTitle() {
		SherlockFragmentActivity activity = new SherlockFragmentActivity();
		SherlockDialogFragment htmlDialog = HtmlDialogFragment.newInstance(R.string.changelog_title, R.string.html_changelog);
		htmlDialog.show(activity.getSupportFragmentManager(), "test");
		
		ShadowDialog shadowDialog = shadowOf(htmlDialog.getDialog());
		String title = activity.getString(R.string.changelog_title);
		assertEquals(title, shadowDialog.getTitle());
	}

}
