/*
 * This file is part of Amazon App Notifier
 *
 * Copyright 2013 Derek <derek@skylark95.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Amazon App Notifier is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Amazon App Notifier. if not, If not, see <http://www.gnu.org/licenses/>.
 *
 *
 */

package com.skylark95.amazonfreenotify.net;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import org.junit.Test;
import org.junit.runner.RunWith;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.skylark95.amazonfreenotify.beans.AppDataResponse;
import com.skylark95.amazonfreenotify.util.TestUtils;
import com.xtremelabs.robolectric.RobolectricTestRunner;

@RunWith(RobolectricTestRunner.class)
public class AppDataReaderTest {

	@Test
	public void appDataReaderMapsValuesCorrectly() {
		File file = new File(TestUtils.TEST_APP_DATA_PATH);
		AppDataResponse appData = TestUtils.readTestAppData();
		try {
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
