<?php
/*
 * This file is part of Amazon App Notifier
 *
 * Copyright 2013 Derek <derek@skylark95.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Amazon App Notifier is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Amazon App Notifier. if not, If not, see <http://www.gnu.org/licenses/>.
 *
 *
 */

require_once 'classes/parser/ParserTestConfig.php';
require_once 'classes/parser/Parser.php';
require_once 'config.php';

/**
 * This test class requires updating the constants in ParserTestConfig.php  
 * with today's app of the day values before running
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
			$this->markTestSkipped("Cannot run test: Update ParserTestConfig.php first.");
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