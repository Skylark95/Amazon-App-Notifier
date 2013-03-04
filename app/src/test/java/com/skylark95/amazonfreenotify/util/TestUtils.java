package com.skylark95.amazonfreenotify.util;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.skylark95.amazonfreenotify.beans.AppDataResponse;
import com.skylark95.amazonfreenotify.net.AppDataReader;
import com.skylark95.amazonfreenotify.net.AppDataReaderImpl;

public final class TestUtils {
		
	public static final String TEST_APP_DATA_PATH = "src/test/resources/testAppData.html";

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
	
	public static AppDataResponse readTestAppData() {
		AppDataResponse retVal = null;
		try {
			File file = new File(TEST_APP_DATA_PATH);
			URL url = file.toURI().toURL();
			AppDataReader appDataReader = new AppDataReaderImpl();
			retVal = appDataReader.downloadAppData(url.toString());		
		} catch (IOException e) {
			fail(e.getMessage());			
		} 
		return retVal;
	}

}
