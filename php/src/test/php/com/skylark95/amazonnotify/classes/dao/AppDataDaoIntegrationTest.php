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

require_once 'classes/dao/AppDataDao.php';
require_once 'classes/dao/DataAccessException.php';
require_once 'config.php';

/**
 * @requires extension mysqli
 */
class AppDataDaoIntegrationTest extends PHPUnit_Framework_TestCase {
	
	private $conn;
	private $appData;
	private $appDate;
	
	protected function setUp() {
		$this->conn = AppDataDao::connect();
		$this->appDate = date("Y-m-d");
		$this->appData = array(
				APP_DATE => $this->appDate,
				APP_URL =>  'http://www.amazon.com/gp/product/B00B4KBIZG/',
				APP_TITLE => 'Shape Buster',
				APP_DEVELOPER => 'Game Pill',
				APP_LIST_PRICE => '$0.99',
				APP_CATEGORY => 'Entertainment',
				APP_DESCRIPTION => 'A puzzle game, perfect for unwinding and relaxing.'
		);
	}
	
	/**
	 * @test
	 */
	public function appDataIntegrationTest() {
		$this->deleteAppIfExists();
		$dao = new AppDataDao($this->conn);		
		$dao->insertTodaysAppData($this->appData);
		$returnData = $dao->selectAppDataForDate($this->appData[APP_DATE]);	
		$this->assertEquals($this->appData, $returnData);
	}
	
	/**
	 * @test
	 */
	public function doesNotAllowMultipleInserts() {
		$this->deleteAppIfExists();
		$dao = new AppDataDao($this->conn);
		$dao->insertTodaysAppData($this->appData);
		
		try {
			$dao->insertTodaysAppData($this->appData);
			$this->fail("Expected DataAccessException");
		} catch(DataAccessException $e) {
			$expected = "AppDataDao: Failed to insert: (1062) Duplicate entry '$this->appDate' for key 'PRIMARY'";
			$this->assertEquals($expected, $e->getMessage());
		}
	}
	
	/**
	 * @test
	 * @expectedException DataAccessException
	 * @expectedExceptionMessage AppDataDao: No rows found for 9999-12-31
	 */
	public function throwsExceptionIfNoDataFound() {
		$dao = new AppDataDao($this->conn);
		$dao->selectAppDataForDate('9999-12-31');
	}
	
	private function deleteAppIfExists() {
		$this->conn->query("DELETE FROM free_app_of_day WHERE app_date = '$this->appDate'");
	}
	
}

?>