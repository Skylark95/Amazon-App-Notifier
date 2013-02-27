package com.skylark95.amazonfreenotify.util;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.actionbarsherlock.app.SherlockFragmentActivity;

public final class TestUtils {
	
	private TestUtils() {
	}

	/**
	 * Test Fragments with Robolectric
	 * 
	 * @see http://stackoverflow.com/questions/11333354/how-can-i-test-fragments-with-robolectric
	 */
	public static void startFragment(Fragment fragment) {
		FragmentManager fragmentManager = new SherlockFragmentActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(fragment, null);
        fragmentTransaction.commit();
	}

}
