package com.skylark95.amazonfreenotify.net;

import java.io.IOException;
import java.net.URL;

import android.util.Log;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.skylark95.amazonfreenotify.beans.AppDataResponse;
import com.skylark95.amazonfreenotify.util.Logger;

public final class AppDataReaderImpl implements AppDataReader {
	
	private static final String TAG = Logger.getTag(AppDataReaderImpl.class);
	
	public AppDataResponse downloadAppData(String appDataUrl) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		URL url = new URL(appDataUrl);
		Log.i(TAG, "Downloading App Data");
		AppDataResponse response = mapper.readValue(url, AppDataResponse.class);
		Log.i(TAG, "App Data downloaded successfully");
		return response;
	}

}
