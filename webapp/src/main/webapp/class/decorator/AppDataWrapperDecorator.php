<?php

require_once 'class/config/data/AppData.php';
require_once 'class/decorator/Decorator.php';

class AppDataWrapperDecorator implements Decorator {
	
	public function decorate($appData) {
		$appDataWrapper = array();
		$appDataWrapper[AppData::APP_DATA] = $appData;
		return $appDataWrapper;
	}
	
}

?>