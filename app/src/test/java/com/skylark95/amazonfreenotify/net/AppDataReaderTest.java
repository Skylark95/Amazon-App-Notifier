package com.skylark95.amazonfreenotify.net;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.Scanner;

import org.junit.Test;
import org.junit.runner.RunWith;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.skylark95.amazonfreenotify.beans.AppDataResponse;
import com.xtremelabs.robolectric.RobolectricTestRunner;

@RunWith(RobolectricTestRunner.class)
public class AppDataReaderTest {

	@Test
	public void appDataReaderMapsValuesCorrectly() {
		File file = new File("src/test/resources/testAppData.html");
		try {
			URL url = file.toURI().toURL();
			AppDataReader appDataReader = new AppDataReaderImpl();
			AppDataResponse appData = appDataReader.downloadAppData(url.toString());		
			assertEquals(getJsonString(file), buildJsonString(appData));
		} catch (IOException e) {
			fail(e.getMessage());			
		} 
	}

	private String getJsonString(File file) throws FileNotFoundException {
		return new Scanner(file).nextLine().replaceAll("\\\\/", "/");
	}


	private String buildJsonString(AppDataResponse appData) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(appData);
	}


}
