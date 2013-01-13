<?php

include 'AppDataParser.php';
include 'DBHelper.php';
 
if ($_GET["name"] == null) {
	echo 'Missing param "name"';
} else {
	switch ($_GET["name"]) {
		case 'parse':
			testParse();
			break;
		case 'insert':
			testInsert();
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
	$dbHelper->insertAppData($appData);
}

?>