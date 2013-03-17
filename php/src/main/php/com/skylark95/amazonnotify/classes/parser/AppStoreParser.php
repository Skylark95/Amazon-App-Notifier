<?php

require_once 'classes/parser/ParserException.php';
require_once 'libs/simple_html_dom.php';

class AppStoreParser {
	
	private $html;
	
	public function __construct($html) {
		if ($html === null) {
			throw new InvalidArgumentException('AppStoreParser: $html cannot be null');
		}
		$this->html = $html;
	}
	
	public function getAppUrl() {
		$appInfoName = $this->html->find('div[class=app-info-name]', 0);		
		if ($appInfoName === null) {
			throw new ParserException('AppStoreParser: Could not find div[class=app-info-name]');
		}
		
		$anchor = $appInfoName->find('a', 0);
		if ($anchor === null) {
			throw new ParserException('AppStoreParser: Could not find url');
		}
		
		return AMAZON_DOMAIN . $anchor->href;
	}
}

?>