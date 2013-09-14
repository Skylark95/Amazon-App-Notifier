<?php

require_once 'class/decorator/Decorator.php';
require_once 'class/config/data/AppData.php';
require_once 'class/parser/XPathParser.php';

class AppStoreDecorator implements Decorator {
	
	private $parser;
	private $config;
	
	public function __construct(XPathParser $parser, Config $config) {
		$this->parser = $parser;
		$this->config = $config;
	}
	
	public function decorate($appData) {
		$appData[AppData::APP_NAME] = $this->getAppName();
		$appData[AppData::APP_DEVELOPER] = $this->getAppDeveloper();
		$appData[AppData::APP_DESCRIPTION] = $this->getAppDescription();
		$appData[AppData::APP_PRICE] = $this->getAppPrice();
		$appData[AppData::APP_URL] = $this->getAppUrl();
		return $appData;
	}
	
	private function getAppName() {
		$appNameXPath = $this->config->getAppNameXPath();
		$appName = $this->parser->queryFirstValue($appNameXPath);
		return $appName;
	}
	
	private function getAppDeveloper() {
		$appDeveloperXPath = $this->config->getAppDeveloperXPath();
		$appDeveloper = $this->parser->queryFirstValue($appDeveloperXPath);
		$appDeveloper = trim($appDeveloper);
		return str_replace('by ', '', $appDeveloper);
	}
	
	private function getAppDescription() {
		$appDescriptionXPath = $this->config->getAppDescriptionXPath();
		$appDescription = $this->parser->queryFirstValue($appDescriptionXPath);
		return trim($appDescription);
	}
	
	private function getAppPrice() {
		$appPriceXPath = $this->config->getAppPriceXPath();
		$appPrice = $this->parser->queryFirstValue($appPriceXPath);
		return $appPrice;
	}
	
	private function getAppUrl() {
		$appUrlXPath = $this->config->getAppUrlXPath();
		$domain = $this->config->getAppStoreDomain();
		$appUrl = $this->parser->queryFirstValue($appUrlXPath);
		if ($appUrl) {
			$appUrlSplit = explode('ref=', $appUrl, 2);
			$appUrl = $appUrlSplit[0];
			return $domain . $appUrl;
		}
		return null;
	}
	
}

?>