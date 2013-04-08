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
