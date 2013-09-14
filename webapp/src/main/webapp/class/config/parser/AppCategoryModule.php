<?php

require_once 'class/parser/ParserModule.php';

class AppCategoryModule implements ParserModule {
	
	private $headerTitle;
	
	public function __construct($headerTitle) {
		$this->headerTitle = $headerTitle;
	}
	
	public function getResult(DOMXPath $domXPath) {
		$results = $domXPath->query('//*[contains(@class,"bucket")]');
		foreach($results as $result) {
			if ($this->hasHeaderTitle($result)) {
				$links = $result->getElementsByTagName('a');
				return $links->item($links->length - 1)->nodeValue;
			}
		}
		return null;
	}
	
	private function hasHeaderTitle(DOMElement $result) {
		foreach ($result->getElementsByTagName('h2') as $h2) {
			if (strpos($h2->nodeValue, $this->headerTitle) !== false) {
				return true;
			}
		}
		return false;
	}
	
}

?>