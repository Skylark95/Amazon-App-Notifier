<?php

require_once 'classes/parser/AppDataParser.php';
require_once 'classes/parser/AppStoreParser.php';
require_once 'libs/simple_html_dom.php';
require_once 'config.php';

class Parser {
	
	public function parseToArray() {
		$appUrl = $this->getAppOfDayUrl(); 
		return $this->buildAppData($appUrl);
	}
	
	private function getAppOfDayUrl() {
		$html = new simple_html_dom();
		$html->load_file(AMAZON_APPSTORE_URL);
		$appStoreParser = new AppStoreParser($html);
		return $appStoreParser->getAppUrl();
	}
	
	private function buildAppData($appUrl) {
		$html = new simple_html_dom();
		$html->load_file($appUrl);
		$appDataParser = new AppDataParser($html);
		
		$appData = array();
		$appData[APP_URL] = $appUrl;
		$appData[APP_TITLE] = $appDataParser->getAppTitle();
		$appData[APP_DEVELOPER] = $appDataParser->getAppDeveloper();
		$appData[APP_LIST_PRICE] = $appDataParser->getAppListPrice();
		$appData[APP_CATEGORY] = $appDataParser->getAppCategory();
		$appData[APP_DESCRIPTION] = $appDataParser->getAppDescription();
		
		return $appData;
	}
	
}