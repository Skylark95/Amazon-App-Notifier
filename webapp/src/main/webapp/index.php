<?php
	require 'class/AmazonAppNotifier.php';
	require 'class/ConfigProvider.php';	
	require 'class/CacheProvider.php';
	date_default_timezone_set('GMT');	
	run();
	
	function run() {
		$config = getConfig();
		if ($config) {
			header('Content-Type: application/json');
			echo getAppData($config);
		} else {
			header('Content-Type: text/plain');
			header('HTTP/1.1 400 Bad Request');
			echo 'HTTP/1.1 400 Bad Request: Missing or invalid location';
		}
	}
	
	function getConfig() {
		$location = isset($_GET['location']) ? $_GET['location'] : null;
		return ConfigProvider::getConfig($location);
	}
	
	function doCache() {
		$doCache = isset($_GET['cache']) ? $_GET['cache'] : '';
		return strtolower($doCache) !== 'false';
	}
	
	function getAppData($config) {
		if (doCache()) {
			$cache = CacheProvider::getCache($config);
			if (CacheProvider::isExpired($cache)) {
				return json_encode(getAppDataLive($config));
			} else {
				return json_encode(getAppDataCache($cache));
			}
		} else {
			return json_encode(getAppDataLive($config));
		}
		
	}
	
	function getAppDataCache($cache) {
		header('Cache-Loaded: true');
		header('Cache-Created: ' . headerDate($cache[CacheData::CREATED]));
		header('Cache-Expires: ' . headerDate($cache[CacheData::EXPIRES]));
		return $cache[CacheData::DATA];
	}
	
	function getAppDataLive($config) {
		$amazonAppNotifier = new AmazonAppNotifier();
		$appData = $amazonAppNotifier->getAppData($config);
		$cache = CacheProvider::saveCache($config, $appData);
		header('Cache-Loaded: false');
		header('Cache-Created: ' . headerDate($cache[CacheData::CREATED]));
		header('Cache-Expires: ' . headerDate($cache[CacheData::EXPIRES]));
		return $appData;
	}
	
	function headerDate($date) {
		return date('D, d M Y H:i:s T', $date);
	}
	
?>
