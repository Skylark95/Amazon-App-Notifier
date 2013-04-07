<?php

require_once 'classes/dao/AppDataDao.php';
require_once 'classes/dao/DataAccessException.php';
require_once 'config.php';

/**
 * @requires extension mysqli
 */
class AppDataDaoIntegrationTest extends PHPUnit_Framework_TestCase {
	
	private $conn;
	private $appData;
	
	protected function setUp() {
		$this->conn = AppDataDao::connect();
		$this->appData = array(
				APP_DATE => '2013-04-06',
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
	 * @expectedException DataAccessException
	 * @expectedExceptionMessage AppDataDao: Failed to insert: (1062) Duplicate entry '2013-04-06' for key 'PRIMARY'
	 */
	public function doesNotAllowMultipleInserts() {
		$this->deleteAppIfExists();
		$dao = new AppDataDao($this->conn);
		$dao->insertTodaysAppData($this->appData);
		$dao->insertTodaysAppData($this->appData);
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
		$appDate = $this->appData[APP_DATE];
		$this->conn->query("DELETE FROM free_app_of_day WHERE app_date = '$appDate'");
	}
	
}

?>