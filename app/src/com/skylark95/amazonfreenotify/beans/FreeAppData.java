package com.skylark95.amazonfreenotify.beans;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FreeAppData {

	@JsonProperty("app_date")
	private String appDate;
	
	@JsonProperty("app_url")
	private String appUrl;
	
	@JsonProperty("app_title")
	private String appTitle;
	
	@JsonProperty("app_developer")
	private String appDeveloper;
	
	@JsonProperty("app_list_price")
	private String appListPrice;
	
	@JsonProperty("app_category")
	private String appCategory;
	
	@JsonProperty("app_description")
	private String appDescription;

	public String getAppDate() {
		return appDate;
	}

	public void setAppDate(String appDate) {
		this.appDate = appDate;
	}

	public String getAppUrl() {
		return appUrl;
	}

	public void setAppUrl(String appUrl) {
		this.appUrl = appUrl;
	}

	public String getAppTitle() {
		return appTitle;
	}

	public void setAppTitle(String appTitle) {
		this.appTitle = appTitle;
	}

	public String getAppDeveloper() {
		return appDeveloper;
	}

	public void setAppDeveloper(String appDeveloper) {
		this.appDeveloper = appDeveloper;
	}

	public String getAppListPrice() {
		return appListPrice;
	}

	public void setAppListPrice(String appListPrice) {
		this.appListPrice = appListPrice;
	}

	public String getAppCategory() {
		return appCategory;
	}

	public void setAppCategory(String appCategory) {
		this.appCategory = appCategory;
	}

	public String getAppDescription() {
		return appDescription;
	}

	public void setAppDescription(String appDescription) {
		this.appDescription = appDescription;
	}

}