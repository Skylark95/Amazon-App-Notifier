<?php

include 'classes/DBHelper.php';
include 'classes/AppDataParser.php';

$requestTime = time();
$status = 'OK';
$message = null;
$appData = null;

try {
	if ($requestTime < strtotime('01:00')) {
		$appData = getYesterdaysAppData();
		$message = 'Loaded app data for yesterday';
	} else if ($requestTime >= strtotime('01:00') && $requestTime < strtotime('01:15')) {
		$appData = getLiveAppData();
		$message = 'Loaded live data';
	} else {
		$appData = getTodaysAppData();
		$message = 'Loaded app data for today';
	}
} catch (DataAccessException $e) {
	$appData = getLiveAppData();
	$status = 'Error'; 
	$message = $e->getMessage();
}

echo buildResponse($status, $message, $requestTime, $appData);

/*
 * Functions
 */
function getYesterdaysAppData() {
	return DBHelper::selectAppDataForDate(date("Y-m-d", strtotime('-1 day')));
}

function getLiveAppData() {
	return AppDataParser::parseToArray();	
}

function getTodaysAppData() {	
	return DBHelper::selectAppDataForDate(date("Y-m-d"));
}

function buildResponse($status, $message, $requestTime, $appData) {
	$response = array('status'=>$status,
					  'message'=>$message,
					  'request_time'=>date('Y-m-d H:i:s', $requestTime),
					  'app_data'=>$appData);
	return json_encode($response);
}

?>