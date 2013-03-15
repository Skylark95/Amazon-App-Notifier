<?php

require 'libs/simple_html_dom.php';
require_once 'config/AppData.php';

define("AMAZON_DOMAIN", "http://www.amazon.com");
define("AMAZON_APPSTORE_URL", "http://www.amazon.com/mobile-apps/b?ie=UTF8&node=2350149011");

class AppDataParser {
	
	private function __construct() { }

	public static function parseToArray() {	
		$appUrl = AppDataParser::getAppUrl();
		$html = file_get_html($appUrl);
		
		$appData = array();
		$appData[APP_URL] = $appUrl;
		$appData[APP_TITLE] = AppDataParser::getAppTitle($html);
		$appData[APP_DEVELOPER] = AppDataParser::getAppDeveloper($html);
		$appData[APP_LIST_PRICE] = AppDataParser::getAppListPrice($html);
		$appData[APP_CATEGORY] = AppDataParser::getAppCategory($html);
		$appData[APP_DESCRIPTION] = AppDataParser::getAppDescription($html);
		
		return $appData;
	}
	
	private static function getAppUrl() {		
		$html = file_get_html(AMAZON_APPSTORE_URL);		
		$url = $html->find('div[class=app-info-name]', 0)->find('a', 0)->href;
		return AMAZON_DOMAIN . $url;
	}
	
	private static function getAppTitle($html) {
		$retVal = 'Name Not Available';
		$text = $html->find('span[id=btAsinTitle]', 0);
		
		if ($text !== null) {
			$retVal = trim($text->plaintext);
		}
		
		return $retVal;
	}
	
	private static function getAppListPrice($html) {
		$retVal = 'N/A';		
		$text = $html->find('span[id=listPriceValue]', 0);
		
		if ($text !== null) {
			$retVal = trim($text->plaintext);
		}
		
		return $retVal;
	}
	
	private static function getAppDeveloper($html) {
		$retVal = 'N/A';		
		static $searchKey = 'Developed By:';

		$bucket = AppDataParser::getBucketByName($html, 'Technical Details');
		if ($bucket === null) return $retVal;
		
		foreach($bucket->find('li') as $li) {			
			$key = $li->find('strong', 0);
			if ($key !== null && $key->plaintext === $searchKey) {
				$text = str_replace($searchKey, '', $li->plaintext);
				$retVal = trim($text);
				break;
			}
		}
		
		return $retVal;
	}
	
	private static function getAppCategory($html) {
		$retVal = 'No Category';
		$bucket = AppDataParser::getBucketByName($html, 'Look for Similar Items by Category');
		if ($bucket === null) return $retVal;
		
		$text = $bucket->find('a', 1);
		if ($text !== null) {
			$retVal = $text->plaintext;
		}
		
		return $retVal;		
	}
	
	private static function getAppDescription($html) {
		$retVal = 'Description Not Available';
		$bucket = AppDataParser::getBucketByName($html, 'Product Description');
		if ($bucket === null) return $retVal;
		
		$text = $bucket->find('div[class=aplus]', 0);
		if ($text !== null) {
			$retVal = trim($text->plaintext);
		}
		
		return $retVal;
	}
	
	private static function getBucketByName($html, $name) {
		$retVal = null;
		
		foreach ($html->find('.bucket') as $bucket) {
			$header1 = $bucket->find('h2', 0);
			$header2 = $bucket->find('div[class=h2]', 0);
			$header1text = $header1 === null ? '' : $header1->plaintext;
			$header2text = $header2 === null ? '' : $header2->plaintext;
	
			if ($header1text === $name || $header2text === $name) {
				$retVal = $bucket;
				break;
			}
		}
	
		return $retVal;
	}

}

?>