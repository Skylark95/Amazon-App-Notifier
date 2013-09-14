<?php
	require 'class/AmazonAppNotifier.php';
	require 'class/ConfigProvider.php';	
	
	$location = isset($_GET['location']) ? $_GET['location'] : null; 
	$config = ConfigProvider::getConfig($location);
	
	if ($config) {
		header('Content-Type: application/json');
		header('Loaded-From-Cache: false');
		$amazonAppNotifier = new AmazonAppNotifier();
		$appData = $amazonAppNotifier->getAppData($config);
		echo json_encode($appData);
	} else {
		header('Content-Type: text/plain');
		header('HTTP/1.1 400 Bad Request');
		echo 'HTTP/1.1 400 Bad Request: Missing or invalid location';
	}
?>
