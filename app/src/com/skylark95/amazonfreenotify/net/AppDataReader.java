package com.skylark95.amazonfreenotify.net;

import java.io.IOException;
import java.net.URL;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.skylark95.amazonfreenotify.beans.AppDataResponse;

public final class AppDataReader {
	
	private AppDataReader() {
	}
	
	public static AppDataResponse downloadAppData(String appDataUrl) throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		URL url = new URL(appDataUrl);
		return mapper.readValue(url, AppDataResponse.class);
	}

}
