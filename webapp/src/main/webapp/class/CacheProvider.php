<?php

require_once 'class/config/Config.php';
require_once 'class/config/cache/CacheData.php';
require_once 'class/config/cache/CacheConfig.php';

final class CacheProvider {

	public static function getCache(Config $config) {
		$key = $config->getLocation();
		if (file_exists("cache/$key.cache")) {
			return unserialize(file_get_contents("cache/$key.cache"));
		}
		return false;
	}
	
	public static function saveCache(Config $config, $data) {
		$key = $config->getLocation();
		$time = time();
		$data = array(
			CacheData::DATA => $data,
			CacheData::CREATED => $time,
			CacheData::EXPIRES => ($time + CacheConfig::EXPIRATION_SECONDS)
		);
		file_put_contents("cache/$key.cache", serialize($data));
		return $data;
	}
	
	public static function isExpired($cache) {
		if ($cache) {
			return time() > $cache[CacheData::EXPIRES];
		}
		return true;
	}
	
	private static function getKey(Config $config) {
		return $config->getLocation();
	}
	
}

?>