package com.skylark95.amazonfreenotify.net;

import java.io.IOException;

import com.skylark95.amazonfreenotify.beans.AppDataResponse;

public interface AppDataReader {
	
	public AppDataResponse downloadAppData(String appDataUrl) throws IOException;

}
