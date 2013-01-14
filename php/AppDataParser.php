<?php

require 'libs/simple_html_dom.php';
require 'ParserUtils.php';
require_once 'Constants.php';

class AppDataParser {

	function parseToArray() {	
		$appUrl = $this->getAppUrl();
		$html = file_get_html($appUrl);
		
		$appData = array();
		$appData[APP_URL] = $appUrl;
		$appData[APP_TITLE] = $this->getAppTitle($html);
		$appData[APP_DEVELOPER] = $this->getAppDeveloper($html);
		$appData[APP_LIST_PRICE] = $this->getAppListPrice($html);
		$appData[APP_CATEGORY] = $this->getAppCategory($html);
		$appData[APP_DESCRIPTION] = $this->getAppDescription($html);
		
		return $appData;
	}
	
	private function getAppUrl() {		
		$html = file_get_html(AMAZON_APPSTORE_URL);		
		$url = $html->find('div[class=app-info-name]', 0)->find('a', 0)->href;
		return AMAZON_DOMAIN . $url;
	}
	
	private function getAppTitle($html) {
		$retVal = 'Name Not Available';
		$text = $html->find('span[id=btAsinTitle]', 0)->plaintext;
		
		if ($text != null) {
			$retVal = trim($text);
		}
		
		return $retVal;
	}
	
	private function getAppListPrice($html) {
		$retVal = 'N/A';		
		$text = $html->find('span[id=listPriceValue]', 0)->plaintext;
		
		if ($text != null) {
			$retVal = trim($text);
		}
		
		return $retVal;
	}
	
	private function getAppDeveloper($html) {
		$retVal = 'N/A';		
		static $searchKey = 'Developed By:';

		$bucket = getBucketByName($html, 'Technical Details');
		if ($bucket == null) return $retVal;
		
		foreach($bucket->find('li') as $li) {			
			$key = $li->find('b', 0)->plaintext;
			if ($key == $searchKey) {
				$text = str_replace($searchKey, '', $li->plaintext);
				$retVal = trim($text);
				break;
			}
		}
		
		return $retVal;
	}
	
	private function getAppCategory($html) {		
		$bucket = getBucketByName($html, 'Look for Similar Items by Category');
		if ($bucket == null) return 'No Category';
		
		$text = $bucket->find('a', 1)->plaintext;
		return $text;		
	}
	
	private function getAppDescription($html) {
		$bucket = getBucketByName($html, 'Product Description');
		if ($bucket == null) return 'Description Not Available';
		
		$text = $bucket->find('div[class=aplus]', 0)->plaintext;
		return trim($text);
	}

}

?>