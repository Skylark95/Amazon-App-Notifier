<?php

require_once 'class/config/Config.php';

abstract class SharedConfig extends Config {
	
	public function __construct() {
		$this->appNameXPath = '//*[contains(@class,"fad-widget-app-name")]/a';
		$this->appUrlXPath = '//*[contains(@class,"fad-widget-app-name")]/a/@href';
		$this->appDeveloperXPath = '//*[contains(@class,"fad-widget-by-line")]';
		$this->appDescriptionXPath = '//*[contains(@class,"fad-widget-description")]';
		$this->appPriceXPath = '//*[contains(@class,"fad-widget-original-price")]';
	}
	
}

?>