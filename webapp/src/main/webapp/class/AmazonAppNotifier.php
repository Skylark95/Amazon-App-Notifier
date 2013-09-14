<?php

require_once 'class/parser/XPathParser.php';
require_once 'class/config/data/AppData.php';
require_once 'class/decorator/AppStoreDecorator.php';
require_once 'class/decorator/AppDecorator.php';
require_once 'class/decorator/AppDataResponseDecorator.php';

class AmazonAppNotifier {
	
	public function getAppData(Config $config) {
		$appData = array();
		$appData = $this->decorateAppStore($appData, $config);
		$appData = $this->decorateApp($appData, $config);
		$appData = $this->decorateAppDataResponse($appData);
		return $appData;
	}
	
	private function decorateAppStore($appData, $config) {
		$parser = new XPathParser($config->getAppStoreUrl());
		$appStoreDecorator = new AppStoreDecorator($parser, $config);
		return $appStoreDecorator->decorate($appData);
	}
	
	private function decorateApp($appData, $config) {
		$parser = new XPathParser($appData[AppData::APP_URL]);
		$appStoreDecorator = new AppDecorator($parser, $config);
		return $appStoreDecorator->decorate($appData);
	}
	
	private function decorateAppDataResponse($appData) {
		$appDataReponseDecorator = new AppDataResponseDecorator();
		return $appDataReponseDecorator->decorate($appData);
	}
	
}

?>