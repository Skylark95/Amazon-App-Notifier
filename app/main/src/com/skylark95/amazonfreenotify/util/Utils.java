package com.skylark95.amazonfreenotify.util;

import java.io.InputStream;

public class Utils {

	private Utils() {
	}

	/**
	 * Converts an InputStream to a String
	 * 
	 * @param InputStream to convert to a String
	 * @return Converted String
	 * @see <a
	 *      href="http://stackoverflow.com/questions/309424/read-convert-an-inputstream-to-a-string">Read/convert
	 *      an InputStream to a String</a>
	 */
	public static String convertStreamToString(InputStream is) {
		java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
		return s.hasNext() ? s.next() : "";
	}

}
