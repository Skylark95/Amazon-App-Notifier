<?php

require_once 'classes/parser/Parser.php';
require_once 'classes/parser/ParserException.php';
require_once 'classes/dao/AppDataDao.php';
require_once 'classes/dao/DataAccessException.php';
require_once 'config.php';

/*
 * Do it
 */
date_default_timezone_set('America/Denver');
getAppData();

function getAppData() {	
	$requestTime = isset($_GET['debugtime']) ? strtotime($_GET['debugtime']) : time();
	$requestMode = $_GET['mode'];
	doWorkForMode($requestMode, $requestTime);
}

function doWorkForMode($mode, $requestTime) {
	$parser = new Parser();
	
	if ($mode === 'live') {
		liveMode($parser, $requestTime);
	} else if ($mode === 'default') {
		try{
			$conn = AppDataDao::connect();
			$dao = new AppDataDao($conn);
			defaultMode($parser, $dao, $requestTime);
		} catch (DataAccessException $e) {
			handleDataAccessException($e, $requestTime, $parser);
		}
	} else {
		noMode($requestTime);
	}
}

function handleDataAccessException($e, $requestTime, $parser) {
	try {
		$appData = $parser->parseToArray();
		$status = 'Default: DB Error';
		$message = $e->getMessage();
		echo buildResponse($status, $message, $appData, $requestTime);
	} catch (ParserException $e2) {
		$status = 'Default: DB Error + Parse Error';
		$message = 'DB: ' . $e->getMessage() . ' - Parser: ' . $e2->getMessage();
		echo buildResponse($status, $message, null, $requestTime);
	}
}

/*
 * Modes
 */
function liveMode($parser, $requestTime) {
	try {
		$appData = $parser->parseToArray();
		$status = 'Live: OK';
		$message = 'Loaded App Data in Live Mode';
		
		echo buildResponse($status, $message, $appData, $requestTime);
	} catch (ParserException $e) {
		$status = 'Live: Parse Error';
		$message = $e->getMessage();
		
		echo buildResponse($status, $message, null, $requestTime);
	}
}

function defaultMode($parser, $dao, $requestTime) {	
	$appData = null;
	$message = null;
	$status = 'Default: OK';
	
	if ($requestTime < strtotime('01:00')) {		
		$appData = $dao->selectAppDataForDate(date("Y-m-d", strtotime('-1 day')));
		$message = 'Loaded app data for yesterday';
	} else if ($requestTime >= strtotime('01:00') && $requestTime < strtotime('01:15')) {
		try {
			$appData = $parser->parseToArray();
			$message = 'Loaded live data';
		} catch (ParserException $e) {
			$appData = null;
			$status = 'Default: Parse Error';
			$message = $e->getMessage();
		}
	} else {
		$appData = $dao->selectAppDataForDate(date("Y-m-d"));
		$message = 'Loaded app data for today';
	}
	
	echo buildResponse($status, $message, $appData, $requestTime);
}

function noMode($requestTime) {
	$status = 'No Mode: Warning';
	$message = 'No valid mode has been specified, returning no data';

	echo buildResponse($status, $message, null, $requestTime);	
}

/*
 * Response
 */
function buildResponse($status, $message, $appData, $requestTime) {
	$response = array(
			'status'=>$status,
			'message'=>$message,
			'request_time'=>date(DATE_W3C, $requestTime),
			'app_data'=>$appData
			);

	return json_encode($response);
}

?>