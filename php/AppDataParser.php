<?php

require 'libs/simple_html_dom.php';
require 'ParserUtils.php';

class AppDataParser {

	function parseToArray() {	
		$appUrl = $this->getAppUrl();
		$html = file_get_html($appUrl);
		
		$appData = array();
		$appData['appUrl'] = $appUrl;
		$appData['appTitle'] = $this->getAppTitle($html);
		$appData['appDeveloper'] = $this->getAppDeveloper($html);
		$appData['appListPrice'] = $this->getAppListPrice($html);
		$appData['appCategory'] = $this->getAppCategory($html);
		
		return $appData;
	}
	
	private function getAppUrl() {
		static $domain = 'http://www.amazon.com';
		
		$html = file_get_html('http://www.amazon.com/mobile-apps/b?ie=UTF8&node=2350149011');		
		$url = $html->find('div[class=app-info-name]', 0)->find('a', 0)->href;
		return $domain . $url;
	}
	
	private function getAppTitle($html) {
		$text = $html->find('span[id=btAsinTitle]', 0)->plaintext;
		return trim($text);
	}
	
	private function getAppListPrice($html) {
		$text = $html->find('span[id=listPriceValue]', 0)->plaintext;
		return trim($text);
	}
	
	private function getAppDeveloper($html) {
		$retVal = '';		
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
		if ($bucket == null) return '';
		
		$text = $bucket->find('a', 1)->plaintext;
		return $text;		
	}

}

?>