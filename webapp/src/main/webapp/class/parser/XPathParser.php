<?php

require_once 'class/parser/ParserModule.php';
require_once 'class/parser/AbstractXPath.php';

class XPathParser extends AbstractXPath {
	
	private $domXPath;
	
	public function __construct($url) {
		$html = file_get_contents($url);
		$dom = $this->loadHtml($html);
		$this->domXPath = new DomXPath($dom);
	}
	
	public function queryFirstValue($xpath) {
		return $this->queryNodeValue($this->domXPath, $xpath, 0);
	}
	
	public function queryModule(ParserModule $module) {
		return $module->getResult($this->domXPath);
	}
	
	private function loadHTML($html) {
		$dom = new DomDocument();
		libxml_use_internal_errors(true);
		$dom->loadHTML($html);
		libxml_clear_errors();
		return $dom;
	}
}

?>