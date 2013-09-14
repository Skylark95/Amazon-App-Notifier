<?php

require_once 'class/decorator/Decorator.php';
require_once 'class/config/data/AppData.php';
require_once 'class/parser/XPathParser.php';

class AppDecorator implements Decorator {
	
	private $parser;
	private $config;
	
	public function __construct(XPathParser $parser, Config $config) {
		$this->parser = $parser;
		$this->config = $config;
	}
	
	public function decorate($appData) {
		$appData[AppData::APP_CATEGORY] = $this->getAppCategory();
		return $appData;
	}
	
	private function getAppCategory() {
		$appCategoryModule = $this->config->getAppCategoryModule();
		$appCategory = $this->parser->queryModule($appCategoryModule);
		return $appCategory;
	}
}

?>