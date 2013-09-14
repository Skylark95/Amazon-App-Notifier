<?php

require_once 'class/decorator/Decorator.php';
require_once 'class/config/data/AppDataResponse.php';

class AppDataResponseDecorator implements Decorator {
	
	public function decorate($appData) {
		$appData[AppData::APP_DATE] = date("Y-m-d");	
		$appDataResponse = array();
		$appDataResponse[AppDataResponse::STATUS] = '';
		$appDataResponse[AppDataResponse::MESSAGE] = 'Data retrieved using webapp v3';
		$appDataResponse[AppDataResponse::REQUEST_TIME] = date(DATE_W3C);
		$appDataResponse[AppDataResponse::APP_DATA] = $appData;
		return $appDataResponse;
	}
	
}

?>