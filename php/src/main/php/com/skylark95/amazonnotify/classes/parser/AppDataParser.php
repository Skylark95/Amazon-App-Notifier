<?php

require_once 'libs/simple_html_dom.php';
require_once 'config.php';

class AppDataParser {
	
	private $html;
	
	public function __construct($html) {
		if ($html === null) {
			throw new InvalidArgumentException('AppDataParser: $html cannot be null');
		}
		$this->html = $html;
	}
	
	public function getAppTitle() {
		$retVal = DEFAULT_APP_TITLE;
		$text = $this->html->find('span[id=btAsinTitle]', 0);
		
		if ($text !== null) {
			$retVal = trim($text->plaintext);
		}
		
		return $retVal;
	}
	
	public function getAppListPrice() {
		$retVal = DEFAULT_APP_LIST_PRICE;		
		$text = $this->html->find('span[id=listPriceValue]', 0);
		
		if ($text !== null) {
			$retVal = trim($text->plaintext);
		}
		
		return $retVal;
	}
	
	public function getAppDeveloper() {
		$retVal = DEFAULT_APP_DEVELOPER;		
		static $searchKey = 'Developed By:';

		$bucket = AppDataParser::getBucketByName('Technical Details');
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
	
	public function getAppCategory() {
		$retVal = DEFAULT_APP_CATEGORY;
		$bucket = AppDataParser::getBucketByName('Look for Similar Items by Category');
		if ($bucket === null) return $retVal;
		
		$text = $bucket->find('a', 1);
		if ($text !== null) {
			$retVal = $text->plaintext;
		}
		
		return $retVal;		
	}
	
	public function getAppDescription() {
		$retVal = DEFAULT_APP_DESCRIPTION;
		$bucket = AppDataParser::getBucketByName('Product Description');
		if ($bucket === null) return $retVal;
		
		$text = $bucket->find('div[class=aplus]', 0);
		if ($text !== null) {
			$retVal = trim($text->plaintext);
		}
		
		return $retVal;
	}
	
	private function getBucketByName($name) {
		$retVal = null;
		
		foreach ($this->html->find('.bucket') as $bucket) {
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