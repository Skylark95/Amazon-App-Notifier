<?php

require 'DataAccessException.php';
require 'AppDataParser.php';
require 'DBHelper.php';

try {
	$parser = new AppDataParser();
	$dbHelper = new DBHelper();
	$appData = $parser->parseToArray();
	
	echo "JSON:\r\n" . json_encode($appData) . "\r\n\r\nStatus:\r\n";
	
	$dbHelper->insertTodaysAppData($appData);
	
	echo "Successfully Added today\'s App to DB";
} catch (DataAccessException $e) {
	echo $e->errorMessage();
	die();
}

?>