<?php

define('STARTS_WITH_APP_URL', 'http://www.amazon.com/gp/product/B00AK0X5VQ/');
define('EXPECTED_APP_TITLE', 'Osmos HD');
define('EXPECTED_APP_DEVELOPER', 'Hemisphere Games');
define('EXPECTED_APP_LIST_PRICE', '$2.99');
define('EXPECTED_APP_CATEGORY', 'Games');
define('STARTS_WITH_APP_DESCRIPTION', 'Enter the Darwinian world of a galactic mote');

include 'com/skylark95/amazonnotify/classes/AppDataParser.php';

class AppDataParserTest extends PHPUnit_Framework_TestCase {
	
	private $appData;
	
	public function __construct() {
		$this->appData = AppDataParser::parseToArray();
	}
	
	public function testLiveDataStartsWithAppUrl() {		
		$this->assertStringStartsWith(STARTS_WITH_APP_URL, $this->appData[APP_URL]);
	}
	
	public function testLiveDataEqualsExpectedAppTitle() {		
		$this->assertEquals(EXPECTED_APP_TITLE, $this->appData[APP_TITLE]);
	}
	
	public function testLiveDataEqualsExpectedAppDeveloper() {		
		$this->assertEquals(EXPECTED_APP_DEVELOPER, $this->appData[APP_DEVELOPER]);
	}
	
	public function testLiveDataEqualsExpectedAppListPrice() {		
		$this->assertEquals(EXPECTED_APP_LIST_PRICE, $this->appData[APP_LIST_PRICE]);
	}
	
	public function testLiveDataEqualsExpectedAppCategory() {		
		$this->assertEquals(EXPECTED_APP_CATEGORY, $this->appData[APP_CATEGORY]);
	}
	
	public function testLiveDataStartsWithAppDescription() {
		$this->assertStringStartsWith(STARTS_WITH_APP_DESCRIPTION, $this->appData[APP_DESCRIPTION]);
	}
	
	
	
}

?>