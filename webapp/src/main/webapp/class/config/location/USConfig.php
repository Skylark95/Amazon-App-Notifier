<?php

require_once 'class/config/location/SharedConfig.php';
require_once 'class/config/parser/AppCategoryModule.php';

class USConfig extends SharedConfig {
	
	public function __construct() {
		parent::__construct();
		$this->location = 'US';
		$this->appStoreDomain = 'http://www.amazon.com';
		$this->appStoreUrl = 'http://www.amazon.com/mobile-apps/b?ie=UTF8&node=2350149011';
		$this->appCategoryModule = new AppCategoryModule('Look for Similar Items by Category');
	}
	
}

?>