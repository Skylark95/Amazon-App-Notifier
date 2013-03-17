<?php

define('TEST_DATE', '2013-03-16');
define('STARTS_WITH_APP_URL', 'http://www.amazon.com/gp/product/B008I3RARW/');
define('EXPECTED_APP_TITLE', 'Race Rally 3D - Racing Car Arcade Fun');
define('EXPECTED_APP_DEVELOPER', 'Sulaba Inc');
define('EXPECTED_APP_LIST_PRICE', '$4.99');
define('EXPECTED_APP_CATEGORY', 'Games');
define('STARTS_WITH_APP_DESCRIPTION', 'The Race Rally is the best 3d arcade racing game features day or night rainy mode with lights on or off and six different camera views');

require_once 'classes/parser/Parser.php';
require_once 'config.php';

/**
 * This test class requires updating the constants with today's 
 * app of the day values before running
 */
class ParserTest extends PHPUnit_Framework_TestCase {
	
	private $appData;
	
	public function __construct() {
		$parser = new Parser();
		$this->appData = $parser->parseToArray();
	}
	
	protected function setUp() {
		$today = date("Y-m-d");
		if (TEST_DATE != $today) {
			$this->markTestSkipped("Cannot run test: Update TEST_DATE first.");
		}
	}
	
	/**
	 * @test
	 */
	public function liveDataStartsWithAppUrl() {		
		$this->assertStringStartsWith(STARTS_WITH_APP_URL, $this->appData[APP_URL]);
	}
	
	/**
	 * @test
	 */
	public function liveDataEqualsExpectedAppTitle() {		
		$this->assertEquals(EXPECTED_APP_TITLE, $this->appData[APP_TITLE]);
	}
	
	/**
	 * @test
	 */
	public function liveDataEqualsExpectedAppDeveloper() {		
		$this->assertEquals(EXPECTED_APP_DEVELOPER, $this->appData[APP_DEVELOPER]);
	}
	
	/**
	 * @test
	 */
	public function liveDataEqualsExpectedAppListPrice() {		
		$this->assertEquals(EXPECTED_APP_LIST_PRICE, $this->appData[APP_LIST_PRICE]);
	}
	
	/**
	 * @test
	 */
	public function liveDataEqualsExpectedAppCategory() {		
		$this->assertEquals(EXPECTED_APP_CATEGORY, $this->appData[APP_CATEGORY]);
	}
	
	/**
	 * @test
	 */
	public function liveDataStartsWithAppDescription() {
		$this->assertStringStartsWith(STARTS_WITH_APP_DESCRIPTION, $this->appData[APP_DESCRIPTION]);
	}
	
	
	
}

?>