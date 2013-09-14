<?php

abstract class Config {
	
	protected $appStoreDomain;
	protected $appStoreUrl;
	protected $appUrlXPath;
	protected $appNameXPath;
	protected $appDeveloperXPath;
	protected $appDescriptionXPath;
	protected $appPriceXPath;
	protected $appCategoryModule;
	
	function getAppStoreDomain() {
		return $this->appStoreDomain;
	}
	
	function getAppStoreUrl() { 
		return $this->appStoreUrl; 
	}
	
	function getAppUrlXPath() { 
		return $this->appUrlXPath; 
	}
	
	function getAppNameXPath() { 
		return $this->appNameXPath; 
	}
	
	function getAppDeveloperXPath() { 
		return $this->appDeveloperXPath; 
	}
	
	function getAppDescriptionXPath() { 
		return $this->appDescriptionXPath; 
	}
	
	function getAppPriceXPath() { 
		return $this->appPriceXPath; 
	}
	
	function getAppCategoryModule() {
		return $this->appCategoryModule;
	}
}

?>