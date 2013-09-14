<?php

require_once 'class/config/location/USConfig.php';

final class ConfigProvider {
	
	public static function getConfig($location) {
		if (strtoupper($location) === 'US') {
			return new USConfig();
		}
		
		return null;
	}
	
}

?>