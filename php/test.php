<?php

include 'DataAccessException.php';
include 'AppDataParser.php';
include 'DBHelper.php';

 
if ($_GET["name"] == null) {
	echo 'Missing param "name"';
} else {
	try {
		runTest();
	} catch (DataAccessException $e) {
		error_log($e->errorMessage());
		die();
	}
}

function runTest() {
	switch ($_GET["name"]) {
		case 'parse':
			testParse();
			break;
		case 'insert':
			testInsert();
			break;
		case 'select':
			testSelect();
			break;
		default:
			echo 'No test for ' . $_GET["name"];
			break;
	}
}

function testParse() {
	$parser = new AppDataParser();
	$appData = $parser->parseToArray();
	
	echo json_encode($appData);
}

function testInsert() {
	$parser = new AppDataParser();
	$appData = $parser->parseToArray();
	
	$dbHelper = new DBHelper();
	$dbHelper->insertTodaysAppData($appData);
}

function testSelect() {
	$dbHelper = new DBHelper();
	
	$result;	
	if ($_GET["date"] == null || $_GET["date"] == 'today') {
		$result = $dbHelper->selectAppDataForDate(date("Y-m-d"));
	} else {
		$result = $dbHelper->selectAppDataForDate($_GET["date"]);
	}
	
	echo json_encode($result);
}

?>