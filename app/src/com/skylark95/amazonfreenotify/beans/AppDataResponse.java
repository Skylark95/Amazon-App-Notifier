package com.skylark95.amazonfreenotify.beans;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AppDataResponse {

	@JsonProperty("status")
	private String status;
	
	@JsonProperty("message")
	private String message;
	
	@JsonProperty("request_time")
	private String requestTime;
	
	@JsonProperty("app_data")
	private FreeAppData freeAppData;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getRequestTime() {
		return requestTime;
	}

	public void setRequestTime(String requestTime) {
		this.requestTime = requestTime;
	}

	public FreeAppData getFreeAppData() {
		return freeAppData;
	}

	public void setFreeAppData(FreeAppData freeAppData) {
		this.freeAppData = freeAppData;
	}

}
