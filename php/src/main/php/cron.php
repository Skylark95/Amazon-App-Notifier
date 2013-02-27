<?php

require 'classes/AppDataParser.php';
require 'classes/DBHelper.php';

try {
	$appData = appDataLooper();
	if ($appData == null) {
		echo "Could not read app data from Amazon";
	} else {
		DBHelper::insertTodaysAppData($appData);	
		echo "Successfully Added today's App to DB";
	}
} catch (DataAccessException $e) {
	echo $e->getMessage();
}

/*
 * Try up to 3 times in the case of HTTP request failure
 */
function appDataLooper() {
	$appData = null;
	$count = 0;
	
	while ($appData == null && count < 3) {
		$appData = AppDataParser::parseToArray();
		$count++;
	}
	echo "Retrieved app data after $count try(s)\r\n";
	return $appData;
}

?>